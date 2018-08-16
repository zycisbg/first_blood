package com.fb.util;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ChartGraphics {
    private BufferedImage image;
    private int imageWidth = 614;  //图片的宽度
    private int imageHeight = 894; //图片的高度
    //生成图片文件
    @SuppressWarnings("restriction")
    public void createImage(String fileLocation) {
        BufferedOutputStream bos = null;
        if(image != null){
            try {
                FileOutputStream fos = new FileOutputStream(fileLocation);
                bos = new BufferedOutputStream(fos);

                JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
                encoder.encode(image);
                bos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }finally{
                if(bos!=null){//关闭输出流
                    try {
                        bos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public void graphicsGeneration(String name, String phone, String logoUrl, String imgurl,String targetUrl) {



        image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
        //设置图片的背景色
        Graphics2D main = image.createGraphics();
        main.setColor(Color.white);
        main.fillRect(0, 0, imageWidth, imageHeight);


        //***********************插入logo
        Graphics mainPic = image.getGraphics();
        BufferedImage bimg = null;
        try {
            bimg = javax.imageio.ImageIO.read(new java.io.File(logoUrl));
        } catch (Exception e) {}

        if(bimg!=null){
            mainPic.drawImage(bimg, 237, 50, 140, 140, null);
            mainPic.dispose();
        }

        //推荐人姓名
        Graphics referrer = image.createGraphics();
        //设置字体颜色，先设置颜色，再填充内容
        referrer.setColor(Color.black);
        //设置字体
        Font referrerFont = new Font("宋体", Font.PLAIN, 36);
        referrer.setFont(referrerFont);

        referrer.drawString(name, 252 , 260);


        //邀请码
        Graphics referrerNo = image.createGraphics();
        //设置字体颜色，先设置颜色，再填充内容
        referrerNo.setColor(Color.black);
        //设置字体
        Font referrerNoFont = new Font("宋体", Font.PLAIN, 20);
        referrer.setFont(referrerNoFont);
        referrer.drawString("邀请码:"+phone, 205 , 320);

        //***********************插入二维码
        Graphics QRCode = image.getGraphics();
        BufferedImage codeImg = null;
        try {
            codeImg = javax.imageio.ImageIO.read(new java.io.File(imgurl));
        } catch (Exception e) {}

        if(codeImg!=null){
            QRCode.drawImage(codeImg, 107, 380, 400, 400, null);
            QRCode.dispose();
        }

        createImage(targetUrl);

    }

    public static void main(String[] args) {
        ChartGraphics cg = new ChartGraphics();
        try {
            cg.graphicsGeneration("张老三", "13830026501", "E:\\login_logo.png", "E:\\new.png","E:\\jeje.png");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}