package com.myweb.smvcip.refresh;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.InputStream;

public class Refresh {
    private String username;
    private RefreshApi refreshApi;

    public Refresh(String username, String password, int i){
        this.username = username;
        try {
            refreshApi = new RefreshApi(username, password, i);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public int refresh() throws Exception {
        String refresh = refreshApi.refresh();
        if (refresh.length() == 3) return Integer.parseInt(refresh);
        else if (refresh.contains("买入")) {
            System.out.println(refresh);
        } else if (refresh.contains("/index.php/login/logincl") && refresh.contains("请输入登陆账号")) {
            return 3;
        } else if (refresh.contains("频繁操作")) return 2;
        return 1;
    }

    public int login() throws Exception {
            InputStream imgInput = refreshApi.getCode();
            if (imgInput == null) return 0;
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
            if (str4nu.length() != 4) return 1;
            String login = refreshApi.login(str4nu);
            if (login.length() == 3) return Integer.parseInt(login);
            if (login.contains("频繁登陆")) return 2;
            else if (login.contains("验证码不正确")) return 3;
            else System.out.println(login);
            return 9;
    }
}
