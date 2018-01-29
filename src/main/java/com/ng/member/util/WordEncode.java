package com.ng.member.util;

import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 密码MD5加密
 * @author niuguang
 * @date 18-1-17
 */
@Slf4j
public class WordEncode {

    public static String EncoderByMd5(String str){
        String newstr = null;
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            //加密后的字符串
            newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
        } catch (Exception e){
            log.error("加密密码失败",e);
        }

        return newstr;
    }
}
