
package my.pack.laprgm2;


import java.io.IOException;
import java.util.*;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.conf.*;
import org.apache.hadoop.io.*;
import org.apache.hadoop.mapred.*;
import org.apache.hadoop.util.*;



public class prgm2 {
//MAPPER CODE 

public static class Map extends MapReduceBase implements Mapper<LongWritable, Text, Text, IntWritable> {



public void map(LongWritable key, Text value, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException {
String myvalue = value.toString() ;
String[] transactiontokens = myvalue.split(",") ;
if(transactiontokens[2].contentEquals("Suspense")) {
output.collect(new Text("Total Number of positive feedbacks in Suspense Genere :"), new IntWritable(Integer.parseInt(transactiontokens[3])));
}
}
}

//REDUCER CODE 
public static class Reduce extends MapReduceBase implements Reducer<Text, IntWritable, Text, IntWritable> {
public void reduce(Text key, Iterator<IntWritable> values, OutputCollector<Text, IntWritable> output, Reporter reporter) throws IOException { 
int sum=0 ;
while (values.hasNext()) {
IntWritable value = (IntWritable) values.next() ;
sum += value.get();
}
output.collect(key, new IntWritable(sum));
} 
}


//DRIVER CODE
public static void main(String[] args) throws Exception {
JobConf conf = new JobConf(prgm2.class);
conf.setJobName("Counting the total number of positive feedback in Suspense Genere ");
conf.setOutputKeyClass(Text.class);
conf.setOutputValueClass(IntWritable.class);
conf.setMapperClass(Map.class);
conf.setCombinerClass(Reduce.class);
conf.setReducerClass(Reduce.class);
conf.setInputFormat(TextInputFormat.class);
conf.setOutputFormat(TextOutputFormat.class); // hadoop jar jarname classpath inputfolder outputfolder
FileInputFormat.setInputPaths(conf, new Path(args[0]));
FileOutputFormat.setOutputPath(conf, new Path(args[1]));
JobClient.runJob(conf); 
}
}


