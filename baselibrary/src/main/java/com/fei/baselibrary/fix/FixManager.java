package com.fei.baselibrary.fix;

import android.content.Context;
import android.util.Log;

import com.fei.baselibrary.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import dalvik.system.BaseDexClassLoader;

/**
 * @ClassName: FixManager
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/3 15:14
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/3 15:14
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class FixManager {

    private static final String TAG = "FixManager";
    // patch extension
    private static final String SUFFIX = ".dex";
    private static final String DIR = "apatch";
    private static final String DIR_OPT = "apatch_opt";
    private File dirFile;

    private Context context;

    /**
     * 初始化
     */
    public void init(Context context) {
        this.context = context;
        dirFile = new File(context.getFilesDir(), DIR);
    }

    /**
     * 加载修复文件
     *
     * @param dexPath 文件路径
     */
    public void addPatch(String dexPath) throws NoSuchFieldException, IllegalAccessException, IOException {
        File src = new File(dexPath);
        File dest = new File(dirFile, src.getName());
        if (!src.exists()) {
            throw new FileNotFoundException(dexPath);
        }
        if (dest.exists()) {
            Log.d(TAG, "dex [" + dexPath + "] has be loaded.");
            return;
        }
        FileUtil.copyFile(src, dest);// copy to patch's directory
        loadPatch(dest);
    }

    private void loadPatch(File dexFile) throws NoSuchFieldException, IllegalAccessException {
        //1.获取已加载的pathList
        ClassLoader sysClassLoader = context.getClassLoader();
        Field sysPathListField = getPathListField(sysClassLoader);
        Object sysPathListValue = sysPathListField.get(sysClassLoader);
        //2.通过已加载的pathList获取dexElements[]
        Object sysDexElements = getDexElements(sysPathListField);
        //3.创建需要修复dexPath的classLoader
        File optimizedDirectory = new File(context.getFilesDir(), DIR_OPT);
        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }
        BaseDexClassLoader myClassLoader = new BaseDexClassLoader(dexFile.getAbsolutePath(),
                optimizedDirectory, null, context.getClassLoader());
        //4.通过需要修复的classLoader获取pathList
        Field myPathList = getPathListField(myClassLoader);
        Object myPathListValue = myPathList.get(myClassLoader);
        //5.通过需要修复的pathList获取dexElements[]
        Object myDexElements = getDexElements(myPathList);
        //6.合并dexElements，将需要修复的dexElements放在前面
        Object value = combineObjects(sysPathListValue, myPathListValue);
        //7.发射注入
        sysPathListField.set(sysClassLoader,value);
    }

    /**
     * 数组合并
     */
    private Object combineObjects(Object src, Object dst) {
        //1.判断是否是数组
        if (src.getClass().isArray() && dst.getClass().isArray()) {
            //2.获取数据类型
            Class elementType = src.getClass().getComponentType();
            //3.获取两个数组的长度
            int srcLength = Array.getLength(src);
            int dstLength = Array.getLength(dst);
            int length = srcLength + dstLength;
            //4.创建新的数组
            Object[] array = (Object[]) Array.newInstance(elementType, length);
            //5.强制转化
            Object[] srcArray = (Object[]) src;
            Object[] dstArray = (Object[]) dst;
            //6.循环赋值到新的数组
            for (int i = 0; i < length; i++) {
                if (i < dstLength) {
                    array[i] = dstArray[i];
                } else {
                    array[i] = srcArray[i-dstLength];
                }
            }
            return array;
        } else {
            throw new IllegalArgumentException("错误数据类型");
        }
    }

    /**
     * 加载之前的bug
     */
    public void loadPatch() {
        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(SUFFIX)) {

            }
        }
    }

    /**
     * 通过classLoader获取pathList的值
     */
    public Field getPathListField(Object object) throws NoSuchFieldException, IllegalAccessException {
        Field pathList = object.getClass().getDeclaredField("pathList");
        pathList.setAccessible(true);
        return pathList;
    }

    /**
     * 通过DexPathList获取dexElements
     */
    public Object getDexElements(Object object) throws NoSuchFieldException, IllegalAccessException {
        Field dexElements = object.getClass().getDeclaredField("dexElements");
        dexElements.setAccessible(true);
        return dexElements.get(object);
    }

}
