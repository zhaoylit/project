package com.zkkj.backend.common.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {
	public static void SaveFileFromInputStream(InputStream stream,String path,String savefile) throws IOException
    {      
       FileOutputStream fs=new FileOutputStream( path + "/"+ savefile);
       System.out.println("------------"+path + "/"+ savefile);
       byte[] buffer =new byte[1024*1024];
       int bytesum = 0;
       int byteread = 0; 
       while ((byteread=stream.read(buffer))!=-1)
       {
          bytesum+=byteread;
          fs.write(buffer,0,byteread);
          fs.flush();
       } 
       fs.close();
       stream.close();      
   }
   public static void deleteFile(String filePath){
	   File f = new File(filePath); // 输入要删除的文件位置
	   if(f.exists())
	   f.delete();
   }
   
}
