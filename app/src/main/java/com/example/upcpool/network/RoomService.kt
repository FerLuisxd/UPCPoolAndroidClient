package com.example.upcpool.network

import androidx.annotation.AnyRes
import com.example.upcpool.entity.*
import com.example.upcpool.models.*
import retrofit2.Call
import retrofit2.http.*

const val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWFiYWQyZGYxNDk1NzAwMWQ3ZGMzNjgiLCJpYXQiOjE1ODgzMDkyOTMsImV4cCI6MTU5NjA4NTI5M30.dA7pSABqN3-9AMduIes-1sIyuKHrneM-qe8uhNXMS9Q"
interface RoomService {
    //Log In
    @POST("auth/v3/login")
    fun login(@Body()user : User): Call<LoginResponseDetails>

    //Obtener cubs disponibles
    @GET("available")
    @Headers(value = [
        "Authorization: Bearer " + token
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
        "Authorization: Bearer " + token
    ])
    fun reserveRoom(@Body()body : ReservationPost) : Call<Reservation>

    //Obtener cubiculos reservados
    @GET("reservation")
    @Headers(value = [
        "Authorization: Bearer " + token
    ])
    fun getReservations() : Call<List<Reservation>>

    //Obtener cubiculos reservados
    @PUT("reservation/{id}")
    @Headers(value = [
        "Authorization: Bearer " + token
    ])
    fun activateReservation(@Path("id")id:String) : Call<Reservation>

    @GET("user/")
    @Headers(value = [
        "Authorization: Bearer " + token
    ])
    fun getUser() : Call<UserDto>

    @GET("reservation/public")
    @Headers(value = [
        "Authorization: Bearer " + token
    ])
    fun getPublics() : Call<List<Reservation>>

    @POST("reservation/public/{id}")
    @Headers(value = [
        "Authorization: Bearer " + token
    ])
    fun joinRoom(@Path("id")id:String, @Body()body : Features) : Call<Reservation>

    @PUT("reservation/share/{id}")
    @Headers(value = [
        "Authorization: Bearer " + token
    ])
    fun shareRoom(@Path("id")id:String, @Body()body : Share) : Call<Reservation>
}

