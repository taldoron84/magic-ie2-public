package com.magic.events

import java.io.Serializable

import play.api.libs.json.{Json, Writes}

/**
  * @author kobi on 13/02/17.
  * @since 12.1
  */
object Events {

  case class Location(locationId: Int, state: String, city: String, latitude: String, longitude: String)


  case class CarEvent(ID: Int, RECHNERBEZ: String, IsSentByHttp: Boolean)

  implicit val CarEventWrites = new Writes[CarEvent] {
    def writes(carEvent: CarEvent) = Json.obj(
      "ID" -> carEvent.ID,
      "RECHNERBEZ" -> carEvent.RECHNERBEZ,
      "IsSentByHttp" -> carEvent.IsSentByHttp
    )
  }

}

//  case class CarEvent(eventId: Long, carId: Int, modelId: Int, location: Location)
