package com.myweb.smvcip;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;
import org.apache.commons.lang3.StringUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Main {

    public static boolean isLogin(MainApi mainApi) throws Exception {
        while (true) {
            String name = mainApi.getCode();
            if (name.equals("403")) return false;
            File imgFile = new File("C:\\imgs\\main\\" + name + ".png");
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
            String loginResult = mainApi.login(mainApi.getUsername(), mainApi.getPassword(), str4nu);
            if (loginResult.equals("403")) return false;
            if (StringUtils.isNotBlank(loginResult) && !loginResult.contains("验证码不正确")) {
                return true;
            }
        }
    }
}