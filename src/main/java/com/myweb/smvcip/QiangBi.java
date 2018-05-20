package com.myweb.smvcip;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class QiangBi {
    public static void main(String[] args){
        SmvcipApi smvcipApi = null;
        try {
            smvcipApi = new SmvcipApi();
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isLogin = false;
        try {
            isLogin = isLogin(smvcipApi);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (isLogin){
            int cout = 0;
            while (true) {
                try {
                    System.out.println("_________________________"+cout+++"_____________________________________________");
                    System.out.println(smvcipApi.refresh());
                    System.out.println("__________________________"+cout+++"_____________________________________");
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }


    public static boolean isLogin(SmvcipApi smvcipApi) throws Exception {
        for (int i = 0; i < 100; i++) {
            smvcipApi.getCode();
            File imgFile = new File("E:\\GitHub\\qiangbi\\imgs\\0000.png");
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
            String loginResult = smvcipApi.login("rlh003", "yhxt123456", str4nu);
            if (!loginResult.contains("验证码不正确")) {
                return true;
            }
        }
        return false;
    }
}