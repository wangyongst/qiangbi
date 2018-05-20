package com.myweb.smvcip;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.*;

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
        HttpHost proxy=new HttpHost("49.51.231.179", 1080);
        RequestConfig requestConfig=RequestConfig.custom().setProxy(proxy).build();
        httpGet.setConfig(requestConfig);
        return httpClient.execute(httpGet);
    }

    public HttpResponse login(String username,String password,String vcode) throws IOException {
        HttpPost httpPost = new HttpPost("https://www.smcvip.com/index.php/login/logincl");
        HttpHost proxy=new HttpHost("49.51.231.179", 1080);
        RequestConfig requestConfig=RequestConfig.custom().setProxy(proxy).build();
        httpPost.setConfig(requestConfig);
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Iterator iterator = loginMap(username,password,vcode).entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
            list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
        }
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
            httpPost.setEntity(entity);
        }
        return httpClient.execute(httpPost);
    }


    public Map loginMap(String username,String password,String code) {
        Map<String, String> createMap = new HashMap<String, String>();
        createMap.put("__TOKEN__", "");
        createMap.put("account", username);
        createMap.put("password", password);
        createMap.put("txtcode", code);
        createMap.put("login", "登陆");
        return createMap;
    }
}
