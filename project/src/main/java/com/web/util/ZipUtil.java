package com.web.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.zip.Zip64Mode;
import org.apache.commons.compress.archivers.zip.ZipArchiveEntry;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveOutputStream;

public class ZipUtil {
	/**
	 * zip压缩文件
	 * @param dir
	 * @param zippath
	 */
	public static void zip(String dir ,String zippath){
		List<String> paths = getFiles(dir); 
		compressFilesZip(paths.toArray(new String[paths.size()]),zippath,dir	);
	}
	/**
	 * 递归取到当前目录所有文件
	 * @param dir
	 * @return
	 */
	public static List<String> getFiles(String dir){
		List<String> lstFiles = null;		
		if(lstFiles == null){
			lstFiles = new ArrayList<String>();
		}
		File file = new File(dir);
		File [] files = file.listFiles(); 
		for(File f : files){
			if(f.isDirectory()){
				lstFiles.add(f.getAbsolutePath());
				lstFiles.addAll(getFiles(f.getAbsolutePath()));
			}else{ 
				String str =f.getAbsolutePath();
				lstFiles.add(str);
			}
		}
		return lstFiles;
	}
	
	/**
	 * 文件名处理
	 * @param dir
	 * @param path
	 * @return
	 */
	public static String getFilePathName(String dir,String path){
		String p = path.replace(dir+File.separator, "");
		p = p.replace("\\", "/");
		return p;
	}
    /**
     * 把文件压缩成zip格式
     * @param files         需要压缩的文件
     * @param zipFilePath 压缩后的zip文件路径   ,如"D:/test/aa.zip";
     */
    public static void compressFilesZip(String[] files,String zipFilePath,String dir) {
        if(files == null || files.length <= 0) {
            return ;
        }
        ZipArchiveOutputStream zaos = null;
        try {
            File zipFile = new File(zipFilePath);
            zaos = new ZipArchiveOutputStream(zipFile);
            zaos.setUseZip64(Zip64Mode.AsNeeded);
            //将每个文件用ZipArchiveEntry封装
            //再用ZipArchiveOutputStream写到压缩文件中
            for(String strfile : files) {
            	File file = new File(strfile);
                if(file != null) {
                	String name = getFilePathName(dir,strfile);
                    ZipArchiveEntry zipArchiveEntry  = new ZipArchiveEntry(file,name);
                    zaos.putArchiveEntry(zipArchiveEntry);
                    if(file.isDirectory()){
                    	continue;
                    }
                    InputStream is = null;
                    try {
                        is = new BufferedInputStream(new FileInputStream(file));
                        byte[] buffer = new byte[1024 ]; 
                        int len = -1;
                        while((len = is.read(buffer)) != -1) {
                            //把缓冲区的字节写入到ZipArchiveEntry
                            zaos.write(buffer, 0, len);
                        }
                        zaos.closeArchiveEntry(); 
                    }catch(Exception e) {
                        throw new RuntimeException(e);
                    }finally {
                        if(is != null)
                            is.close();
                    }
                     
                }
            }
            zaos.finish();
        }catch(Exception e){
            throw new RuntimeException(e);
        }finally {
                try {
                    if(zaos != null) {
                        zaos.close();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
        }
         
    }
    
   
    /**
    * 把zip文件解压到指定的文件夹
    * @param zipFilePath zip文件路径, 如 "D:/test/aa.zip"
    * @param saveFileDir 解压后的文件存放路径, 如"D:/test/" ()
    */
	public static void unzip(String zipFilePath, String saveFileDir) {
		if(!saveFileDir.endsWith("\\") && !saveFileDir.endsWith("/") ){
			saveFileDir += File.separator;
		}
		File dir = new File(saveFileDir);
		if(!dir.exists()){
			dir.mkdirs();
		}
		File file = new File(zipFilePath);
		if (file.exists()) {
			InputStream is = null; 
			ZipArchiveInputStream zais = null;
			try {
				is = new FileInputStream(file);
				zais = new ZipArchiveInputStream(is);
				ArchiveEntry archiveEntry = null;
				while ((archiveEntry = zais.getNextEntry()) != null) { 
					// 获取文件名
					String entryFileName = archiveEntry.getName();
					// 构造解压出来的文件存放路径
					String entryFilePath = saveFileDir + entryFileName;
					OutputStream os = null;
					try {
						// 把解压出来的文件写到指定路径
						File entryFile = new File(entryFilePath);
						if(entryFileName.endsWith("/")){
							entryFile.mkdirs();
						}else{
							os = new BufferedOutputStream(new FileOutputStream(
									entryFile));							
							byte[] buffer = new byte[1024]; 
	                        int len = -1; 
	                        while((len = zais.read(buffer)) != -1) {
	                        	os.write(buffer, 0, len); 
	                        }
						}
					} catch (IOException e) {
						throw new IOException(e);
					} finally {
						if (os != null) {
							os.flush();
							os.close();
						}
					}

				} 
			} catch (Exception e) {
				throw new RuntimeException(e);
			} finally {
				try {
					if (zais != null) {
						zais.close();
					}
					if (is != null) {
						is.close();
					}
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		}
	}
	
	/**
     * 把接受的全部文件打成压缩包 
     * @param List<File>;  
     * @param org.apache.tools.zip.ZipOutputStream  
     */
    public static void zipFile(List files,ZipOutputStream outputStream) {
        int size = files.size();
        for(int i = 0; i < size; i++) {
            File file = (File) files.get(i);
            zipFile(file, outputStream);
        }
    }
    public static void zipInputStreamFile(List<Map> files,ZipOutputStream outputStream) {
        if(files != null && files.size() > 0){
        	for(Map mm : files){
        		//获取文件输入流
        		InputStream inputStream = (InputStream) mm.get("inputStream");
        		String fileName= (String) mm.get("fileName");
            	zipFile(inputStream, fileName, outputStream);
        	}
        }
    }
    /**  
     * 根据输入的文件与输出流对文件进行打包
     * @param File
     * @param org.apache.tools.zip.ZipOutputStream
     */
    public static void zipFile(File inputFile,
            ZipOutputStream ouputStream) {
        try {
            if(inputFile.exists()) {
                /**如果是目录的话这里是不采取操作的，
                 * 至于目录的打包正在研究中*/
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    //org.apache.tools.zip.ZipEntry
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据   
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象   
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void zipFile(InputStream IN,String fileName,ZipOutputStream ouputStream) {
        try {
            BufferedInputStream bins = new BufferedInputStream(IN, 512);
            //org.apache.tools.zip.ZipEntry
            ZipEntry entry = new ZipEntry(fileName);
            ouputStream.putNextEntry(entry);
            // 向压缩文件中输出数据   
            int nNumber;
            byte[] buffer = new byte[512];
            while ((nNumber = bins.read(buffer)) != -1) {
                ouputStream.write(buffer, 0, nNumber);
            }
            // 关闭创建的流对象   
            bins.close();
            IN.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	public static void main(String[] args) {
		String unzipdir = "G:/Work/";
		String unzipfile = "test.zip";
		ZipUtil.unzip(unzipfile, unzipdir);
		
		System.out.println("success!");
	} 
}
