package com.myweb.smvcip;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class ProxyUtils {

    public static RequestConfig makeRequestConfig() throws IOException {
        CloseableHttpClient hc = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://api.ip.data5u.com/dynamic/get.html?order=1bba62a4564617691a6bfae7dec87141&sep=4");
        HttpResponse httpResponse = hc.execute(httpGet);
        String reust = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
        HttpHost proxy = new HttpHost(reust.split(":")[0], Integer.parseInt(reust.split(":")[1].trim()));
        RequestConfig requestConfig = RequestConfig.custom().setConnectionRequestTimeout(1000).setProxy(proxy).build();
        return requestConfig;
    }
}
