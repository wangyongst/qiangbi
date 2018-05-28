package com.myweb.smvcip.refresh;

import com.myweb.smvcip.utils.HttpClientUtil;
import com.myweb.smvcip.utils.Proxys;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.Date;

public class RefreshApi {
    private HttpClientUtil httpClientUtil = new HttpClientUtil();
    private String username;
    private String password;
    private int i;

    public RefreshApi(String username, String password,int i) throws Exception {
        this.username = username;
        this.password = password;
        this.i = i;
    }

    public InputStream getCode() throws Exception {
        HttpResponse httpResponse = httpClientUtil.get("https://www.smcvip.com/index.php/login/verify",  Proxys.getProxy(i).makeRequestConfig());
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            return null;
        }
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null && entity.getContentType().getValue().equals("image/png")) {
            InputStream instream = entity.getContent();
            return instream;
        }
        return null;
    }
    public String login(String code) throws Exception {
        HttpResponse httpResponse = httpClientUtil.login(username, password, code, Proxys.getProxy(i).makeRequestConfig());
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            Proxys.getProxy(i).out = true;
            return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        }
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
    }

    public String refresh() throws Exception {
        HttpResponse httpResponse = httpClientUtil.get("https://www.smcvip.com/Works/IBOT", Proxys.getProxy(i).makeRequestConfig());
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            Proxys.getProxy(i).out = true;
            return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        }
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
    }
}
