package com.myweb.smvcip;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Date;

public class ProxyUtils {
    private static long time = 0;
    private static final long offet = 60000;
    private static RequestConfig requestConfig;
    public static RequestConfig makeRequestConfig() throws IOException, InterruptedException {
       if(new Date().getTime() > time + offet){
           time = new Date().getTime();
           requestConfig = createRequestConfig();
       }
       return requestConfig;
    }


    public static RequestConfig createRequestConfig() throws IOException, InterruptedException {
        CloseableHttpClient hc = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.ip.data5u.com/dynamic/get.html?order=1bba62a4564617691a6bfae7dec87141&sep=4");
        HttpResponse httpResponse = hc.execute(httpGet);
        while (httpResponse.getStatusLine().getStatusCode() != 200){
            System.out.println("获取动态代理IP失败，失败原因："+httpResponse.getStatusLine().getStatusCode()+" 程序将自动重新尝试获取");
            httpResponse = hc.execute(httpGet);
        }
        String reust = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        HttpHost proxy = new HttpHost(reust.split(":")[0], Integer.parseInt(reust.split(":")[1].trim()));
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000).setProxy(proxy).build();
        return requestConfig;
    }
}
