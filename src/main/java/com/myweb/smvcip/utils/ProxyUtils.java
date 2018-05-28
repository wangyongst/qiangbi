package com.myweb.smvcip.utils;

import com.myweb.Start;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ProxyUtils {
    private long time = 0;
    private long offet = 300000;
    private RequestConfig requestConfig;
    public String ip;
    public int port;
    private int size;
    public boolean out = false;

    public ProxyUtils(int size){
        this.size = size;
    }

    public RequestConfig makeRequestConfig() throws Exception {
        if (new Date().getTime() > time + offet || out) {
           getIP();
            //ip="222.64.33.247";
            //port=9797;
            time = new Date().getTime();
            requestConfig = createRequestConfig();
            out = false;
        }
        return requestConfig;
    }

    private void getIP() throws Exception {
        CloseableHttpClient hc = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://webapi.http.zhimacangku.com/getip?num=1&type=1&pro=0&city=0&yys=0&port=1&time=1&ts=0&ys=0&cs=0&lb=4&sb=0&pb=4&mr=1&regions=");
        HttpResponse httpResponse = hc.execute(httpGet);
        httpGet.setConfig(requestConfig);
        while (httpResponse.getStatusLine().getStatusCode() != 200) {
            System.out.println("获取动态代理IP失败，失败原因：" + httpResponse.getStatusLine().getStatusCode() + " 程序将自动重新尝试获取");
            if (httpResponse.getStatusLine().getStatusCode() == 429) {
            }
            httpResponse = hc.execute(httpGet);
        }
        String reusts = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        if (reusts.contains("服务期限已经于")) System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + "  代理已经过期！");
        else if (reusts.contains("请控制好请求频率")) System.out.println(new SimpleDateFormat("HH:mm:ss").format(new Date()) + "  超过代理访问频率");
        String reust = reusts.split("\n")[size];
        ip = reust.split(":")[0];
        port = Integer.parseInt(reust.split(":")[1].split(",")[0].trim());
       // offet = Long.parseLong(reust.split(":")[1].split(",")[1].trim());
    }


    private RequestConfig createRequestConfig() throws IOException, InterruptedException {
        HttpHost proxy = new HttpHost(ip, port);
        RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(1000).setProxy(proxy).build();
        return null;
    }
}
