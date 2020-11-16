package com.fei.baselibrary.crash;

import android.content.Context;

import com.fei.baselibrary.utils.DeviceUtil;
import com.fei.baselibrary.utils.LogUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @ClassName: ExceptionCrashHandler
 * @Description: 单例模式的异常捕获
 * @Author: Fei
 * @CreateDate: 2020-11-01 11:40
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-11-01 11:40
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class ExceptionCrashHandler implements Thread.UncaughtExceptionHandler {

    private static ExceptionCrashHandler instance;
    private Context context;
    private Thread.UncaughtExceptionHandler handler;
    private static final String TAG = "ExceptionCrashHandler";


    public static ExceptionCrashHandler getInstance() {
        if (instance == null) {
            synchronized (ExceptionCrashHandler.class) {
                if (instance == null) {
                    instance = new ExceptionCrashHandler();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        Thread.currentThread().setUncaughtExceptionHandler(this);
        handler = Thread.currentThread().getDefaultUncaughtExceptionHandler();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {

        //写入本地文件
        String fileName = saveInfoToSD(e);
        // 保存当前文件，等应用再次启动在上传
        LogUtils.i(TAG, fileName);
        cacheCrashFile(fileName);
        //交给系统处理Exception
        handler.uncaughtException(t, e);
    }

    /**
     * 缓存异常文件
     */
    private void cacheCrashFile(String fileName) {
        CrashPreUtil.getInstance(context).put(CrashConfig.CRASH_KEY, fileName);
    }

    /**
     * 获取异常文件
     */
    public File getCrashFile() {
        return new File(CrashPreUtil.getInstance(context).get(CrashConfig.CRASH_KEY).toString());
    }


    /**
     * 保存软件信息，设备信息，出错信息到SDCard中
     */
    private String saveInfoToSD(Throwable ex) {
        String fileName = null;
        StringBuffer sb = new StringBuffer();
        //1.应用信息 包名 版本号 手机信息
        for (Map.Entry<String, String> entry : DeviceUtil.obtainSimpleInfo(context).entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append(" = ").append(value).append("\n");
        }
        //2.崩溃的详细信息
        sb.append(obtainExceptionInfo(ex));

        //3.保存文件
        File cacheDir = context.getFilesDir();
        DateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String time = formatter.format(new Date());
        fileName = "crash_" + time + ".log";
        File dirFile = new File(cacheDir + File.separator + "crash");

        if (!dirFile.exists()) {
            dirFile.mkdirs();
        }
        File file = new File(dirFile, fileName);
        LogUtils.i(TAG, file.getName());
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file.getAbsolutePath());
            fos.write(sb.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return file.getAbsolutePath();
    }

    /**
     * 获取错误信息
     */
    private String obtainExceptionInfo(Throwable ex) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        ex.printStackTrace(printWriter);
        printWriter.close();
        return printWriter.toString();
    }
}
