package com.myweb.smvcip.utils;

import com.myweb.smvcip.refresh.HttpClientUtil;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtils {
    public static HttpResponse get(String url) throws IOException {
        return HttpClientUtil.get(url);
    }

    public static HttpResponse login(String username, String password, String code) throws IOException {
        return HttpClientUtil.post("https://www.smcvip.com/index.php/login/logincl", loginMap(username, password, code));
    }


    public static Map loginMap(String username, String password, String code) {
        Map<String, String> createMap = new HashMap<String, String>();
        createMap.put("__TOKEN__", "");
        createMap.put("account", username);
        createMap.put("password", password);
        createMap.put("txtcode", code);
        createMap.put("login", "登陆");
        return createMap;
    }
}
