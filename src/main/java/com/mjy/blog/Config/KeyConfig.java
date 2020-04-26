package com.mjy.blog.Config;


import com.mjy.blog.Utils.RsaUtils;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author mjy
 * @create 2020-03-05-17:03
 */

@Data
@Component
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

}
