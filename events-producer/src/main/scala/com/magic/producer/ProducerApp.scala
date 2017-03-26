package com.magic.producer

import java.util.Properties

/**
  * @author kobi on 13/02/17.
  * @since 12.1
  */
object ProducerApp extends App{

  println("-- Running kafka producer")
  println("-- Arguments: " + args.mkString("[", ",", "]"))

  val delim = "="
  args.find(!_.contains(delim)).foreach(a => throw new IllegalArgumentException("Incorrect argument " + a))
  val mapArgs = args.map(a => a.trim.split(delim)).map(a => a(0) -> a(1)).toMap

  val kafkaConfig = {
    val props = new Properties()
    props.put(BOOTSTRAP_SERVERS, mapArgs.getOrElse(BOOTSTRAP_SERVERS, "localhost:9092"))
    props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer")
    props.put(CSV_LOCATION, mapArgs.getOrElse(CSV_LOCATION,"/home/yuval/opt/magic/gigaspaces-insightedge-1.0.0-premium"))
    props.put(TOPIC_CAR, mapArgs.getOrElse(TOPIC_CAR, "car_events"))
    props
  }

  println(s"-- kafkaConfig=$kafkaConfig")
  CSVProducer.run(kafkaConfig)
}
