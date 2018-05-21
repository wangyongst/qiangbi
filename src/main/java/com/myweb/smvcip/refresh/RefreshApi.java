package com.myweb.smvcip.refresh;

import com.myweb.smvcip.HttpClientUtil;
import com.myweb.smvcip.ProxyUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class RefreshApi {
    private HttpClientUtil httpClientUtil = null;

    public RefreshApi() throws Exception {
        httpClientUtil = new HttpClientUtil();
    }

    public String getCode() throws Exception {
        String name = String.valueOf(new Date().getTime());
        HttpResponse httpResponse = httpClientUtil.get("https://www.smcvip.com/index.php/login/verify", ProxyUtils.makeRequestConfig());
        if (httpResponse.getStatusLine().getStatusCode() != 200) return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        File fileDir = new File("C:\\imgs\\refresh");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File imgFile = new File("C:\\imgs\\refresh\\" + name + ".png");
        if (!imgFile.exists()) {
            imgFile.createNewFile();
        }
        FileOutputStream output = new FileOutputStream(imgFile);
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null) {
            InputStream instream = entity.getContent();
            byte b[] = new byte[1024];
            int j = 0;
            while ((j = instream.read(b)) != -1) {
                output.write(b, 0, j);
            }
            output.flush();
            output.close();
        }
        return name;
    }

    public String login(String username, String password, String code) throws Exception {
        HttpResponse httpResponse = httpClientUtil.login(username, password, code, ProxyUtils.makeRequestConfig());
        if (httpResponse.getStatusLine().getStatusCode() != 200) return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
    }

    public String refresh() throws Exception {
        HttpResponse httpResponse = httpClientUtil.get("https://www.smcvip.com/Works/IBOT", ProxyUtils.makeRequestConfig());
        if (httpResponse.getStatusLine().getStatusCode() != 200) return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        return EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
    }
}
