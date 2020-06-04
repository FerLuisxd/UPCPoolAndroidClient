package com.example.upcpool.database

import androidx.room.*
import com.example.upcpool.models.Room
import com.example.upcpool.models.Token

@Dao
interface TokenDAO {
    @Insert
    fun insertToken(vararg token: Token)

    @Query("SELECT * FROM tokens ORDER BY id DESC LIMIT 1")
    fun getLastToken(): Token
}