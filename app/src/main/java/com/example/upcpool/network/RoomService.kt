package com.example.upcpool.network

import androidx.annotation.AnyRes
import com.example.upcpool.entity.ReservationPost
import com.example.upcpool.entity.User
import com.example.upcpool.models.ApiResponseDetails
import com.example.upcpool.models.LoginResponseDetails
import com.example.upcpool.models.Availables
import com.example.upcpool.models.Reservation
import retrofit2.Call
import retrofit2.http.*


interface RoomService {

    //Log In
    @POST("auth/v3/login")
    fun login(@Body()user : User): Call<LoginResponseDetails>

    //Obtener cubs disponibles
    @GET("available")
    @Headers(value = [
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWFiYWQyZGYxNDk1NzAwMWQ3ZGMzNjgiLCJpYXQiOjE1ODgzMDkyOTMsImV4cCI6MTU5NjA4NTI5M30.dA7pSABqN3-9AMduIes-1sIyuKHrneM-qe8uhNXMS9Q"
    ])
    fun getAvailable(): Call<List<Availables>>

    //Obtener un cubiculo / TODO: Eliminar en la limpieza de codigo
    @GET("room/{id}")
    fun getRoom(@Path("id")id:String): Call<*>

    //Obtener todos los cubiculos / TODO: Eliminar en la limpieza de codigo
    @GET("room?")
    fun getRooms(@Query("api_key")apiKey: String, @Query("query")query: String): Call<ApiResponseDetails>

    //Reservar un cubiculo
    @POST("reservation")
    @Headers(value = [
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWFiYWQyZGYxNDk1NzAwMWQ3ZGMzNjgiLCJpYXQiOjE1ODgzMDkyOTMsImV4cCI6MTU5NjA4NTI5M30.dA7pSABqN3-9AMduIes-1sIyuKHrneM-qe8uhNXMS9Q"
    ])
    fun reserveRoom(@Body()body : ReservationPost) : Call<Reservation>

    //Obtener cubiculos reservados
    @GET("reservation")
    @Headers(value = [
        "Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWFiYWQyZGYxNDk1NzAwMWQ3ZGMzNjgiLCJpYXQiOjE1ODgzMDkyOTMsImV4cCI6MTU5NjA4NTI5M30.dA7pSABqN3-9AMduIes-1sIyuKHrneM-qe8uhNXMS9Q"
    ])
    fun getReservations() : Call<List<Reservation>>
}
