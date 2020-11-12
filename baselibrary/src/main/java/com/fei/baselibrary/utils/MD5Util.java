package com.fei.baselibrary.utils;

import java.security.MessageDigest;

/**
 * @ClassName: MD5Util
 * @Description: 描述
 * @Author: Fei
 * @CreateDate: 2020/11/12 19:43
 * @UpdateUser: Fei
 * @UpdateDate: 2020/11/12 19:43
 * @UpdateRemark: 更新说明
 * @Version: 1.0
 */
public class MD5Util {

    /**
     * 根据传入的字节数组生成MD5摘要（方便二进制内容生成MD5摘要
     *
     * @param str
     * @return MD5摘要
     */
    public static String getMd5(String str) {
        try {
            byte[] byteArray = str.getBytes();
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            mdInst.update(byteArray);
            byte[] md = mdInst.digest();

            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < md.length; i++) {
                int val = ((int) md[i]) & 0xff;
                if (val < 16)
                    sb.append("0");
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
