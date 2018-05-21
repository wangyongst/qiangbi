package com.myweb.smvcip;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.config.RequestConfig;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QiangBi {
    public static void main(String[] args) {
        System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.SimpleLog");
        System.setProperty("org.apache.commons.logging.simplelog.showdatetime", "true");
        System.setProperty("org.apache.commons.logging.simplelog.log.org.apache.commons.httpclient", "info");// "stdout"为标准输出格式，"debug"为调试模式
        SmvcipApi smvcipApi = null;
        try {
            smvcipApi = new SmvcipApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isLogin = false;
        try {
            isLogin = isLogin(smvcipApi,ProxyUtils.getProxy());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isLogin) {
            int cout = 0;
            while (true) {
                try {
                    System.out.println("_________________________" + cout++ + "_____________________________________________");
                    System.out.println(smvcipApi.refresh(ProxyUtils.getProxy()));
                    System.out.println("__________________________" + cout++ + "_____________________________________");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }


    public static boolean isLogin(SmvcipApi smvcipApi,RequestConfig requestConfig) throws Exception {
        for (int i = 0; i < 100; i++) {
            smvcipApi.getCode(requestConfig);
            File imgFile = new File("C:\\Users\\Administrator\\Documents\\GitHub\\qiangbi\\imgs\\0000.png");
            ITesseract instance = new Tesseract();
            BufferedImage bi = ImageIO.read(imgFile);
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
            String loginResult = smvcipApi.login("rlh003", "yhxt123456", str4nu,requestConfig);
            if (StringUtils.isNotBlank(loginResult) && !loginResult.contains("验证码不正确")) {
                return true;
            }
        }
        return false;
    }
}