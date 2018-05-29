package com.myweb.smvcip.refresh;

import com.myweb.smvcip.utils.HttpClientUtils;
import com.myweb.smvcip.utils.Result;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Date;

public class RefreshApi {

    public static Result getCode() throws Exception {
        Result result = new Result();
        HttpResponse httpResponse = HttpClientUtils.get("https://www.smcvip.com/index.php/login/verify");
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            result.setCode(httpResponse.getStatusLine().getStatusCode());
            return result;
        }
        File fileDir = new File("C:\\imgs\\refresh");
        if (!fileDir.exists()) {
            fileDir.mkdirs();
        }
        File imgFile = new File("C:\\imgs\\refresh\\" + String.valueOf(new Date().getTime()) + ".png");
        if (!imgFile.exists()) {
            imgFile.createNewFile();
        }
        FileOutputStream output = new FileOutputStream(imgFile);
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null && entity.getContentType().getValue().equals("image/png")) {
            result.setCode(1);
            InputStream instream = entity.getContent();
            byte b[] = new byte[1024];
            int j = 0;
            while ((j = instream.read(b)) != -1) {
                output.write(b, 0, j);
            }
            output.flush();
            output.close();
            result.setFile(imgFile);
        } else {
            result.setCode(0);
        }
        return result;
    }

    public static Result login(String username, String password, String code) throws Exception {
        Result result = new Result();
        HttpResponse httpResponse = HttpClientUtils.login(username, password, code);
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            result.setCode(httpResponse.getStatusLine().getStatusCode());
        } else {
            result.setCode(1);
            result.setOut(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
        }
        return result;
    }

    public static Result refresh() throws Exception {
        Result result = new Result();
        HttpResponse httpResponse = HttpClientUtils.get("https://www.smcvip.com/Works/IBOT");
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            result.setCode(httpResponse.getStatusLine().getStatusCode());
        } else {
            result.setCode(1);
            result.setOut(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));
        }
        return result;
    }
}
