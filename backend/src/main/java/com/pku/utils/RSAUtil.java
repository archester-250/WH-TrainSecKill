package com.pku.utils;

import javax.crypto.Cipher;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

public class RSAUtil {
    //密钥位数：512，密钥格式：PKCS#8
    private static final String PRIVATE_KEY = "MIIBVgIBADANBgkqhkiG9w0BAQEFAASCAUAwggE8AgEAAkEAuYgMP7hHlBTbJXwKNBffAxhijrBFnHc5wLzgA3lSdwvHIR/l6OEVnsnqYUrywzxE152GjEMQzjdRJy8FYPjZ8wIDAQABAkBNQgBrxlaULO0m2lS7ZZdZyB0+ctvB8IntnxkfiTVUWa3UAo4IyTgfC2cikJh1T4+RxugM8NU6KQQnzANDbgARAiEA9nlCOi2b4hrB+5L4aUtyel97Wjt76ykFnBEH1OgXHdECIQDAs8tcDMaLvfhBmVknnZUSxEfFoMfzw6aKCUbVhgkYgwIhAJejApvIc58n1zBvu2UMheHD3KAm+JY1Jr5du/PA1ngBAiEAg6bSGnNH+hSxVO/VbzFDtWyPtD8iHDHgx5GK6ToHYM0CIQDEvVqfjBNGMDPtHgAxPurlkSg/E8r5yXbBzUWoYPwrLg==";
    public static String decrypt(String encryptedData) throws Exception {
        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(PRIVATE_KEY));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }
}
