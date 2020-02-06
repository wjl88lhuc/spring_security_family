package com.itheima.utils;

import org.junit.Test;

public class RsaUtilsTest {

    private String privateFilePath = "D:/auth_key/id_key_rsa";
    private String publicFilePath = "D:/auth_key/id_key_rsa.pub";

    @Test
    public void generateKey() throws Exception {
        //生成密钥与私钥
        RsaUtils.generateKey(publicFilePath,privateFilePath,"itguigu",2048);
    }

    @Test
    public void showPrivateKey() throws Exception {
        System.out.println(RsaUtils.getPrivateKey(privateFilePath));
    }

    @Test
    public void showPublicKey() throws Exception {
        System.out.println(RsaUtils.getPublicKey(publicFilePath));
    }
}
