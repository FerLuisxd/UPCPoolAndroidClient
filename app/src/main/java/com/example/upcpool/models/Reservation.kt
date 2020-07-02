package com.example.upcpool.models

import com.example.upcpool.entity.RoomDto
import com.google.gson.annotations.SerializedName
import java.util.*

data class Reservation(

    @SerializedName("_id")
    var _id:String,
    @SerializedName("seats")
    var seats:List<Seat>,
    @SerializedName("publicFeatures")
    var publicFeatures:List<String>,
    @SerializedName("active")
    var active:Boolean,
    @SerializedName("start")
    var start: Date,
    @SerializedName("end")
    var end:Date,
    @SerializedName("room")
    var room: RoomDto,
    @SerializedName("userCode")
    var userCode:String,
    @SerializedName("userSecondaryCode")
    var userSecondaryCode:String,
    @SerializedName("public")
    var public:Boolean,
    @SerializedName("theme")
    var theme:String


    ) {
}