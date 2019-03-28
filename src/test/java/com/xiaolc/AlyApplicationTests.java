package com.xiaolc;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
public class AlyApplicationTests {

    @Test
    public void contextLoads() {
        String str="http://xiaolc-2.oss-cn-shanghai.aliyuncs.com/test/aliyun.html?Expires=1869116961&OSSAccessKeyId=LTAI118gJZGIX0OT&Signature=zFqgY9GEMU4FzOnhfbuAuuKK0zQ%3D";
        String s=str.substring(0,str.indexOf("?"));
        System.out.println(s);
    }

}
