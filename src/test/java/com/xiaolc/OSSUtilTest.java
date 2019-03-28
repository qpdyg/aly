package com.xiaolc;

import com.aliyun.oss.OSSClient;
import com.xiaolc.util.OSSUtil;
import org.junit.Test;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

/**
 * @author lc
 * @create 2019-03-28 14:16
 * @ClassName OSSUtilTest
 **/
public class OSSUtilTest {
    private OSSClient ossClient = OSSUtil.getOSSClient();
    private String bucketName = "xiaolc-2";

    @Test
    public void testUploadByNetworkStream() {
        //测试通过网络流上传文件
        try {
            URL url = new URL("https://www.aliyun.com/");
            OSSUtil.uploadByNetworkStream(ossClient, url, bucketName, "test/aliyun.html");
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            String u = ossClient.generatePresignedUrl(bucketName, "test/aliyun.html", expiration).toString();
            System.out.println(u.substring(0, u.indexOf("?")));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUploadByInputStream() {
        //文件存储目录不用加/
        String catalog = "test";
        //测试通过输入流上传文件
        File file = new File("D:/applicationContext.xml");
        try {
            InputStream inputStream = new FileInputStream(file);
            String filename = OSSUtil.genImageName()+file.getName();
            OSSUtil.uploadByInputStream(ossClient, inputStream, bucketName, filename,catalog);
            String url = OSSUtil.url(bucketName, filename, catalog);
            System.out.println(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testUploadByFile() {
        //文件存储目录不用加/
        String catalog = "test";
        //测试通过file上传文件
        File file = new File("D:/1.jpg");
        //文件名称
        String filename = OSSUtil.genImageName() + file.getName();
        //上传
        OSSUtil.uploadByFile(ossClient, file, bucketName, filename,catalog);
       //获取文件url
        String url = OSSUtil.url(bucketName, filename,catalog);
        System.out.println(url);
    }

    @Test
    public void testDeleteFile() {
        //测试根据key删除oss服务器上的文件
        OSSUtil.deleteFile(ossClient, bucketName, "test/a.xml");
    }

    @Test
    public void testGetInputStreamByOSS() {
        //测试根据key获取服务器上的文件的输入流
        try {
            InputStream content = OSSUtil.getInputStreamByOSS(ossClient, bucketName, "test/applicationContext.xml");
            if (content != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(content));
                while (true) {
                    String line = reader.readLine();
                    if (line == null) break;
                    System.out.println("\n" + line);
                }
                // 数据读取完成后，获取的流必须关闭，否则会造成连接泄漏，导致请求无连接可用，程序无法正常工作。
                content.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testQueryAllObject() {
        //测试查询某个bucket里面的所有文件
        List<String> results = OSSUtil.queryAllObject(OSSUtil.getOSSClient(), bucketName);
        System.out.println(results);
    }

}
