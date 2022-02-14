package com.amandeep.kotlinbasics.kotlin.sealedclass


sealed interface Error

open class ReadError():Error
data class NetworkError(val errorMesage:String):Error
object  DatabaseError:Error


sealed class Response{
    class ValidResponse():Response()
    object FailedRequest:Response()
    data class InValidResponse(val errorMesage: String):Response()

}


