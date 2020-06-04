package com.example.upcpool.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "tokens"
)

data class Token(
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    val id : Int =0,
    @SerializedName("token")
    var token : String
): Serializable {
}