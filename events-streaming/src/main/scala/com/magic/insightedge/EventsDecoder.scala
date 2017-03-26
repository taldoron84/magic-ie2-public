package com.magic.insightedge

import com.magic.events.Events.CarEvent
import kafka.serializer.Decoder
import kafka.utils.VerifiableProperties
import org.apache.commons.lang3.SerializationUtils

class EventsDecoder(props: VerifiableProperties) extends Decoder[CarEvent] {
  override def fromBytes(bytes: Array[Byte]): CarEvent = SerializationUtils.deserialize(bytes)
}
