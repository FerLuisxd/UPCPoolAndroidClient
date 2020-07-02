package com.example.upcpool.controllers.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
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
import kotlinx.android.synthetic.main.room_details.*
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

    lateinit var etTiu: EditText
    lateinit var etHours: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_details)
        tvNumber = findViewById(R.id.tvNumber)
        tvInfo = findViewById(R.id.tvInfo)
        tvTopic = findViewById(R.id.tvTopic)
        btnYes = findViewById(R.id.bYes)
        btnNo = findViewById(R.id.bNo)

        etTiu = findViewById(R.id.et_tiu2)
        etHours = findViewById(R.id.et_hours)

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
        if (RoomObject != null) {
            tvTopic.text = RoomObject?.features[0] + " - " + RoomObject?.features[1]
        }

        btnYes.setOnClickListener {
            reserveRoom(RoomObject, context)

        }

        btnNo.setOnClickListener {
            val intento = Intent(context, MainActivity::class.java)
            startActivity(intento)
        }
    }
    private fun getHeaderMap(token : String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer $token"
        return headerMap
    }
    private fun reserveRoom(RoomObject: RoomDto?, context:Context){

        val retrofit = Retrofit.Builder()
            .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //DECLARAMOS NUESTRO OBJETO RoomService
        val roomService: RoomService
        roomService = retrofit.create(RoomService::class.java)
        val token: String  = RoomDB.getInstance(this).getTokenDAO().getLastToken().token
        val auxRoomPost = RoomObject?.let { ReservationRoom(RoomObject.office, RoomObject.code) }
        val post = auxRoomPost?.let { ReservationPost(it, Integer.parseInt(etHours.text.toString()), etTiu.text.toString(), RoomObject.date) }
        val request = post?.let { roomService.reserveRoom(it,getHeaderMap(token)) }

        request?.enqueue(object : Callback<Reservation> {
            override fun onFailure(call: Call<Reservation>, t: Throwable) {
                Log.d("Details Activity Fail", "Error: "+t.toString())
            }

            override fun onResponse(call: Call<Reservation>, response: Response<Reservation>) {

                if (response.isSuccessful)
                {
                    val mAlertDialog = AlertDialog.Builder(context)
                    mAlertDialog.setTitle("Cubiculo reservado")
                    mAlertDialog.setPositiveButton("Ok") { dialog, id ->
                        finish()
                    }
                    mAlertDialog.show()

                    println(response)
                    println("Reserva hecha correctamente !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!")

                }
                else
                {
                    val mAlertDialog = AlertDialog.Builder(context)
                    mAlertDialog.setTitle("Error al reservar cubiculo")
                    mAlertDialog.setPositiveButton("Ok") { dialog, id ->
                        finish()
                    }
                    mAlertDialog.show()

                    println(response)
                    println("Error al reservar cubiculo")
                }
            }


        })

    }
}