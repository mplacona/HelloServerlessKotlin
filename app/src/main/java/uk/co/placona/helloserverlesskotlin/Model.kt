package uk.co.placona.helloserverlesskotlin

object Model {
    data class Request(val name:String)
    data class Result(val message: String)
}