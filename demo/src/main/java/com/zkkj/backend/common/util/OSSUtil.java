package com.zkkj.backend.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.commons.lang.StringUtils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.ObjectMetadata;
import com.aliyun.oss.model.PutObjectResult;
import com.zkkj.backend.common.constant.ResourceType;

/**
 *@Title:阿里OSS云存储工具类
 *@Author:Todd
 *@Since:2016年6月19日
 *@Version:1.1.0
 */
public class OSSUtil {


    private static final String ACCESS_ID = "uev8B2BarASEhDqo";
    private static final String ACCESS_KEY = "nUbV7DSHxuXj936Iv0QnPiX1ELyXeM";
    private static final String ENDPOINT = "http://oss-cn-beijing.aliyuncs.com";
    private static final String IMG_BUCKET_NAME = "swanimg";
    private static final String VIDEO_BUCKET_NAME = "swanvideo";

    static OSSClient client = new OSSClient(ENDPOINT, ACCESS_ID, ACCESS_KEY);
 
    public static String getOSSFileContextPath(String resourceType){
    	
    	String bucketName = IMG_BUCKET_NAME;
    	if(ResourceType.IMAGE.name.equals(resourceType))
    		bucketName = IMG_BUCKET_NAME;
    	else if(ResourceType.VEDIO.name.equals(resourceType))
    		bucketName = VIDEO_BUCKET_NAME;
    	
    	return ENDPOINT.replace("http://", "http://"+bucketName+".");
    }
    
    /**
     * @param key 文件在阿里云上的存储名称
     * @param filepath 文件本地路径
     * @description 上传图片到阿里云OSS,上传失败则会向外抛出异常
     */
    public static void uploadImgFile( String key, String filepath) throws OSSException, ClientException, FileNotFoundException {
        File file = new File(filepath);

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
        //objectMeta.setContentType("image/jpeg");

        InputStream input = new FileInputStream(file);
        PutObjectResult p=client.putObject(IMG_BUCKET_NAME, key, input, objectMeta);
        //System.out.println(p.getETag());
        
    }
    
    /**
     * @param key 文件在阿里云上的存储名称
     * @param filepath 文件本地路径
     * @description 上传图片到阿里云OSS,上传失败则会向外抛出异常
     */
    public static void uploadImgFile( String key, File file) throws OSSException, ClientException, FileNotFoundException {
        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
        //objectMeta.setContentType("image/jpeg");

        InputStream input = new FileInputStream(file);
        PutObjectResult p=client.putObject(IMG_BUCKET_NAME, key, input, objectMeta);
        //System.out.println(p.getETag());
        
    }
    
    /**
     * @param key 文件在阿里云上的存储名称
     * @param filepath 文件本地路径
     * @description 上传图片到阿里云OSS,上传失败则会向外抛出异常
     */
    public static void uploadImgInputStream( String key, InputStream input) throws OSSException, ClientException, FileNotFoundException {
    	ObjectMetadata objectMeta = new ObjectMetadata();
    	//objectMeta.setContentLength(file.length());
    	// 可以在metadata中标记文件类型
    	//objectMeta.setContentType("image/jpeg");
    	
    	//InputStream input = new FileInputStream(file);
    	PutObjectResult p=client.putObject(IMG_BUCKET_NAME, key, input, objectMeta);
    	//System.out.println(p.getETag());
    	
    }

    /**
     * @param key 文件在阿里云上的存储名称
     * @param filepath 文件本地路径
     * @description 上传视频到阿里云OSS,上传失败则会向外抛出异常
     */
    @SuppressWarnings("unused")
    public static void uploadVideoFile(String key, String filepath) throws OSSException, ClientException, FileNotFoundException {
        File file = new File(filepath);

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(file.length());
        // 可以在metadata中标记文件类型
        //objectMeta.setContentType("image/jpeg");

        InputStream input = new FileInputStream(file);
        PutObjectResult p=client.putObject(VIDEO_BUCKET_NAME, key, input, objectMeta);
        //System.out.println(p.getETag());
        
    }
    
    
    /**
     * @param key 文件在阿里云上的存储名称
     * @param filepath 文件本地路径
     * @description 上传图片到阿里云OSS,上传失败则会向外抛出异常
     */
    public static void uploadImgFile(String key, InputStream input, long fileLength) throws OSSException, ClientException, FileNotFoundException {

        ObjectMetadata objectMeta = new ObjectMetadata();
        objectMeta.setContentLength(fileLength);
        // 可以在metadata中标记文件类型
        //objectMeta.setContentType("image/jpeg");

        PutObjectResult p=client.putObject(IMG_BUCKET_NAME, key, input, objectMeta);
        //System.out.println(p.getETag());
        
    }
    
    
    
    
    public static void main(String[] args) throws Exception {
    	OSSUtil.uploadImgFile("testimg.png", "d:/temp/photo.png");
    }
    
    

}
