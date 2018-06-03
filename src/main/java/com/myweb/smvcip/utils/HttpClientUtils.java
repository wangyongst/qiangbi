package com.myweb.smvcip.utils;

import com.myweb.smvcip.account.Account;
import com.myweb.smvcip.account.Accounts;
import com.myweb.smvcip.refresh.HttpClientUtil;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/*
 * 利用HttpClient进行post请求的工具类
 */
public class HttpClientUtils {
    public static HttpResponse get(String url,String username) throws IOException {
        HttpHost proxy = new HttpHost(Accounts.getAccounts().get(username).getIp(),Accounts.getAccounts().get(username).getPort());
        return HttpClientUtil.get(url,proxy);
    }

    public static HttpResponse login(String username, String password, String code) throws IOException {
        HttpHost proxy = new HttpHost(Accounts.getAccounts().get(username).getIp(),Accounts.getAccounts().get(username).getPort());
        return HttpClientUtil.post("https://www.smcvip.com/index.php/login/logincl", loginMap(username, password, code),proxy);
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
