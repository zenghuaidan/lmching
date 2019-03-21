package com.lmching.mall.utils;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;  
  
public class ZipUtils {  
    private static final int BUFFEREDSIZE = 1024;
  
    /** 
     * 解压zip格式的压缩文件到当前文件夹 
     *  
     * @param zipFileName 
     * @throws Exception 
     */    
    public static synchronized void unzipFile(String zipFileName) throws Exception {  
    	unzip(zipFileName, new File(zipFileName).getParent());
    }  
  
    /** 
     * 解压zip格式的压缩文件到指定位置 
     *  
     * @param zipFileName 
     *            压缩文件 
     * @param extPlace 
     *            解压目录 
     * @throws Exception 
     */  
    @SuppressWarnings("rawtypes")
	public static synchronized void unzip(String zipFileName, String extPlace)  
            throws Exception {  
    	File f = new File(zipFileName);  
    	ZipFile zipFile = new ZipFile(zipFileName);  
        try {  
            (new File(extPlace)).mkdirs();  
            if ((!f.exists()) && (f.length() <= 0)) {  
                throw new Exception("zip file not exists!");  
            }  
            String strPath, gbkPath, strtemp;  
            File tempFile = new File(extPlace);  
            strPath = tempFile.getAbsolutePath();  
            java.util.Enumeration e = zipFile.getEntries();  
            while (e.hasMoreElements()) {  
                org.apache.tools.zip.ZipEntry zipEnt = (ZipEntry) e  
                        .nextElement();  
                gbkPath = zipEnt.getName();  
                if (zipEnt.isDirectory()) {  
                    strtemp = strPath + File.separator + gbkPath;  
                    File dir = new File(strtemp);  
                    dir.mkdirs();  
                    continue;  
                } else {  
                    // 读写文件  
                    InputStream is = zipFile.getInputStream(zipEnt);  
                    BufferedInputStream bis = new BufferedInputStream(is);  
                    gbkPath = zipEnt.getName();  
                    strtemp = strPath + File.separator + gbkPath;  
  
                    // 建目录  
                    String strsubdir = gbkPath;  
                    for (int i = 0; i < strsubdir.length(); i++) {  
                        if (strsubdir.substring(i, i + 1).equalsIgnoreCase("/")) {  
                            String temp = strPath + File.separator  
                                    + strsubdir.substring(0, i);  
                            File subdir = new File(temp);  
                            if (!subdir.exists())  
                                subdir.mkdir();  
                        }  
                    }  
                    FileOutputStream fos = new FileOutputStream(strtemp);  
                    BufferedOutputStream bos = new BufferedOutputStream(fos);  
                    int c;  
                    while ((c = bis.read()) != -1) {  
                        bos.write((byte) c);  
                    }  
                    bos.close();  
                    fos.close();  
                }  
            }  
        } catch (Exception e) {  
            e.printStackTrace();  
            throw e;  
        } finally {
			zipFile.close();
		} 
    }
  
    /** 
     * 压缩zip格式的压缩文件 
     *  
     * @param inputFilename 
     *            压缩的文件或文件夹及详细路径 
     * @param zipFilename 
     *            输出文件名称及详细路径 
     * @throws IOException 
     */  
    public static synchronized void zip(String inputFilename, String zipFilename)  
            throws IOException {  
        zip(new File(inputFilename), zipFilename);  
    }  
  
    /** 
     * 压缩zip格式的压缩文件 
     *  
     * @param inputFile 
     *            需压缩文件 
     * @param zipFilename 
     *            输出文件及详细路径 
     * @throws IOException 
     */  
    public static synchronized void zip(File inputFile, String zipFilename)  
            throws IOException {  
        ZipOutputStream out = new ZipOutputStream(new FileOutputStream(  
                zipFilename));  
        try {  
            zip(inputFile, out, "");  
            System.out.println(zipFilename + " was genrated successful....");  
        } catch (IOException e) {  
            throw e;  
        } finally {  
            out.close();  
        }  
    }  
  
    /** 
     * 压缩zip格式的压缩文件 
     *  
     * @param inputFile 
     *            需压缩文件 
     * @param out 
     *            输出压缩文件 
     * @param base 
     *            结束标识 
     * @throws IOException 
     */  
    private static synchronized void zip(File inputFile, ZipOutputStream out,  
            String base) throws IOException {  
        System.out.println("compress " + inputFile + " ...");  
        if (inputFile.isDirectory()) {  
            File[] inputFiles = inputFile.listFiles();  
            out.putNextEntry(new ZipEntry(base + "/"));  
            base = base.length() == 0 ? "" : base + "/";  
            for (int i = 0; i < inputFiles.length; i++) {  
                System.out.println("compress file:" + inputFiles[i].getName());  
                zip(inputFiles[i], out, base + inputFiles[i].getName());  
            }  
        } else {  
            if (base.length() > 0) {  
                out.putNextEntry(new ZipEntry(base));  
            } else {  
                out.putNextEntry(new ZipEntry(inputFile.getName()));  
            }  
            FileInputStream in = new FileInputStream(inputFile);  
            try {  
                int c;  
                byte[] by = new byte[BUFFEREDSIZE];  
                while ((c = in.read(by)) != -1) {  
                    out.write(by, 0, c);  
                }  
            } catch (IOException e) {  
                throw e;  
            } finally {  
                in.close();  
            }  
        }  
    }  
  
     public static void main(String[] args) {  
	     try {	    	 	    	 
	    	 String mobileFolder = args[0];//"C:/Users/larry/Downloads";
	    	 File mobileFolderFile = new File(mobileFolder);
	    	 Collection<File> files = FileUtils.listFiles(mobileFolderFile, new String[] { "lastupdate" }, false);
	    	 if (files.size() > 0) {
	    		 for(File file : files) {	    			 
	    			 String zipFolder = FilenameUtils.concat(mobileFolder, FilenameUtils.getBaseName(file.getName()));
	    			 File zipFolderFile = new File(zipFolder);
	    			 String zipFile = zipFolder + ".zip";
	    			 if (zipFolderFile.exists() && !new File(zipFile).exists()) {
	    				 ZipUtils.zip(zipFolder, zipFile);
	    				 FileUtils.forceDelete(zipFolderFile);
	    			 }
	    			 break;
	    		 }
	    	 }
	    	 
	     } catch (Exception e) {  
	    	 e.printStackTrace();  
	     }  
     }  
}  
