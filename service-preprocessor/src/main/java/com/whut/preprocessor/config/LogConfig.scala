package com.whut.preprocessor.config

import org.apache.spark.internal.Logging
import org.apache.log4j.{Level, Logger}

object LogConfig extends Logging {

  def setStreamingLogLevels() {
    val log4jInitialized = Logger.getRootLogger.getAllAppenders.hasMoreElements
    if (!log4jInitialized) {
      logInfo("Setting log level to [WARN] for streaming example." +
        " To override add a custom log4j.properties to the classpath.")
      Logger.getRootLogger.setLevel(Level.WARN)//警告以上级别才打印
    }
  }
}