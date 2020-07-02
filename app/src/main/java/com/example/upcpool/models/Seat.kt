package com.example.upcpool.models

import com.google.gson.annotations.SerializedName

data class Seat(
    @SerializedName("userCode")
    var userCode:String,
    @SerializedName("name")
    var name:String,
    @SerializedName("features")
    var features:List<String>

) {
}