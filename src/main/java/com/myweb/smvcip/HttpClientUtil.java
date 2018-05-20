package com.myweb.smvcip;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;

import java.io.IOException;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {

    public HttpClient httpClient;

    public HttpClientUtil() throws Exception {
        if (httpClient == null) {
            httpClient = SSLClient.SslHttpClientBuild();
        }
    }

    public HttpResponse get(String url) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return httpClient.execute(httpGet);
    }
}
