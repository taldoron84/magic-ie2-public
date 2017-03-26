package com.magic.insightedge

import java.util
import java.util.{Calendar, UUID}

import org.apache.spark.{SparkConf, SparkContext}
import org.insightedge.spark.context.InsightEdgeConfig
import org.rogach.scallop.ScallopConf
import org.insightedge.spark.implicits.all._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SQLContext


/**
  * @author kobi on 13/02/17.
  * @since 12.1
  */
object AggregatorJob {

  def main(args : Array[String]): Unit = {
//    println("Starting Car aggregated job")
//    println(s"with params: ${args.toList}")
//
//    val conf = new Conf(args)
//
//    println(s"conf=${conf}")
//
//    val sql = createSqlContext(conf)
//
//    val now = Calendar.getInstance().getTime()
//    def uuid = UUID.randomUUID().toString.substring(0, 6)
//
//    val df = sql.read.grid.loadClass[model.CarEvent]
//    val aggCarModelCounts = df.groupBy("modelId")
//      .agg("modelId" -> "count").collect()
//
//    var aggMap = new util.HashMap[Int, Long]()
//
//    aggCarModelCounts.foreach( l =>
//     aggMap.put(l.get(0).asInstanceOf[Int], l.get(1).asInstanceOf[Long])
//    )
//
//    sql.sparkContext.saveToGrid(new model.AggregatedCar(uuid, now, aggMap))


    println("done")
  }

  class Conf(args: Array[String]) extends ScallopConf(args) {
    val masterUrl = opt[String]("master-url", required = true)
    val spaceName = opt[String]("space-name", required = true)
    val lookupGroups = opt[String]("lookup-groups", required = true)
    val lookupLocators = opt[String]("lookup-locators", required = true)
    verify()
  }

  def createSqlContext(conf: Conf): SQLContext = {
    val ieConfig = InsightEdgeConfig(conf.spaceName(), Some(conf.lookupGroups()), Some(conf.lookupLocators()))
    val scConfig = new SparkConf().setAppName("CarAggregatorJob").setMaster(conf.masterUrl()).setInsightEdgeConfig(ieConfig)

    val rootLogger = Logger.getRootLogger
    rootLogger.setLevel(Level.ERROR)

    val sc = new SparkContext(scConfig)
    val sql = new SQLContext(sc)
    sql
  }

}
