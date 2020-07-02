package com.example.upcpool.network

import androidx.annotation.AnyRes
import com.example.upcpool.entity.*
import com.example.upcpool.models.*
import retrofit2.Call
import retrofit2.http.*



const val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI1ZWEwZmE3ZjFiMWRmZmM5MWQ0NmZlNzQiLCJpYXQiOjE1OTM2NzAyNDcsImV4cCI6MTYwMTQ0NjI0N30.nppdWZfyro2GpgJLEoArZiWF51_e6HDDdMoa3b_iQWM"
interface RoomService {
    //Log In
    @POST("auth/v3/login")
    fun login(@Body()user : User): Call<LoginResponseDetails>

    //Obtener cubs disponibles
    @GET("available")
    //@Headers(value = [
    //    "Authorization: Bearer $token"
    //])
    fun getAvailable(@HeaderMap headers: Map<String, String>): Call<List<Availables>>



    //Obtener un cubiculo / TODO: Eliminar en la limpieza de codigo
    @GET("room/{id}")
    fun getRoom(@Path("id")id:String): Call<*>

    //Obtener todos los cubiculos / TODO: Eliminar en la limpieza de codigo
    @GET("room?")
    fun getRooms(@Query("api_key")apiKey: String, @Query("query")query: String): Call<ApiResponseDetails>

    //Reservar un cubiculo
    @POST("reservation")
    //@Headers(value = [
    //    "Authorization: Bearer $token"
    //])
    fun reserveRoom(@Body()body : ReservationPost, @HeaderMap headers: Map<String, String>) : Call<Reservation>

    //Obtener cubiculos reservados
    @GET("reservation")
    //@Headers(value = [
    //    "Authorization: Bearer $token"
    //])
    fun getReservations(@HeaderMap headers: Map<String, String>) : Call<List<Reservation>>

    //Obtener cubiculos reservados
    @PUT("reservation/{id}")
    //@Headers(value = [
    //    "Authorization: Bearer $token"
    //])
    fun activateReservation(@Path("id")id:String , @HeaderMap headers: Map<String, String>) : Call<Reservation>

    @GET("user/")
    //@Headers(value = [
    //    "Authorization: Bearer $token"
    //])
    fun getUser(@HeaderMap headers: Map<String, String>) : Call<UserDto>

    @GET("reservation/public")
    //@Headers(value = [
    //    "Authorization: Bearer $token"
    //])
    fun getPublics(@HeaderMap headers: Map<String, String>) : Call<List<Reservation>>

    @POST("reservation/public/{id}")
    //@Headers(value = [
    //    "Authorization: Bearer $token"
    //])
    fun joinRoom(@Path("id")id:String, @Body()body : Features, @HeaderMap headers: Map<String, String>) : Call<Reservation>

    @PUT("reservation/share/{id}")
    //@Headers(value = [
    //    "Authorization: Bearer $token"
    //])
    fun shareRoom(@Path("id")id:String, @Body()body : Share, @HeaderMap headers: Map<String, String>) : Call<Reservation>
}

