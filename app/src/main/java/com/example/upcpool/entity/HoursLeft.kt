package com.example.upcpool.entity

import com.google.gson.annotations.SerializedName

class HoursLeft (
    @SerializedName("todayHours")
    val todayHours:Int,
    @SerializedName("tomorrowHours")
    val tomorrowHours:Int,
    @SerializedName("secondaryHours")
    val secondaryHours:Int
){

}