package com.magic.insightedge

import java.util.{Calendar, UUID}

import com.magic.events.Events.CarEvent
import org.apache.spark.api.java.JavaSparkContext

//import com.magic.insightedge.model.CarEvent
//import org.apache.http.client.methods.HttpPost
//import org.apache.http.entity.StringEntity
//import org.apache.http.impl.client.HttpClientBuilder
import org.apache.spark.{SparkConf, SparkContext}
import org.insightedge.spark.context.InsightEdgeConfig
import org.rogach.scallop.ScallopConf
import org.insightedge.spark.implicits.all._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SQLContext
import play.api.libs.json.Json

/**
  * Created by tal on 3/16/17.
  */
object HttpJob {
  def main(args : Array[String]): Unit = {
//    println("Starting Car HTTP job")
//    println(s"with params: ${args.toList}")
//
//    val conf = new Conf(args)
//
//    println(s"conf=${conf}")
//
////    val sql = createSqlContext(conf)
//    val sc = createSparkContext(conf)
//
//    val now = Calendar.getInstance().getTime()
//    def uuid = UUID.randomUUID().toString.substring(0, 6)
//
//
//    //------------HTTP-------------
//
//    val restHost = "Tals-MacBook-Pro.local"
//    val restPort = "8090"
//    val client = HttpClientBuilder.create().build()
//
//    val selectedCars = sc.gridSql[CarEvent]("IsSentByHttp == false")
//    for (car <- selectedCars){
//      val now = Calendar.getInstance().getTime()
//      val eventJson = Json.toJson(car).toString()
//      println(s"${now} : ${eventJson}")
//      val httpPost = new HttpPost("http://localhost:8091/v1")
//      httpPost.setEntity(new StringEntity(eventJson, "UTF-8"))
//      httpPost.setHeader("Content-type", "application/json; charset=UTF-8")
//      client.execute(httpPost)
////      car.IsSentByHttp=false
////      car.setIsSentByHttp(true)
//    }
//
//    //Since we've change the boolean attribute of HTTP, save back to grid
//    selectedCars.saveToGrid()
//
//    //-------------HTTP-------------

    println("done")
  }

  class Conf(args: Array[String]) extends ScallopConf(args) {
    val masterUrl = opt[String]("master-url", required = true)
    val spaceName = opt[String]("space-name", required = true)
    val lookupGroups = opt[String]("lookup-groups", required = true)
    val lookupLocators = opt[String]("lookup-locators", required = true)
    verify()
  }

  def createSqlContext(conf: Conf, sc:SparkContext): SQLContext = {
    val sql = new SQLContext(sc)
    sql
  }

  def createSparkContext(conf: Conf): SparkContext = {
    val ieConfig = InsightEdgeConfig(conf.spaceName(), Some(conf.lookupGroups()), Some(conf.lookupLocators()))
    val scConfig = new SparkConf().setAppName("CarHttpJob").setMaster(conf.masterUrl()).setInsightEdgeConfig(ieConfig)

    val rootLogger = Logger.getRootLogger
    rootLogger.setLevel(Level.ERROR)

    val sc = new SparkContext(scConfig)
    sc
  }
}
