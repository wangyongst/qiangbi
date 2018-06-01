package com.myweb.smvcip.refresh;

import com.myweb.smvcip.utils.HttpClientUtils;
import com.myweb.smvcip.utils.Result;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

public class RefreshApi {

    public static Result getCode(String username) throws Exception {
        Result result = new Result();
        HttpResponse httpResponse = HttpClientUtils.get("https://www.smcvip.com/index.php/login/verify", username);
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            result.setCode(httpResponse.getStatusLine().getStatusCode());
            return result;
        }
        HttpEntity entity = httpResponse.getEntity();
        if (entity != null && entity.getContentType().getValue().equals("image/png")) {
            result.setCode(1);
            result.setInputStream(entity.getContent());
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

    public static Result refresh(String username) throws Exception {
        Result result = new Result();
        HttpResponse httpResponse = HttpClientUtils.get("https://www.smcvip.com/Works/IBOT", username);
        if (httpResponse.getStatusLine().getStatusCode() != 200) {
            result.setCode(httpResponse.getStatusLine().getStatusCode());
        } else {
            result.setCode(1);
            result.setOut(EntityUtils.toString(httpResponse.getEntity(), "UTF-8"));

        }
        return result;
    }
}
