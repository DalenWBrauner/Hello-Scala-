/**
  * Created by dalenwbrauner on 4/2/17.
  */

class RequestType extends Enumeration {
  type RequestType = Value
  val GET, POST, DELETE = Value
}

case class Request(rtype:RequestType, resource:String, header:String, body:String)


