package com.example.upcpool.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room as RoomImp
import androidx.room.RoomDatabase
import com.example.upcpool.models.Room


@Database(entities = [Room::class], version = 1)
abstract class RoomDB : RoomDatabase() {
    abstract fun getRoomDAO() : RoomDAO

    companion object {

        private var INSTANCE : RoomDB?= null


        fun getInstance(context: Context) : RoomDB {
            if (INSTANCE == null) {
                INSTANCE = RoomImp
                    .databaseBuilder(context, RoomDB::class.java, "room.db")
                    .allowMainThreadQueries()
                    .build()
            }
            return INSTANCE as RoomDB
        }
    }
}