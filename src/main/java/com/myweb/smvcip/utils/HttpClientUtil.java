package com.myweb.smvcip.utils;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.util.*;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtil {
    private HttpClient httpClient = null;
    public  HttpClientContext context = null;
    public  CookieStore cookieStore = null;

    public HttpClientUtil() {
        context = HttpClientContext.create();
        cookieStore = new BasicCookieStore();;
        httpClient = SSLClient.SslHttpClientBuild(cookieStore);
    }

    public HttpResponse get(String url, RequestConfig requestConfig) throws IOException {
        HttpGet httpGet = new HttpGet(url);
        return httpClient.execute(httpGet,context);
    }

    public HttpResponse login(String username, String password, String vcode, RequestConfig requestConfig) throws IOException {
        HttpPost httpPost = new HttpPost("https://www.smcvip.com/index.php/login/logincl");
        if (requestConfig != null) httpPost.setConfig(requestConfig);
        //设置参数
        List<NameValuePair> list = new ArrayList<NameValuePair>();
        Iterator iterator = loginMap(username, password, vcode).entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, String> elem = (Map.Entry<String, String>) iterator.next();
            list.add(new BasicNameValuePair(elem.getKey(), elem.getValue()));
        }
        if (list.size() > 0) {
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(list);
            httpPost.setEntity(entity);
        }
        return httpClient.execute(httpPost,context);
    }


    public Map loginMap(String username, String password, String code) {
        Map<String, String> createMap = new HashMap<String, String>();
        createMap.put("__TOKEN__", "");
        createMap.put("account", username);
        createMap.put("password", password);
        createMap.put("txtcode", code);
        createMap.put("login", "登陆");
        return createMap;
    }
}
