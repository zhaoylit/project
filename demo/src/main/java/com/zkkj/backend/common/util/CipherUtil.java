package com.zkkj.backend.common.util;

import java.io.File;

public class CipherUtil {
	/**
	 * 给zip进行数字签名
	 * 
	 * @param inputFile
	 *            要签名的文件
	 * @param publicKeyFile
	 *            把生成的公钥存在此文件，需要把此文件拷到sd卡，最好再读取其值到数据库
	 * @param privateKeyFile
	 *            保存生成的私钥，最好再读取其值存到数据库
	 * @param md5File
	 *            zip包生成的明文散列值，会在同一目录产生.encrypt的加密文件，需要把加密文件也考到sd卡
	 * @throws Exception
	 */
	public static void signature(String inputFile, String publicKeyFile,
			String privateKeyFile, String md5File) throws Exception {
		// 1. 第一步： 对原始内容进行散列(MD5算法)
		MD5Util md5 = new MD5Util();
		byte[] srcContentBytes = md5.getFileBytes(inputFile); // 获取原始内容字节流。test.txt是要签名的内容文件
		String md5Str = md5.getMD5(srcContentBytes); // MD5散列
		md5.saveString2File(md5Str, md5File);
		// 2. 第二步：得到私匙和公匙对
		RSAUtil rsa = new RSAUtil();
		rsa.initKey();
		rsa.savePublicKey(new File(publicKeyFile));
		rsa.savePrivateKey(new File(privateKeyFile));
		// 3. 第三步：使用私匙对散列内容加密。并把加密后的内容保存到文件 “散列文件.encrypt”
		// 3.1 然后，加密者把 原始内容文件+加密后的散列内容文件+公匙 发给解密者
		rsa.encryptFile(new File(md5File));

	}

	/**
	 * 比较加密过的MD5值是否和对zip文件重新散列的值，相等则说明文件来源合法
	 * 
	 * @param inputFile
	 * @param publicKeyFile
	 * @param md5File
	 * @return
	 */
	public static boolean validate(String inputFile, String publicKeyFile,
			String md5File) {
		try {
			// 1.获取zip文件的MD5值
			MD5Util md5 = new MD5Util();
			byte[] srcContentBytes = md5.getFileBytes(inputFile); 
			String md5Str = md5.getMD5(srcContentBytes);
            //2.根据公钥文件解密sdk上的服务器端生成的MD5值  
			RSAUtil rsa = new RSAUtil();
			rsa.getPublicKey(new File(publicKeyFile));
			String newStr = new String(rsa.decrypt(md5.getFileBytes(md5File)));
			//3.比较2个MD5值是否相等，相等表示zip包来源可信赖
			if (md5Str.equals(newStr.trim()))
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;

	}

	public static void main(String[] args) throws Exception {

		/*
//		 * CipherUtil.signature("G:/Work/test.zip","G:/Work/public.key",
		 * "G:/Work/private.key","G:/Work/test.md5.txt");
		 */
		 CipherUtil.signature("D:/zip/test.zip","D:/zip/public.key","D:/zip/private.key","D:/zip/test.md5.txt");
		 
//		 System.out.println(CipherUtil.validate("G:/Work/test.zip",
//				"G:/Work/public.key", "G:/Work/test.md5.txt.encrypt"));
		
	}
}
