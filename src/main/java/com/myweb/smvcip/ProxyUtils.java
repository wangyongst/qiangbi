package com.myweb.smvcip;

import com.myweb.Start;
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
    private static long offet = 0;
    private static RequestConfig requestConfig;
    private static String ip;
    private static int port;
    public static boolean isfeng = false;

    public static RequestConfig makeRequestConfig() throws Exception {
        if (new Date().getTime() > time + offet || isfeng == true) {
            getIP();
            time = new Date().getTime();
            requestConfig = createRequestConfig();
        }
        return requestConfig;
    }

    private static void getIP() throws Exception {
        CloseableHttpClient hc = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.ip.data5u.com/dynamic/get.html?order=1bba62a4564617691a6bfae7dec87141&sep=4&ttl=true");
        HttpResponse httpResponse = hc.execute(httpGet);
        while (httpResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("获取动态代理IP失败，失败原因：" + httpResponse.getStatusLine().getStatusCode() + " 程序将自动重新尝试获取");
            if (httpResponse.getStatusLine().getStatusCode() == 429) {
                Thread.sleep(Start.SLEEP);
            }
            httpResponse = hc.execute(httpGet);
        }
        String reust = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        ip = reust.split(":")[0];
        port = Integer.parseInt(reust.split(":")[1].split(",")[0].trim());
        offet = Long.parseLong(reust.split(":")[1].split(",")[1].trim());
    }


    private static RequestConfig createRequestConfig() throws IOException, InterruptedException {
        HttpHost proxy = new HttpHost(ip, port);
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000).setProxy(proxy).build();
        return requestConfig;
    }
}
