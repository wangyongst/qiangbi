package com.myweb.smvcip;


import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.util.ImageHelper;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class QiangBi {
    public static void main(String[] args) throws Exception {
        SmvcipApi smvcipApi = new SmvcipApi();
        smvcipApi.getCode();
        File imgFile = new File("E:\\GitHub\\qiangbi\\imgs\\0000.png");
        ITesseract instance = new Tesseract();
        BufferedImage bi = ImageIO.read(imgFile);
        BufferedImage textImage = ImageHelper.convertImageToGrayscale(ImageHelper.getSubImage(bi, 0, 0, bi.getWidth(), bi.getHeight()));
        textImage = ImageHelper.convertImageToBinary(textImage);
        textImage = ImageHelper.getScaledInstance(textImage, bi.getWidth() * 20, bi.getHeight() * 20);
        String result = instance.doOCR(textImage);
        System.out.println(result);
    }
}