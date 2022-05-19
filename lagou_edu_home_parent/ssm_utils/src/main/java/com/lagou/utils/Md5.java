package com.lagou.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.UUID;

public class Md5 {

    public final static String md5key = "lagou";
    /**
     * MD5方法
     * @param text 明文 123456
     * @param key 密钥 lagou
     * @return 密文
     * @throws Exception
     */
    //进行注册的操作
    public static String md5(String text, String key) throws Exception {
        //加密后的字符串
        String encodeStr= DigestUtils.md5Hex(text+key); //这里拼接一个固定数
        System.out.println("MD5加密后的字符串为:encodeStr="+encodeStr);
        return encodeStr;
    }

    /**
     * MD5验证方法
     * @param text 明文
     * @param key 密钥
     * @param md5 密文
     * @return true/false
     * @throws Exception
     */
    //进行登录的操作，获得数据库的密文，进行判断是否一致
    public static boolean verify(String text, String key, String md5) throws Exception {
        //根据传入的密钥进行验证
        String md5Text = md5(text, key);
        if(md5Text.equalsIgnoreCase(md5))
        {
            System.out.println("MD5验证通过");
            return true;
        }
        return false;
    }

    //上面并没有用到加盐，即加随机数字符串

    //测试登录和注册
    public static void main(String[] args) throws Exception {

        //注册 用户名：tom 密码：123456
        //注册用户时：将明文密码转换成密文密码
        String s = md5("123456", md5key);
        System.out.println(s);

        //登录 用户名：tom 密码：123456
        //登录时：将明文密码转换成密文密码
        boolean verify = verify("123456", md5key, s);
        System.out.println(verify);


    }

}
