package com.mjy.blog;

import com.mjy.blog.mapper.ImgDao;
import com.mjy.blog.utils.GetJsonImg;
import com.mjy.blog.utils.imgJson;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class imgTest {
    @Resource
    private ImgDao imgDao;

    Logger logger1 = Logger.getLogger("console");


    @Test
    public void test1(){
        imgJson imgJson = new imgJson();
        imgJson.setBucket("mjy");
        imgJson.setKey("1234");
        imgJson.setFsize(122);
        imgJson.setHash("dsjfdfdfsfdsfds");
//        imgDao.addImgInformation(imgJson);
        logger1.debug(imgJson);
        System.out.println("ok");
    }

    @Test
    public void testutil() throws Exception{
        String a = "{\"key\":\"Fp2ZmohuFLoeFZygcsk51Hf0pZXf\",\"hash\":\"Fp2ZmohuFLoeFZygcsk51Hf0pZXf\",\"bucket\":\"mjy\",\"fsize\":182779}";
        imgJson jsonBodyObject = GetJsonImg.getJsonBodyObject(a, imgJson.class);
        System.out.println(jsonBodyObject);
    }
}
