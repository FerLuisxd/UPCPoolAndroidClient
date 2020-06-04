package com.example.upcpool.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "rooms"
)
data class Room(
    @PrimaryKey
    @SerializedName("id")
    val id : Int =0,
    @SerializedName("title")
    val title : String,
    @SerializedName("overview")
    val overview: String
): Serializable {
}