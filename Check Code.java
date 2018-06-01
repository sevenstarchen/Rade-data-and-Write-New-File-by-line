package com.company;
import java.io.LineNumberReader;
import java.io.*;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.*;

public  class Main {
    public  static int[] readLine(String filePath) {
        String pat = "[()\\[\\]!=0-9]";//正则表达式
        String pat1 = "[\\\\:\";]";//需要转义的正则表达式
        int k=0;
        int j=0;
        int a[]=new int[2];
        a[0]=a[1]=99999;
        try {
            File file = new File(filePath);
            if (!file.isDirectory()) {
                    File readfile = new File(filePath );
                    InputStreamReader isr = new InputStreamReader(new FileInputStream(readfile), "utf-8");
                    LineNumberReader br = new LineNumberReader(isr);
                    String lineTxt = null;
                    int count = 0;
                    int count2 = 0;
                    while ((lineTxt = br.readLine()) != null) {
                        String con = lineTxt;
                        con = con.replaceAll(pat, " ").replaceAll(pat1, " ").toString().trim();
                        if(con.contains("{")&&br.getLineNumber()>=a[0]){
                            k++;
                        }else if(con.contains("}")&&br.getLineNumber()>=a[0]){
                            j++;
                        }
                       // System.out.println("k:"+k+" j:"+j);
                        //第一遍读取主函数所在的行数上限和下限
                       if (con.equals("public static void main String   args {")) {
                            count = br.getLineNumber();
                         //  System.out.println(count + "count");
                              a[0]=count;
                              //return count;
                        } else if (con.equals("}")&&j==k+1) {
                            count2 = br.getLineNumber();
                               // System.out.println(count2 + "count2");
                                a[1]=count2;
                                break;

                        }

                    }
                br.close();

            }else{
                    System.out.println("文件不存在!");
                }
        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }
        return a;
    }


    public static void checkCode(String filePath) {
        HashMap<String, String> map = new HashMap<String, String>();
        List<String>al= new ArrayList<String>();
        String pat = "[(){\\[\\]!=}0-9]";//正则表达式
        String pat1 = "[\\\\:\";]";//需要转义的正则表达式
        String tr;
        int a[]=new int[2];
        int z=0;
        List[] ls = new ArrayList[1000];
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    System.out.println(filelist[i]);
                    File readfile = new File(filePath + "\\" + filelist[i]);
                    InputStreamReader isr = new InputStreamReader(new FileInputStream(readfile), "utf-8");
                    LineNumberReader br = new LineNumberReader(isr);
                    String lineTxt = null;
                    int count = 0;
                    int count2 = 0;
                    int b[]=readLine(readfile.toString());
                    //System.out.println(b[1]);
                    int t=0;
                    while ((lineTxt = br.readLine()) != null) {
                        t++;
                        if (t < b[1] && t > b[0]) {
                            String con = lineTxt;
                            //  System.out.println(con+":con");
                            con = con.replaceAll(pat, " ").replaceAll(pat1, " ").toString().trim();
                            // System.out.println("con:"+con);
                            //第一遍读取主函数所在的行数上限和下限
                            String[] con2 = null;
                            con2 = con.split("\\ ");
                            for (int i1 = 0; i1 < con2.length; i1++) {
                                if (!con2[i1].equals("")) {
                                    //   System.out.println(con2[i1]);
                                    // map.put(con2[i], "1");
                                    al.add(con2[i1]);

                                }
                            }
                        }
                    }
                    z=i;
                    ls[i] = new ArrayList();
                    String []m=new String[1000];
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    tr = String.join(",", al).toString();
                    m=tr.split(",");
                    for(int p=0;p<m.length;p++){
                        //System.out.println(m[p]);
                        ls[i].add(m[p]);
                    }
                   // System.out.println(ls[i].size());
                    //List<String> result = Arrays.asList(tr.split(","));
                /*   for(int p=0;p<ls[i].size();p++) {
                        System.out.println(ls[i].get(p));
                    }*/
                    md.update(tr.getBytes());
                    BigInteger bigInt = new BigInteger(1, md.digest());
                  //  System.out.println(tr);
                    //System.out.println("MD5:" + bigInt);
                    al.clear();
                    br.close();

                }
            }else{
                System.out.println("文件不存在!");
            }int count=0;
        for(int i=0;i<z;i++){
                for(int j=1;j<=z;j++){
                    for(int x=0;x<ls[i].size();x++) {
                        for(int c=0;c<ls[j].size();c++) {
                            if (ls[i].get(x).equals(ls[j].get(c))) {
                                    count++;
                                    break;
                                }
                            }
                        }
                    float num=(float)count/(float)ls[i].size();
                    if(num*100>90){
                        float fNumC = 0.0F;
                        fNumC = (float)count/ls[i].size();
                        System.out.println("涉嫌抄袭：");
                        System.out.println("相似度："+num*100+"%");
                    }else
                        System.out.println("相似度："+num*100+"%");
                }
        }

        } catch (Exception e) {
            System.out.println("文件读取错误!");
        }
    }
        public static void main(String[] args) {
        String f="C:\\\\Users\\\\admin\\\\Desktop\\\\test";
        checkCode(f);
    }
}