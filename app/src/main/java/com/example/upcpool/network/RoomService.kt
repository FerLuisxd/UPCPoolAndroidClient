package com.example.upcpool.network

import com.example.upcpool.models.ApiResponseDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RoomService {
    @GET("room?")
    fun getRooms(@Query("api_key")apiKey: String, @Query("query")query: String): Call<ApiResponseDetails>
}