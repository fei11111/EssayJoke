package com.fei.baselibrary.fix;

import android.content.Context;
import android.util.Log;

import com.fei.baselibrary.utils.FileUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.SortedSet;
import java.util.concurrent.ConcurrentSkipListSet;

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
    private final File dirFile;
    private final SortedSet<File> mFiles;

    private final Context context;

    public FixManager(Context context) {
        this.context = context;
        dirFile = new File(context.getFilesDir(), DIR);
        mFiles = new ConcurrentSkipListSet<File>();
    }

    /**
     * 初始化
     */
    public void init() {
        if (!dirFile.exists() && !dirFile.mkdirs()) {// make directory fail
            Log.e(TAG, "patch dir create error.");
            return;
        } else if (!dirFile.isDirectory()) {// not directory
            dirFile.delete();
            return;
        }
        initDexFiles();
    }

    private void initDexFiles() {
        File[] files = dirFile.listFiles();
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(SUFFIX)) {
                mFiles.add(file);
            }
        }
    }

    /**
     * 加载修复文件
     *
     * @param file
     */
    public void addPatch(File file) throws NoSuchFieldException, IllegalAccessException, IOException, ClassNotFoundException {
        File dest = new File(dirFile, file.getName());
        if (!file.exists()) {
            Log.e(TAG, "dex [" + file.getAbsolutePath() + "] 文件不存在");
            throw new FileNotFoundException(file.getAbsolutePath());
        }
        if (dest.exists()) {
//            Log.d(TAG, "dex [" + dest.getAbsolutePath() + "] has be loaded.");
//            return;
            dest.delete();
        }
        // copy to patch's directory
        FileUtil.copyFile(file, dest);
        loadPatch(dest);


    }

    private void loadPatch(File dexFile) throws NoSuchFieldException, IllegalAccessException, ClassNotFoundException {
        //1.获取已加载的pathList
        ClassLoader sysClassLoader = context.getClassLoader();
        Object sysPathList = getPathListField(sysClassLoader);
        //2.通过已加载的pathList获取dexElements属性和值
        Field sysDexElementsField = getDexElements(sysPathList);
        Object sysDexElementsValue = sysDexElementsField.get(sysPathList);
        //3.创建需要修复dexPath的classLoader
        File optimizedDirectory = new File(dirFile, DIR_OPT);
        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }
        BaseDexClassLoader mClassLoader = new BaseDexClassLoader(dexFile.getAbsolutePath(),
                optimizedDirectory, null, sysClassLoader);
        //4.通过需要修复的classLoader获取pathList
        Object mPathList = getPathListField(mClassLoader);
        //5.通过需要修复的pathList获取dexElements[]和值
        Field mDexElementsField = getDexElements(mPathList);
        Object mDexElementsValue = mDexElementsField.get(mPathList);
        //6.合并dexElements，将需要修复的dexElements放在前面
        Object value = combineObjects(sysDexElementsValue, mDexElementsValue);
        //7.重新注入
        sysDexElementsField.set(sysPathList, value);
    }

    /**
     * 获取属性pathList的值
     */
    private Object getPathListField(Object object) throws NoSuchFieldException, ClassNotFoundException, IllegalAccessException {
        Class baseDexClazzLoader = Class.forName("dalvik.system.BaseDexClassLoader");
        Field pathList = baseDexClazzLoader.getDeclaredField("pathList");
        pathList.setAccessible(true);
        return pathList.get(object);
    }

    /**
     * 通过DexPathList获取dexElements属性
     */
    public Field getDexElements(Object object) throws NoSuchFieldException, IllegalAccessException {
        Field dexElements = object.getClass().getDeclaredField("dexElements");
        dexElements.setAccessible(true);
        return dexElements;
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
            Object array = Array.newInstance(elementType, length);

            //5.循环赋值到新的数组
            for (int i = 0; i < length; i++) {
                if (i < dstLength) {
                    Array.set(array, i, Array.get(dst, i));
                } else {
                    Array.set(array, i, Array.get(src, i - dstLength));
                }
            }
            return array;
        } else {
            Log.e(TAG, "合并数据错误");
            throw new IllegalArgumentException("错误数据类型");
        }
    }

    /**
     * 加载之前所有的dex文件
     */
    public void loadPatch() {
        for (File mFile : mFiles) {
            try {
                loadPatch(mFile);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
                Log.e(TAG, "加载之前文件：" + mFile.getAbsolutePath() + "] NoSuchFieldException");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.e(TAG, "加载之前文件：" + mFile.getAbsolutePath() + "] IllegalAccessException");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


}
