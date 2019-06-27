package com.whut.analyst

import org.apache.log4j.{Level, Logger}
import org.apache.spark.mllib.classification.SVMWithSGD
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.optimization.L1Updater
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkConf, SparkContext}
import redis.clients.jedis.Jedis


object SVM{

  def main(args:Array[String]){

    /**
      * 设置spark master
      * 单机模式： "local[*]"
      * 集群模式： "spark://192.168.1.32:7077"
      */
    val master = "local[2]"
    val conf = new SparkConf().setAppName("SparkSVM").setMaster(master)
    val sc = new SparkContext(conf)

    Logger.getRootLogger.setLevel(Level.WARN)

    val data = sc.textFile("service-analyst/iris.data")
    val parsedData = data.map { line =>
      val parts = line.split(',')
      LabeledPoint(if(parts(4)=="Iris-setosa") 0.toDouble else if (parts(4)
        =="Iris-versicolor") 1.toDouble else
        2.toDouble, Vectors.dense(parts(0).toDouble,parts(1).toDouble,parts
      (2).toDouble,parts(3).toDouble))
    }
    val splits = parsedData.filter { point => point.label != 2 }.randomSplit(
      Array(0.6, 0.4), seed = 11L)
    val training = splits(0).cache()
    val test = splits(1)

    val svmAlg = new SVMWithSGD()
    svmAlg.optimizer.setNumIterations(2000).setRegParam(0.1).setUpdater(new L1Updater)
    val model = svmAlg.run(training)

    val scoreAndLabels = test.map { point =>
      val score = model.predict(point.features)
      (score, point.label)
    }

    scoreAndLabels.foreach(println)

    }
}
