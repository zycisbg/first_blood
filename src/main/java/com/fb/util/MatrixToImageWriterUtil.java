package com.fb.util;

import com.google.zxing.common.BitMatrix;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 创建二维码
 * @author java
 *
 */
public class MatrixToImageWriterUtil {
	   private static final int BLACK = 0xFF000000; 
	   private static final int WHITE = 0xFFFFFFFF; 
	    
	   private MatrixToImageWriterUtil() {} 
	    
	   //创建二维码图片   
	   public static BufferedImage toBufferedImage(BitMatrix matrix) { 
	     int width = matrix.getWidth(); 
	     int height = matrix.getHeight(); 
	     BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB); 
	     for (int x = 0; x < width; x++) { 
	       for (int y = 0; y < height; y++) { 
	         image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE); 
	       } 
	     } 
	     return image; 
	   } 
	    
	   //将生成的二维码文件保存到本地   
	   public static void writeToFile(BitMatrix matrix, String format, File file) 
	       throws IOException { 
	     BufferedImage image = toBufferedImage(matrix); 
	     if (!ImageIO.write(image, format, file)) { 
	       throw new IOException("Could not write an image of format " + format + " to " + file); 
	     } 
	   } 
	    
	   //将二维码文件写到流中   
	   public static void writeToStream(BitMatrix matrix, String format, OutputStream stream) 
	       throws IOException { 
	     BufferedImage image = toBufferedImage(matrix); 
	     if (!ImageIO.write(image, format, stream)) { 
	       throw new IOException("Could not write an image of format " + format); 
	     } 
	   } 
	    
}