import java.io.*;
import java.sql.SQLOutput;
import java.io.FileWriter;

public class ImportExecl {
    public static void main(String args[]){
        readLine("C:\\Users\\Lucky-Chi\\Desktop\\dc");
    }
    public  static void readLine(String filePath) {
        try {
            File file = new File(filePath);
            if (file.isDirectory()) {
                String[] filelist = file.list();
                for (int i = 0; i < filelist.length; i++) {
                    System.out.println(filelist.length+filelist[i]);
                    String y=null;
                    String m = "\t\t\t短期借款\t应付账款\t预收账款 \t货币资金\t财务费用\t营外收入\t营外支出\t净资产收益率\t毛利率\t资产负债率\n";
                    m=m+"\r\n";
                    String name=null;
                    String dm=null;
                    String data;
                    data=filelist[i].substring(0,filelist[i].lastIndexOf("."));
                    File readfile = new File(filePath + "\\" + filelist[i]);
                    InputStreamReader isr = new InputStreamReader(new FileInputStream(readfile), "UTF-8");
                    LineNumberReader br = new LineNumberReader(isr);
                    String lineTxt = null;
                    int k = 0;
                    FileWriter writer = null;
                    while ((lineTxt = br.readLine()) != null) {
                        String con = lineTxt;
                        String b1=":    ";
                        StringBuffer sb1 = new StringBuffer(b1);
                        sb1.insert(0,data);
                        b1=sb1.toString();
                        StringBuffer sb = new StringBuffer(con);

                        //con=sb.toString();

                        if (br.getLineNumber() <= 1) {
                                continue;
                        }else  {
                            dm = con.substring(0, 9);
                            name=con.substring(10,14);
                            name=name.trim();
                            k = br.getLineNumber() + 1;
                            String f = String.format("C:\\Users\\Lucky-Chi\\Desktop\\dcc\\%s.txt",dm+name);

                            //  String f1 = String.format("C:\\Users\\Lucky-Chi\\Desktop\\dcc\\%s\\%s.txt",dm,dm);
                            File mkfile = new File(f);
                           // File mkfile2 = new File(f1);
                            if (!mkfile.exists()) {
                                //mkfile.mkdirs();
                                mkfile.createNewFile();
                                sb.insert(0,b1);
                                con=sb.toString();
                                con=con+"\r\n";
                                System.out.println(con);
                                OutputStream out=null;
                                out=new FileOutputStream(mkfile);
                                byte b2[]=m.getBytes();
                                out.write(b2);
                                byte b[]=con.getBytes();
                                out.write(b);

                                out.close();
                            }else{
                                sb.insert(0,b1);
                                con=sb.toString();
                                con=con+"\r\n";
                                System.out.println(con);
                                writer = new FileWriter(f, true);
                                writer.write(con);
                                writer.close();
                            }

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