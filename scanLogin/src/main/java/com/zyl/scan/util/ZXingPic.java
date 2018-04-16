package com.zyl.scan.util;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;

import sun.misc.BASE64Encoder;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

public class ZXingPic {
	public Map getDecodeHintType()
	{
	// 用于设置QR二维码参数
	Map hints = new HashMap();
	// 设置QR二维码的纠错级别（H为最高级别）具体级别信息
	hints.put(EncodeHintType.ERROR_CORRECTION,ErrorCorrectionLevel.H);
	// 设置编码方式
	hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
	hints.put(EncodeHintType.MAX_SIZE, 350);
	hints.put(EncodeHintType.MIN_SIZE, 100);


	return hints;
	}

	public BufferedImage fileToBufferedImage(BitMatrix bm)
	{
	BufferedImage image = null;
	try
	{
	int w = bm.getWidth(), h = bm.getHeight();
	image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);


	for (int x = 0; x < w; x++)
	{
	for (int y = 0; y < h; y++)
	{
	image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFCCDDEE);
	}
	}


	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	return image;
	}

	public BufferedImage getQR_CODEBufferedImage(String content,BarcodeFormat barcodeFormat, int width, int height, Map hints)
	{
	MultiFormatWriter multiFormatWriter = null;
	BitMatrix bm = null;
	BufferedImage image = null;
	try
	{
	multiFormatWriter = new MultiFormatWriter();


	// 参数顺序分别为：编码内容，编码类型，生成图片宽度，生成图片高度，设置参数
	bm = multiFormatWriter.encode(content, barcodeFormat, width,height, hints);


	int w = bm.getWidth();
	int h = bm.getHeight();
	image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);


	// 开始利用二维码数据创建Bitmap图片，分别设为黑（0xFFFFFFFF）白（0xFF000000）两色
	for (int x = 0; x < w; x++)
	{
	for (int y = 0; y < h; y++)
	{
	image.setRGB(x, y, bm.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
	}
	}
	}
	catch (WriterException e)
	{
	e.printStackTrace();
	}
	return image;
	}

	public void decodeQR_CODE2ImageFile(BitMatrix bm, String imageFormat, File file)
	{
	try
	{
	if (null == file || file.getName().trim().isEmpty())
	{
	throw new IllegalArgumentException("文件异常，或扩展名有问题！");
	}


	BufferedImage bi = fileToBufferedImage(bm);
	ImageIO.write(bi, "jpeg", file);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public void decodeQR_CODE2OutputStream(BitMatrix bm, String imageFormat, OutputStream os)
	{
	try
	{
	BufferedImage image = fileToBufferedImage(bm);
	ImageIO.write(image, imageFormat, os);
	}
	catch (Exception e)
	{
	e.printStackTrace();
	}
	}

	public void addLogo_QRCode(File qrPic, File logoPic, LogoConfig logoConfig)
	{
		try
		{
			if (!qrPic.isFile() || !logoPic.isFile())
			{
				System.out.print("file not find !");
				System.exit(0);
			}
		
		
		
			BufferedImage image = ImageIO.read(qrPic);
			Graphics2D g = image.createGraphics();
		
		
		
			BufferedImage logo = ImageIO.read(logoPic);
		
			int widthLogo = logo.getWidth(), heightLogo =logo.getHeight();
		
			// 计算图片放置位置
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - logo.getHeight()) / 2;
		
		
			//开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, widthLogo, heightLogo);
		
			g.dispose();
		
			ImageIO.write(image, "jpeg", new File("D:/newPic.jpg"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	public BufferedImage addLogo_QRCode(BufferedImage image,BufferedImage logoImage, LogoConfig logoConfig)
	{
		try
		{
		
			Graphics2D g = image.createGraphics();
		
		
		
			BufferedImage logo = logoImage;
		
			int widthLogo = logo.getWidth(), heightLogo =logo.getHeight();
		
			// 计算图片放置位置
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - logo.getHeight()) / 2;
		
		
			//开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, widthLogo, heightLogo);
		
			g.dispose();
			return image;
//			ImageIO.write(image, "jpeg", new File("D:/newPic.jpg"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	public BufferedImage addLogo_QRCode(BufferedImage image, File logoPic, LogoConfig logoConfig)
	{
		try
		{
		
			Graphics2D g = image.createGraphics();
		
		
		
			BufferedImage logo = ImageIO.read(logoPic);
		
			int widthLogo = logo.getWidth(), heightLogo =logo.getHeight();
		
			// 计算图片放置位置
			int x = (image.getWidth() - widthLogo) / 2;
			int y = (image.getHeight() - logo.getHeight()) / 2;
		
		
			//开始绘制图片
			g.drawImage(logo, x, y, widthLogo, heightLogo, null);
			g.drawRoundRect(x, y, widthLogo, heightLogo, 15, 15);
			g.setStroke(new BasicStroke(logoConfig.getBorder()));
			g.setColor(logoConfig.getBorderColor());
			g.drawRect(x, y, widthLogo, heightLogo);
		
			g.dispose();
			return image;
//			ImageIO.write(image, "jpeg", new File("D:/newPic.jpg"));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public void parseQR_CODEImage(File file){
		try{
			MultiFormatReader formatReader = new MultiFormatReader();
		// File file = new File(filePath);
		if (!file.exists())
		{
			return;
		}
	
		BufferedImage image = ImageIO.read(file);
		LuminanceSource source = new BufferedImageLuminanceSource(image);
		Binarizer binarizer = new HybridBinarizer(source);
		BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
	
	
		Map hints = new HashMap();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
	
	
		Result result = formatReader.decode(binaryBitmap, hints);
	
	
		System.out.println("result = " + result.toString());
		System.out.println("resultFormat = " +result.getBarcodeFormat());
		System.out.println("resultText = " + result.getText());
	}
	catch (Exception e)
	{
		e.printStackTrace();
	}
  }
	/** 
     * 将文件转成base64 字符串 
     * @param path文件路径 
     * @return  *  
     * @throws Exception 
     */  
    
    public static String encodeBase64File(InputStream in)  
    {//将图片文件转化为字节数组字符串，并对其进行Base64编码处理  
        byte[] data = null;  
        //读取图片字节数组  
        try   
        {  
            data = new byte[in.available()];  
            in.read(data);  
            in.close();  
        }   
        catch (IOException e)   
        {  
            e.printStackTrace();  
        }  
        //对字节数组Base64编码  
        BASE64Encoder encoder = new BASE64Encoder();  
        return encoder.encode(data);//返回Base64编码过的字节数组字符串  
    }  
	public static String getImagePath(String content,BufferedImage logo,int width,int height){
        try
        {
        	ZXingPic zp = new ZXingPic();
        	BufferedImage bm = null;
            bm = zp.getQR_CODEBufferedImage(content,BarcodeFormat.QR_CODE, width,height,zp.getDecodeHintType());
            if(logo != null){
                bm = zp.addLogo_QRCode(bm,logo, new LogoConfig());
            }
            
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageOutputStream imageOutput = ImageIO.createImageOutputStream(byteArrayOutputStream);
            ImageIO.write(bm, "png", imageOutput);
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            
            
            return encodeBase64File(inputStream);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
		return "";
	}
	public static String getImagePath(String content,File logoFile,int width,int height){
        try
        {
        	ZXingPic zp = new ZXingPic();
        	BufferedImage bm = null;
            bm = zp.getQR_CODEBufferedImage(content,BarcodeFormat.QR_CODE, width,height,zp.getDecodeHintType());
            if(logoFile != null){
                bm = zp.addLogo_QRCode(bm,logoFile, new LogoConfig());
            }
            
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageOutputStream imageOutput = ImageIO.createImageOutputStream(byteArrayOutputStream);
            ImageIO.write(bm, "png", imageOutput);
            InputStream inputStream = new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
            
            
            return encodeBase64File(inputStream);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
		return "";
	}
	public static void main(String[]args) throws WriterException
    {
        String content = "【优秀员工】恭喜您，中奖了！！！领取方式，请拨打电话：15998099997*咨询。";
        String filePath = "D:/weibow.jpg";
 
        //if(args.length != 2)
        //{
        //System.out.println("没有内容,图片生成失败!");
        //System.exit(0);
        //}
 
        try
        {
            File file = new File(filePath);
            
            ZXingPic zp = new ZXingPic();
 
            BufferedImage bim = zp.getQR_CODEBufferedImage(content,BarcodeFormat.QR_CODE, 800,800,zp.getDecodeHintType());
 
            ImageIO.write(bim,"jpeg",file);
 
            zp.addLogo_QRCode(file,new File("D:/logo.jpg"), new LogoConfig());
             
//            Thread.sleep(5000);
//            zp.parseQR_CODEImage(new File("D:/newPic.jpg"));
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
