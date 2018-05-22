package com.myweb.smvcip.refresh;


import com.myweb.Start;
import com.myweb.smvcip.ProxyUtils;
import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Refresh {
    public static boolean refresh(RefreshApi refreshApi, String username, String password) throws Exception {
        while (true) {
            long start = new Date().getTime();
            String resout = refreshApi.refresh();
            if (resout.equals("403")) {
                System.out.println("代理IP被禁");
                continue;
            }
            if (resout.contains("买入")) {
                System.out.println(resout);
            } else if (resout.contains("/index.php/login/logincl") && resout.contains("请输入登陆账号")) {
                return false;
            }
            long end = new Date().getTime();
            System.out.println( new SimpleDateFormat("HH:mm:ss").format(new Date())+" 账号 " + username + " 刷新成功，用时 " + (end - start) + "ms");
        }
    }

    public static boolean isLogin(RefreshApi refreshApi, String username, String password) throws Exception {
        while (true) {
            String name = refreshApi.getCode();
            if (name.equals("403")) {
                System.out.println("代理IP被禁");
                continue;
            }
            ;
            if (name.length() == 3) continue;
            File imgFile = new File("C:\\imgs\\refresh\\" + name + ".png");
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
            String loginResult = refreshApi.login(username, password, str4nu);
            if (loginResult.equals("403")) {
                System.out.println("代理IP被禁");
                continue;
            }
            if(loginResult.endsWith("302")){
                continue;
            }
            if (StringUtils.isNotBlank(loginResult) && !loginResult.contains("验证码不正确")) {
                System.out.println(loginResult);
                return true;
            }
        }
    }
}