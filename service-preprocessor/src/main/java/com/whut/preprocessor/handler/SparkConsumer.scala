package com.whut.preprocessor.handler

import com.whut.preprocessor.config.LogConfig
import org.apache.spark.streaming._
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka010.KafkaUtils
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.spark.streaming.kafka010.LocationStrategies.PreferConsistent
import org.apache.spark.streaming.kafka010.ConsumerStrategies.Subscribe

object SparkConsumer{

  def main(args:Array[String]){

    /**
      * 设置spark master
      * 单机模式： "local[*]"
      * 集群模式： "spark://192.168.1.32:7077"
      */
    val master = "local[1]"

    /**
      * 设置checkpoint路径
      * 单机模式： "checkpoint"
      * 集群模式： "hdfs://master:9000/user/checkpoint"
      */
    val checkpoint = "checkpoint"

    //设置日志等级
    LogConfig.setStreamingLogLevels()

    //设置批处理间隔，单位秒
    val batchDuration = 1

    //设置输入流的topic
    val topic = "DCS-TOPIC"

    //初始化streamingContext
    val streamingContext = new StreamingContext(
      new SparkConf().setAppName(s"${this.getClass.getSimpleName}").setMaster(master),
      Seconds(batchDuration)
    )
    streamingContext.checkpoint(checkpoint)

    //kafka配置
    val kafkaParams = Map[String, Object](
      "bootstrap.servers" -> "59.69.101.206:49092,59.69.101.206:49093,59.69.101.206:49094",
      "group.id" -> "com.lou",
      "auto.offset.reset" -> "latest",
      "enable.auto.commit" -> (false: java.lang.Boolean),
      "auto.commit.interval.ms" -> "5000",
      "session.timeout.ms" -> "30000",
      "key.deserializer" -> classOf[StringDeserializer],
      "value.deserializer" -> classOf[StringDeserializer]
    )

    //创建DStream
    val dStream = KafkaUtils.createDirectStream[String, String](
      streamingContext,
      PreferConsistent,
      Subscribe[String, String](Array(topic), kafkaParams)
    )

    //对接收到的一个DStream进行解析
    val lines = dStream.map(record => (record.key, record.value))
    val objects = lines.map(_._2)
    val word = objects.flatMap(_.split(" "))
    val pair = word.map(x => (x,1))
    //窗口长度设置为10秒,窗口滑动距离设置为2秒
    val wordCounts = pair.reduceByKeyAndWindow(_ + _, _ - _, Seconds(10), Seconds(2))
    wordCounts.print
    streamingContext.start
    streamingContext.awaitTermination
  }
}