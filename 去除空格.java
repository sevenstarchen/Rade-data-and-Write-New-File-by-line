import java.io.IOException;
import java.util.*;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class test {
    //全局变量
    static String banName = null;
    static String kaipan = null;
    static String shoupan = null;
    public static class Map extends Mapper<Object, Text, Text, Text> {
        public void map(Object key, Text value, Context context) throws IOException, InterruptedException {


            String value_clean = value.toString();
            value_clean = value_clean.replaceAll("\\s+", " ");//去掉多余空格
            if (Objects.equals(key.toString(), "0")) {
                String[] parts = value_clean.split(" ");
                System.out.println(parts.length);
                banName = parts[0];
            } else if (Objects.equals(key.toString(), "1")) {
            } else if (Objects.equals(key.toString(), "2")) {
            } else {
                String[] parts1 = value_clean.split(" ");
                System.out.println(parts1.length);
                kaipan = parts1[1];
                shoupan = parts1[4];
                context.write(new Text(banName), new Text(kaipan + "+" + shoupan));
            }

        }
    }

    public static class Reduce extends Reducer<Text, Text, Text, Text> {
        public void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
            Iterator iterator = values.iterator();
            List<String> kaipan = new ArrayList<String>();
            List<String> shoupan = new ArrayList<String>();
            while (iterator.hasNext()) {
                String record = iterator.next().toString();
                String[] parts = record.split("+");
                kaipan.add(parts[0]);
                shoupan.add(parts[1]);
            }
            double sum = 0;
            for (String string : kaipan) {
                double value = Double.parseDouble(string);  //string是保留两位小数，所以转成double型，最开始犯了错误转成int所以一直报错
                sum = sum + value;
            }
            double kaipan_avg = sum / (kaipan.size());

            double sum1 = 0;
            for (String string : shoupan) {
                double value = Double.parseDouble(string);
                sum1 = sum1 + value;
            }
            double shoupan_avg = sum1 / (shoupan.size());
            String kaipan_avg_shoupan_avg = "开盘平均价：" + kaipan_avg + "收盘平均价:" + shoupan_avg;
            System.out.println(key+"    "+kaipan_avg_shoupan_avg);
            context.write(key, new Text(kaipan_avg_shoupan_avg));
        }
    }

    public static void main(String[] args) throws Exception {
        // TODO Auto-generated method stub
        Configuration conf = new Configuration();
        conf.set("fs.default.name", "hdfs://localhost:9000");
        String[] otherArgs = new String[]{"output/export3", "output2"};
        if (otherArgs.length != 2) {
            System.err.println("Usage: wordcount <in><out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "Average");
        job.setJarByClass(test.class);
        job.setMapperClass(Map.class);
        job.setReducerClass(Reduce.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);
        FileInputFormat.addInputPath(job, new Path(otherArgs[0]));
        FileOutputFormat.setOutputPath(job, new Path(otherArgs[1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
