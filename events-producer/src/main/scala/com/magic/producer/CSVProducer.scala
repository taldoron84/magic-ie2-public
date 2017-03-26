package com.magic.producer

import scala.io
import scala.util.Random
import java.util.{Properties, UUID}
import com.magic.events.Events._
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import play.api.libs.json.Json

/**
  * Created by tal on 3/15/17.
  */
object CSVProducer {

  def run(kafkaConfig: Properties): Unit = {
    println("-- Running CSV producer")

    val bufferedSource = io.Source.fromFile("./temp2.csv")
    //drop the headers first line
    for (line <- bufferedSource.getLines.drop(1)) {
      val cols = line.split(",").map(_.trim)
      // do whatever you want with the columns here
      runThread(kafkaConfig.getProperty(TOPIC_CAR)) { (topic, producer) =>
        val eventJson = Json.toJson(CarEvent(cols(0).toInt,cols(1),false)).toString()
        println(s"sending event to $topic $eventJson")
        producer.send(new ProducerRecord[String, String](topic, eventJson))
//        println(s"sent event with the following fields: ${cols(0)} | ${cols(1)}")
        println(s"JSON is: ${eventJson}")

      }
    }
    bufferedSource.close


    def runThread(kafkaTopic: String) (sendMessage: (String, KafkaProducer[String, String]) => Unit): Unit = {
      new Thread(new Runnable {
        override def run(): Unit = {
          val producer = new KafkaProducer[String, String](kafkaConfig)
//          var i = 0
//          while (true) {
            sendMessage(kafkaTopic, producer)
            producer.flush()
            Thread.sleep(1000)//add two more zeros to wait a whole second
//          }
        }
      }).start()
    }

  }



}
