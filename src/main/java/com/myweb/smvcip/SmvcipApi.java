package com.myweb.smvcip;

import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Collector;
import org.jsoup.select.Elements;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SmvcipApi {
    private HttpClientUtil httpClientUtil = null;

    public SmvcipApi() throws Exception {
        httpClientUtil = new HttpClientUtil();
    }

    public void getCode(RequestConfig requestConfig) throws Exception {
        HttpResponse httpResponse = httpClientUtil.get("https://www.smcvip.com/index.php/login/verify",requestConfig);
        File imgFile = new File("C:\\Users\\Administrator\\Documents\\GitHub\\qiangbi\\imgs\\0000.png");
        FileOutputStream output = new FileOutputStream(imgFile);
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            byte b[] = new byte[1024];
            int j = 0;
            while ((j = instream.read(b)) != -1) {
                output.write(b, 0, j);
            }
            output.flush();
            output.close();
        }
    }

    public String login(String username,String password,String code,RequestConfig requestConfig) throws Exception {
       return EntityUtils.toString(httpClientUtil.login(username,password,code,requestConfig).getEntity(), "UTF-8");
    }

    public String refresh(RequestConfig requestConfig) throws IOException {
        return EntityUtils.toString(httpClientUtil.get("https://www.smcvip.com/Works/IBOT",requestConfig).getEntity(),"UTF-8");
    }
}
