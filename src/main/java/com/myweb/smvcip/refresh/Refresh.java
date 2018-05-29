package com.myweb.smvcip.refresh;


import com.myweb.smvcip.utils.ClearImageHelper;
import com.myweb.smvcip.utils.Result;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;

import java.awt.image.BufferedImage;

public class Refresh {

    public static Result login(String username, String password) throws Exception {
        Result result = RefreshApi.getCode();
        if (result.getCode() == 1) {
            ITesseract instance = new Tesseract();
            BufferedImage textImage = ClearImageHelper.cleanImage(result.getInputStream());
            result.getInputStream().close();
            String resstr2ult = instance.doOCR(textImage).trim();
            String str4nu = "";
            for (int t = 0; t < resstr2ult.length(); t++) {
                if (resstr2ult.charAt(t) >= 48 && resstr2ult.charAt(t) <= 57) {
                    str4nu += resstr2ult.charAt(t);
                }
            }
            if (str4nu.length() != 4) {
                result.setCode(4);
                return result;
            }
            result = RefreshApi.login(username, password, str4nu);
            if (result.getCode() == 1) {
                if (result.getOut().contains("验证码不正确")) {
                    result.setCode(4);
                } else if (result.getOut().contains("频繁登陆,请稍候重试")) {
                    result.setCode(2);
                }
                return result;
            } else return result;
        } else return result;
    }
}
