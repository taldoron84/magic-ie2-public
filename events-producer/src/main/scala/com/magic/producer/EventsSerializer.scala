package com.magic.producer

import java.util

import com.magic.events.Events.CarEvent
import org.apache.commons.lang3.SerializationUtils
import org.apache.kafka.common.serialization.Serializer


class EventsSerializer extends Serializer[CarEvent]{

  override def serialize(topic: String, data: CarEvent): Array[Byte] = SerializationUtils.serialize(data)
  override def close(): Unit = {}
  override def configure(map: util.Map[String, _], b: Boolean): Unit = {}
}
