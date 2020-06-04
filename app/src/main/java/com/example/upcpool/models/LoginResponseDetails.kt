package com.example.upcpool.models

import com.google.gson.annotations.SerializedName

data class LoginResponseDetails(
    @SerializedName("name")
    var name:String,
    @SerializedName("token")
    var token:String
) {
}