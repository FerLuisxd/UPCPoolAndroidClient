package com.example.upcpool.models

import com.google.gson.annotations.SerializedName
import java.util.*

data class Availables(
    @SerializedName("_id")
    var _id:String,
    @SerializedName("start")
    var start: String,
    @SerializedName("startOriginal")
    var startOriginal: Date,
    @SerializedName("available")
    var available: List<Room>
) {
}