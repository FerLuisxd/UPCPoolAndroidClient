package com.example.upcpool.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Available(
    @SerializedName("_id")
    var _id:String,
    @SerializedName("start")
    var start: Date,
    @SerializedName("available")
    var available: List<Room>
) {
}