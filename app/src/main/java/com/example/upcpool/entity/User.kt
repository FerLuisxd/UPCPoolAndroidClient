package com.example.upcpool.entity

import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class User(
    val userCode: String,
    val password: String
) {
}