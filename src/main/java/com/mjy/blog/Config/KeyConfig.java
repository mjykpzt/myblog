package com.mjy.blog.Config;


import com.mjy.blog.Utils.RsaUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author mjy
 * @create 2020-03-05-17:03
 */
@Configuration
@Data
public class KeyConfig {

    @Value("${rsa.key.pukey}")
    private String pu_key;

    @Value("${rsa.key.prkey}")
    private String pr_key;

    private PublicKey publicKey;
    private PrivateKey privateKey;

    @PostConstruct
    public void creat_key() {
        try {
            publicKey = RsaUtils.getPublicKey(pu_key);
            privateKey = RsaUtils.getPrivateKey(pr_key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("无法读取key");
        }
    }

//    public String getPu_key() {
//        return pu_key;
//    }
//
//    public void setPu_key(String pu_key) {
//        this.pu_key = pu_key;
//    }
//
//    public String getPr_key() {
//        return pr_key;
//    }
//
//    public void setPr_key(String pr_key) {
//        this.pr_key = pr_key;
//    }
//
//    public PublicKey getPublicKey() {
//        return publicKey;
//    }
//
//    public void setPublicKey(PublicKey publicKey) {
//        this.publicKey = publicKey;
//    }
//
//    public PrivateKey getPrivateKey() {
//        return privateKey;
//    }
//
//    public void setPrivateKey(PrivateKey privateKey) {
//        this.privateKey = privateKey;
//    }
}
