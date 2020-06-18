package com.example.upcpool.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Reservation(

    @SerializedName("_id")
    var _id:String,
    @SerializedName("active")
    var active:Boolean,
    @SerializedName("start")
    var start: Date,
    @SerializedName("end")
    var end:Date,
    @SerializedName("room")
    var room:Room,
    @SerializedName("userCode")
    var userCode:String,
    @SerializedName("userSecondaryCode")
    var userSecondaryCode:String

    ) {
}