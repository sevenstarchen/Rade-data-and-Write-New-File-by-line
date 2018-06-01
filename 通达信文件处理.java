package com.company;

import java.io.*;
import java.sql.SQLOutput;

public class Main {
    public static void main(String args[]){
        readLine("C:\\new_tdx\\T0002\\export");
    }
    public  static void readLine(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                   System.out.println(i + "i");
                    String y=null;
                    String m = null;
                    String name=null;
                    String d = null;
                    File readfile = new File(filePath + "\\" + filelist[i]);
                    InputStreamReader isr = new InputStreamReader(new FileInputStream(readfile), "gbk");
                    LineNumberReader br = new LineNumberReader(isr);
                    String lineTxt = null;
                    int k = 0;

                    while ((lineTxt = br.readLine()) != null) {
                        String con = lineTxt;
                        if (br.getLineNumber() <= 2) {
                            if (br.getLineNumber() == 1) {
                                String linshi = con;
                                name = con.substring(0, 11);
                            } else if (br.getLineNumber() == 2) {
                                String linshi2 = con;
                                k = br.getLineNumber() + 1;
                            }
                        }
                     if (br.getLineNumber() > 2&&!con.equals("数据来源:通达信".toString()) ) {
                            String linshi3 = con;
                           y = con.substring(0, 4);
                          m = con.substring(5, 7);
                          d = con.substring(8,10);
                           String f = String.format("C:\\迅雷下载\\New File\\%s年\\%s月%s日\\%s", y, m, d, name);
                         String f1 = String.format("C:\\迅雷下载\\New File\\%s年\\%s月%s日\\%s\\%s.txt", y, m, d, name,name);
                           File mkfile = new File(f);
                         File mkfile2 = new File(f1);
                           if (!mkfile.exists()) {
                               mkfile.mkdirs();
                               mkfile2.createNewFile();
                            }
                         OutputStream out=null;
                           out=new FileOutputStream(mkfile2);
                           byte b[]=con.getBytes();
                           out.write(b);
                           out.close();
                        }
                    }
                    br.close();
                }


            }else{
                System.out.println("文件不存在!");
            }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }
    }
}