package com.test.SparkTest;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;

import scala.Tuple2;

public class SparkTest {
	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setMaster("local").setAppName("My App");
		JavaSparkContext sc = new JavaSparkContext(conf);
		
		/**
		 * 集群URL：告诉spark如何连接到集群中，local这个特殊值可以让spark运行在单机单线程上而无需连接到集群。
		 * 应用名：当连接到一个集群时，这个值可以帮助你在集群管理器的用户界面中找到你的应用。
		 * 
		 */
		
		//读取我们的输入数据
		JavaRDD<String> input = sc.textFile("C:/Users/ZYL_PC/Desktop/spark学习笔记.txt");
		//切分为单词
		JavaRDD<String> words = input.flatMap(new FlatMapFunction<String, String>() {
			@Override
			public Iterator<String> call(String x) throws Exception {
				// TODO Auto-generated method stub
				return (Iterator<String>) Arrays.asList(x.split(" "));
				
			}
		});
		//转换为键值对并计数
		JavaPairRDD<String,Integer> counts = words.mapToPair(new PairFunction<String, String,Integer>() {
			@Override
			public Tuple2<String, Integer> call(String x) throws Exception {
				// TODO Auto-generated method stub
				return new Tuple2(x,1);
			}
			
		}).reduceByKey(new Function2<Integer, Integer, Integer>(){
			@Override
			public Integer call(Integer x, Integer y) throws Exception {
				// TODO Auto-generated method stub
				return x + y;
			}
		});
		//把统计出来的单词总数放存入一个文本文件，引发求值
		counts.saveAsTextFile("C:/Users/ZYL_PC/Desktop/求值.txt");
		
		JavaSparkContext context = new JavaSparkContext();
		
		//创建RDD，把已有的集合传给SparkContext的parallelize()方法
		JavaRDD<String> lines = sc.parallelize(Arrays.asList("pandas","i like pandas"));
		//从外部存储中读取数据来创建RDD
		//JavaRDD<String> lines = sc.textFile("");
		
		//RDD的两种操作，转化和行动操作
		//RDD的转化操作总是返回一个新的RDD,比如map和filter()，而行动操作则是向驱动器返回结果或把结果写入外部系统的操作
		
		
		//用java实现filter()转化操作
		JavaRDD<String> inputRDD = sc.textFile("log.txt");
		//将RDD转化为只包含错误信息的RDD，不会改变已有RDD inputRDD的值
		JavaRDD<String> errorRDD = inputRDD.filter(new Function<String, Boolean>() {
			@Override
			public Boolean call(String x) throws Exception {
				// TODO Auto-generated method stub
				return x.contains("error");
			}
		});
		//将RDD转化为只包含警告信息的RDD，
		JavaRDD<String> warningsRDD = inputRDD.filter(new Function<String, Boolean>() {
			@Override
			public Boolean call(String x) throws Exception {
				// TODO Auto-generated method stub
				return x.contains("warning");
			}
		});
		//将只包含错误信息的RDD和只包含警告信息的RDD合并转化为新的RDD
		JavaRDD<String> badLinesRDD = errorRDD.union(inputRDD);
		
		//通过转化操作，可以从已有的RDD中派生出新的RDD
		
		
		//RDD行动操作，会吧最终求得的结果返回到驱动器或者写入外部存储系统,用count来返回计数结果， 用take来收集RDD中的一些元素,collect()可以用来获取整个RDD的数据
		//只有当你的整个数据集能够在但单台机器的内存中放得下时，才能使用collect(),因此不能用于在大规模数据集中。
		
		//用行动操作对上面badLinesRDD进行计数
		System.out.println("input had " + badLinesRDD.count() + " concerning lines");
		System.out.println("下面列出10个例子");
		for(String line : badLinesRDD.take(10)){
			System.out.println(line);
		}
		//每调用一个新的心动进行操作时，整个RDD都会从头开始计算，要避免这种低效的行为，用户可以将中间结果持久化。
	}
}
