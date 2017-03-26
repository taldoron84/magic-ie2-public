package com.magic.insightedge

import java.util._

import com.gigaspaces.client.{ChangeModifiers, ChangeSet}
import com.gigaspaces.query.IdQuery
import com.magic.events.Events.CarEvent


//import com.magic.events.Events.CarEvent
//import com.magic.producer.ProducerApp._
//import com.magic.producer.ProducerApp._
//import com.magic.producer._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}

//import org.apache.http.client.methods.HttpPost
//import org.apache.http.entity.StringEntity
//import org.apache.http.impl.client.HttpClientBuilder


//import com.magic.events.Events.CarEvent
//import org.apache.spark.api.java.JavaSparkContext


import org.apache.spark.{SparkConf, SparkContext}
import org.insightedge.spark.context.InsightEdgeConfig
import org.rogach.scallop.ScallopConf
import org.insightedge.spark.implicits.all._
import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SQLContext
import play.api.libs.json.Json
import java.util.Properties


/**
  * Created by tal on 3/21/17.
  */
object KafkaJob {

  val TOPIC_CAR = "topic.car"
  val BOOTSTRAP_SERVERS = "bootstrap.servers"


  val kafkaConfig = {
    val props = new Properties()
    props.put(BOOTSTRAP_SERVERS, "localhost:9092")
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put(TOPIC_CAR, "car_events_outbound")
    props
  }

  def main(args: Array[String]): Unit = {
    println("Starting Car KAFKA job")
    println(s"with params: ${args.toList}")

    val conf = new Conf(args)

    println(s"conf=${conf}")

    //    val sql = createSqlContext(conf, createSparkContext(conf))
    val sc = createSparkContext(conf)

    //    val now = Calendar.getInstance().getTime()
    //    def uuid = UUID.randomUUID().toString.substring(0, 6)


    //------------KAFKA-------------
    //    val selectedCars = sc.gridRdd[model.CarEvent]()
    val selectedCars = sc.gridSql[model.CarEvent]("isSentByHttp = false")

    //    val now = Calendar.getInstance().getTime()
    //    def uuid = UUID.randomUUID().toString.substring(0, 6)
    //
    //    val df = sql.read.grid.loadClass[model.CarEvent]

    //    val sql = createSqlContext(conf)

    println(s"--------------Amount of cars selected : ${selectedCars.count()}")

    for (car: model.CarEvent <- selectedCars) {
      val now = Calendar.getInstance().getTime()
      val eventJson = Json.toJson(CarEvent(car.ID, car.RECHNERBEZ, car.IsSentByHttp)).toString()

//      val idQuery = new IdQuery[model.CarEvent](classOf[model.CarEvent], car.ID, car.ID)
//      sc.grid.change(idQuery, new ChangeSet().set("isSentByHttp", true), ChangeModifiers.NONE)

      println(s"--------------JSON is : ${eventJson}")
      runThread("car_events_outbound") { (topic, producer) =>
        producer.send(new ProducerRecord[String, String](topic, eventJson))
      }

      val idQuery = new IdQuery[model.CarEvent](classOf[model.CarEvent],car.ID)
      sc.grid.change(idQuery, new ChangeSet().set("isSentByHttp", "true"), ChangeModifiers.NONE)

    }
    //Since we've change the boolean attribute of HTTP, save back to grid
    //    selectedCars.foreach(selectedCars=>sc.saveToGrid(selectedCars.setIsSentByHttp(true)))
    //    selectedCars.saveToGrid()

    //    sc.saveToGrid(selectedCars)

    //  def sentIt(kafkaTopic: String)(sendMessage: (String, KafkaProducer[String, String])): Unit = {
    //    val producer = new KafkaProducer[String, String](kafkaConfig)
    //    sendMessage(kafkaTopic, producer)
    //    producer.flush()
    //  }

    def runThread(kafkaTopic: String)(sendMessage: (String, KafkaProducer[String, String]) => Unit): Unit = {
      new Thread(new Runnable {
        override def run(): Unit = {
          val producer = new KafkaProducer[String, String](kafkaConfig)
          sendMessage(kafkaTopic, producer)
          producer.flush()
          //          Thread.sleep(1000)
        }
      }).start()
    }



    //-------------KAFKA-------------

    println("done")
  }

  class Conf(args: Array[String]) extends ScallopConf(args) {
    val masterUrl = opt[String]("master-url", required = true)
    val spaceName = opt[String]("space-name", required = true)
    val lookupGroups = opt[String]("lookup-groups", required = true)
    val lookupLocators = opt[String]("lookup-locators", required = true)
    verify()
  }

  def createSqlContext(conf: Conf, sc: SparkContext): SQLContext = {
    val sql = new SQLContext(sc)
    sql
  }

  def createSparkContext(conf: Conf): SparkContext = {
    val ieConfig = InsightEdgeConfig(conf.spaceName(), Some(conf.lookupGroups()), Some(conf.lookupLocators()))
    val scConfig = new SparkConf().setAppName("CarKafkaJob").setMaster(conf.masterUrl()).setInsightEdgeConfig(ieConfig)

    val rootLogger = Logger.getRootLogger
    rootLogger.setLevel(Level.ERROR)

    val sc = new SparkContext(scConfig)
    sc
  }
}
