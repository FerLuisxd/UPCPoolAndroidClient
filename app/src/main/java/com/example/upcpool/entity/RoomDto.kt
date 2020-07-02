package com.example.upcpool.entity

import java.io.Serializable
import java.util.*

class RoomDto(_id : Int, _office : String, _code : String, _seats: Int, _features : List<String>, _date : Date) : Serializable {
    val id : Int = _id
    val office : String = _office
    val code : String = _code
    val seats : Int = _seats
    val features : List<String> = _features
    val date : Date = _date
    var pubId : String = ""
    var theme : String = ""
}





