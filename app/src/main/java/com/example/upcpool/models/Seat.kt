package com.example.upcpool.models

import com.google.gson.annotations.SerializedName

data class Seat(
    @SerializedName("_id")
    var _id:String,
    @SerializedName("name")
    var name:String,
    @SerializedName("features")
    var features:List<String>

) {
}