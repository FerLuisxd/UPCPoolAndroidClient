package com.example.upcpool.controllers.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.upcpool.R
import com.example.upcpool.models.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.upcpool.database.RoomDB
import com.example.upcpool.entity.ReservationPost
import com.example.upcpool.entity.ReservationRoom
import com.example.upcpool.entity.RoomDto
import com.example.upcpool.models.Availables
import com.example.upcpool.models.Reservation
import com.example.upcpool.network.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DetailsActivity : AppCompatActivity() {

    lateinit var tvNumber: TextView
    lateinit var tvInfo: TextView
    lateinit var tvTopic: TextView
    lateinit var btnYes: Button
    lateinit var btnNo: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_details)
        tvNumber = findViewById(R.id.tvNumber)
        tvInfo = findViewById(R.id.tvInfo)
        tvTopic = findViewById(R.id.tvTopic)
        btnYes = findViewById(R.id.bYes)
        btnNo = findViewById(R.id.bNo)
        supportActionBar?.setHomeButtonEnabled(true);

        setSupportActionBar(findViewById(R.id.app_bar))

        initFields(this)
    }

    private fun initFields(context: Context){

        val RoomObject: RoomDto? = intent.getSerializableExtra("Room") as RoomDto?

       /* val picBuilder = Picasso.Builder(context)
        picBuilder.downloader(OkHttp3Downloader(context))
        picBuilder.build().load(RoomObject?.logo)
            .placeholder((R.drawable.ic_launcher_background))
            .error(R.drawable.ic_launcher_background)
            .into(ivRoomDetail);*/

        tvNumber.text = RoomObject?.code
        tvInfo.text = RoomObject?.office
        tvTopic.text = RoomObject?.seats.toString()


        btnYes.setOnClickListener {
            reserveRoom(RoomObject)
            finish()
        }
    }

    private fun reserveRoom(RoomObject: RoomDto?){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //DECLARAMOS NUESTRO OBJETO RoomService
        val roomService: RoomService
        roomService = retrofit.create(RoomService::class.java)

        val auxRoomPost = RoomObject?.let { ReservationRoom(RoomObject.office, RoomObject.code) }
        val post = auxRoomPost?.let { ReservationPost(it, 1, "u123456789", RoomObject.date) }

        val request = post?.let { roomService.reserveRoom(it) }

        request?.enqueue(object : Callback<Reservation> {
            override fun onFailure(call: Call<Reservation>, t: Throwable) {
                Log.d("Details Activity Fail", "Error: "+t.toString())
            }

            override fun onResponse(call: Call<Reservation>, response: Response<Reservation>) {
                println(response)
                println("Reserva hecha correctamente !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")
            }


        })

    }
}