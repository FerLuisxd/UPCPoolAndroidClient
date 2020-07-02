package com.example.upcpool.entity

import com.google.gson.annotations.SerializedName

class UserDto(
    @SerializedName("email")
    var email: String,
    @SerializedName("name")
    var name: String,
    @SerializedName("points")
    var points: Int,
    @SerializedName("userCode")
    var userCode: String,
    @SerializedName("inRoom")
    var inRoom: Boolean,
    @SerializedName("hoursLeft")
    var hoursLeft: HoursLeft

) {
}