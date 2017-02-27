package config

import org.apache.spark.{SparkConf, SparkContext}

object SparkCore extends Config {

  val sparkConf: SparkConf = new SparkConf()
    .setAppName(SparkAppName)
    .setMaster(SparkMaster)

  val sc = SparkContext.getOrCreate(sparkConf)
}