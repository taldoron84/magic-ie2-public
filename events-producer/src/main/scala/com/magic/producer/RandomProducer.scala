package com.magic.producer

import java.util.{Properties, UUID}

import com.magic.events.Events.{CarEvent, Location}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerRecord}
import play.api.libs.json.Json

import scala.util.Random


object RandomProducer {

//  def run(kafkaConfig: Properties): Unit = {
//    println("-- Running deterministic producer")
//    runThread(kafkaConfig.getProperty(TOPIC_CAR)) { (topic, producer) =>
//      val eventJson = Json.toJson(DataGenerator.nextCarEvent).toString()
//      println(s"sending event to $topic $eventJson")
//      producer.send(new ProducerRecord[String, String](topic, eventJson))
//    }
//
//    def runThread(kafkaTopic: String) (sendMessage: (String, KafkaProducer[String, String]) => Unit): Unit = {
//      new Thread(new Runnable {
//        override def run(): Unit = {
//          val producer = new KafkaProducer[String, String](kafkaConfig)
//          var i = 0
//          while (true) {
//            sendMessage(kafkaTopic, producer)
//            producer.flush()
//            i = i + 1
//            if (i % 10 == 0) Thread.sleep(Random.nextInt(1000))
//          }
//        }
//      }).start()
//    }
//
//  }

//  object DataGenerator {
//
//    val locations = (1 to 1000).map(DataGenerator.generateLocation)
//    val carIds = 1 to 5000
//    val modelIds = 1 to 20
//
//    def nextCarEvent = CarEvent(carEventIds.next, randomCarId, randomModelId, randomLocation)
//
//    def generateLocation(id: Int) = Location(id, genStr, genStr, genStr, genStr)
//
//    private def longStream(i: Long = 1): Stream[Long] = i #:: longStream(i + 1)
//    val carEventIds = longStream(1).iterator
//
//    def genStr = UUID.randomUUID().toString.substring(0, 6)
//
//    def randomCarId = pickRandom(carIds)
//    def randomLocation = pickRandom(locations)
//    def randomModelId = pickRandom(modelIds)
//
//    def pickRandom[T](list: Seq[T]) = list(Random.nextInt(list.size))
//
//  }

}
