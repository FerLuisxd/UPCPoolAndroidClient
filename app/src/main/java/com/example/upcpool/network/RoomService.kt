package com.example.upcpool.network

import com.example.upcpool.entity.User
import com.example.upcpool.models.ApiResponseDetails
import com.example.upcpool.models.LoginResponseDetails
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Body
import retrofit2.http.Query
import retrofit2.http.Path


interface RoomService {
    @POST("auth/v3/login")
    fun login(@Body()user : User): Call<LoginResponseDetails>
    @GET("available")
    fun getAvailable(): Call<Any>
    @GET("room/{id}")
    fun getRoom(@Path("id")id:String): Call<Any>
    @GET("room?")
    fun getRooms(@Query("api_key")apiKey: String, @Query("query")query: String): Call<ApiResponseDetails>
}
