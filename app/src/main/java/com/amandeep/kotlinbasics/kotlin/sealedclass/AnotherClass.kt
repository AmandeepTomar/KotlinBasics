package com.amandeep.kotlinbasics.kotlin.sealedclass

fun main() {

    getSealedClasses()
    getSealedClassesWithInterface()
}


private fun getSealedClassesWithInterface(){
    val error:Error = DatabaseError
    when(error){
        is  DatabaseError -> println("DataBaseError")
        is NetworkError -> println("Network Error")
        is ReadError -> println("Read Error")
    }
}

private fun getSealedClasses(){
    val response:Response=Response.InValidResponse("Response is incorrect")
    when(response){
        Response.FailedRequest -> println("Failed response")
        is Response.InValidResponse -> println("InValid response ${response.errorMesage}")
        is Response.ValidResponse -> println("Valid response ")
    }
}




