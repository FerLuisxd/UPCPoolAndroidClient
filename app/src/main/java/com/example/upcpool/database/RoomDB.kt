package com.example.upcpool.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room as RoomImp
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.upcpool.models.FeatureTypeConverter
import com.example.upcpool.models.Room
import com.example.upcpool.models.Token


@Database(entities = [Room::class, Token::class], version = 4)
@TypeConverters(FeatureTypeConverter::class)
abstract class RoomDB : RoomDatabase() {
    abstract fun getRoomDAO() : RoomDAO
    abstract fun getTokenDAO() : TokenDAO

    companion object {

        private var INSTANCE : RoomDB?= null


        fun getInstance(context: Context) : RoomDB {
            if (INSTANCE == null) {
                INSTANCE = RoomImp
                    .databaseBuilder(context, RoomDB::class.java, "room.db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return INSTANCE as RoomDB
        }
    }
}