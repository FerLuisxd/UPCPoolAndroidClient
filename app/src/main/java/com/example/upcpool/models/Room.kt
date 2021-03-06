package com.example.upcpool.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(
    tableName = "rooms"
)
data class Room(
    @PrimaryKey(autoGenerate = true)
    val id : Int,
    @SerializedName("office")
    val office : String = "",
    @SerializedName("code")
    val code: String = "",
    @SerializedName("seats")
    val seats: Int = 0,
    @TypeConverters(FeatureTypeConverter::class)
    @SerializedName("features")
    val features: List<String> = listOf()
): Serializable {
}