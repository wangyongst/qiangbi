package com.myweb.smvcip.refresh;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Date;

public class Refresh {
    public static boolean refresh(RefreshApi refreshApi, String username, String password) throws Exception {
        if (!isLogin(refreshApi, username, password)) return false;
        while (true) {
            Thread.sleep(100);
            long start = new Date().getTime();
            System.out.println(refreshApi.refresh());
            long end = new Date().getTime();
            System.out.println("账号 " + username + " 刷新成功，用时 " + (end - start) + "ms");
        }
    }

    public static boolean isLogin(RefreshApi refreshApi, String username, String password) throws Exception {
        while (true) {
            Thread.sleep(100);
            String name = refreshApi.getCode();
            if (name.equals("403")) return false;
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
            // String loginResult = smvcipApi.login("rlh003", "yhxt123456", str4nu);
            String loginResult = refreshApi.login(username, password, str4nu);
            if (loginResult.equals("403")) return false;
            if (StringUtils.isNotBlank(loginResult) && !loginResult.contains("验证码不正确")) {
                return true;
            }
        }
    }
}