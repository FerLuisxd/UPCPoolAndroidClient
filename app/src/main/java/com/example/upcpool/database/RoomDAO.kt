package com.example.upcpool.database

import androidx.room.*
import com.example.upcpool.models.Room
import com.example.upcpool.models.Token

@Dao
interface RoomDAO {
    @Insert
    fun insertRoom(vararg room: Room)

    @Query("SELECT * FROM rooms ")
    fun getAllRooms(): List<Room>

    @Delete
    fun deleteRooms(vararg room: Room)

    @Delete
    fun deleteRoom(vararg room: Room)

    @Update
    fun updateRooms(vararg room: Room)
}