package com.myweb.smvcip;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class Main {

    public static boolean isLogin(MainApi mainApi) throws Exception {
        while (true) {
            InputStream imgInput = mainApi.getCode();
            if (imgInput == null) continue;
            ITesseract instance = new Tesseract();
            BufferedImage bi = ImageIO.read(imgInput);
            imgInput.close();
            BufferedImage textImage = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(bi, 0, 0, bi.getWidth(), bi.getHeight()));
            textImage = ImageHelper.convertImageToBinary(textImage);
            textImage = ImageHelper.getScaledInstance(textImage, bi.getWidth() * 20, bi.getHeight() * 20);
            String resstr2ult = instance.doOCR(textImage).trim();
            String str4nu = "";
            for (int t = 0; t < resstr2ult.length(); t++) {
                if (resstr2ult.charAt(t) >= 48 && resstr2ult.charAt(t) <= 57) {
                    str4nu += resstr2ult.charAt(t);
                }
            }
            if (str4nu.length() != 4) continue;
            String loginResult = mainApi.login(mainApi.getUsername(), mainApi.getPassword(), str4nu);
            if (loginResult.equals("403")) continue;
            if (StringUtils.isNotBlank(loginResult) && !loginResult.contains("验证码不正确")) {
                return true;
            }else{
                System.out.println("登录失败，验证码识别不正确！");
            }
        }
    }
}