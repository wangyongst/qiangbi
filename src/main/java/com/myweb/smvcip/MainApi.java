package com.myweb.smvcip;

import com.myweb.smvcip.utils.HttpClientUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.InputStream;
import java.util.Date;

public class MainApi {
    private HttpClientUtil httpClientUtil = null;
    private String username;
    private String password;

    public MainApi(String username, String password) throws Exception {
        this.httpClientUtil = new HttpClientUtil();
        this.username = username;
        this.password = password;
    }

    public InputStream getCode() throws Exception {
        HttpResponse httpResponse = httpClientUtil.get("https://www.smcvip.com/index.php/login/verify", null);
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

    public String login(String username, String password, String code) throws Exception {
        HttpResponse httpResponse = httpClientUtil.login(username, password, code, null);
        if (httpResponse.getStatusLine().getStatusCode() != 200) return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
    }

//    public String buy() throws IOException {
//        HttpResponse httpResponse = httpClientUtil.get("https://www.smcvip.com/Works/IBOT");
//        if (httpResponse.getStatusLine().getStatusCode() != 200) return String.valueOf(httpResponse.getStatusLine().getStatusCode());
//        return EntityUtils.toString(httpClientUtil.get("https://www.smcvip.com/Works/IBOT").getEntity(), "UTF-8");
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
