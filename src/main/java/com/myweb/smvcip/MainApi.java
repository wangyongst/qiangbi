package com.myweb.smvcip;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

public class MainApi {
    private HttpClientUtil httpClientUtil = null;
    private String username;
    private String password;

    public MainApi(String username, String password) throws Exception {
        this.httpClientUtil = HttpClientUtil.getHttpClientUtil();
        this.username = username;
        this.password = password;
    }

    public String getCode() throws Exception {
        String name = String.valueOf(new Date().getTime());
        HttpResponse httpResponse = httpClientUtil.get("https://www.smcvip.com/index.php/login/verify");
        if (httpResponse.getStatusLine().getStatusCode() != 200) return String.valueOf(httpResponse.getStatusLine().getStatusCode());
        File fileDir = new File("C:\\imgs\\main");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File imgFile = new File("C:\\imgs\\main\\" + name + ".png");
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
        HttpResponse httpResponse = httpClientUtil.login(username, password, code);
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
