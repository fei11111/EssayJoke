package com.fei.essayjoke.utils;

/**
 * @ClassName: DiffUtil native方法
 * @Description: 合并apk和差分包生成新的apk
 * @Author: Fei
 * @CreateDate: 2020-12-01 20:10
 * @UpdateUser: 更新者
 * @UpdateDate: 2020-12-01 20:10
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class PatchUtil {

    /**
     * 将apk和差分包合并生成新的apk
     * @param oldApkPath 上一版本apk路径
     * @param newApkPath 新版本apk路径，用来存放
     * @param patchPath 差分包路径
     * */
    public static native void combine(String oldApkPath,
                                      String newApkPath,
                                      String patchPath);

}
