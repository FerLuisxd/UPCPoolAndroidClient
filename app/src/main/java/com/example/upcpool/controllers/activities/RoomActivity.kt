package com.example.upcpool.controllers.activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.upcpool.R
import com.example.upcpool.controllers.fragments.HomeFragment
import com.example.upcpool.controllers.fragments.RoomFragment
import com.example.upcpool.controllers.fragments.SharedFragment
import com.example.upcpool.database.RoomDB
import com.example.upcpool.entity.RoomDto
import com.example.upcpool.entity.Share
import com.example.upcpool.entity.UserDto
import com.example.upcpool.models.Reservation
import com.example.upcpool.models.Seat
import com.example.upcpool.network.RoomService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_in_room.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.share_modal.view.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RoomActivity : AppCompatActivity() {

    lateinit var token : String

    lateinit var tvRoom: TextView
    lateinit var tvTopic: TextView
    lateinit var btnShare: FloatingActionButton

    lateinit var tv_tiu1: TextView
    lateinit var tv_name1: TextView
    lateinit var tv_tiu2: TextView
    lateinit var tv_name2: TextView
    lateinit var tv_tiu3: TextView
    lateinit var tv_name3: TextView
    lateinit var tv_tiu4: TextView
    lateinit var tv_name4: TextView
    lateinit var tv_tiu5: TextView
    lateinit var tv_name5: TextView
    lateinit var tv_tiu6: TextView
    lateinit var tv_name6: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_room)

        tvRoom = findViewById(R.id.tvRoom)
        tvTopic = findViewById(R.id.tvTopic)
        btnShare = findViewById(R.id.btn_Share)

        tv_tiu1 = findViewById(R.id.tv_tiu1)
        tv_name1 = findViewById(R.id.tv_name1)
        tv_tiu2 = findViewById(R.id.tv_tiu2)
        tv_name2 = findViewById(R.id.tv_name2)
        tv_tiu3 = findViewById(R.id.tv_tiu3)
        tv_name3 = findViewById(R.id.tv_name3)
        tv_tiu4 = findViewById(R.id.tv_tiu4)
        tv_name4 = findViewById(R.id.tv_name4)
        tv_tiu5 = findViewById(R.id.tv_tiu5)
        tv_name5 = findViewById(R.id.tv_name5)
        tv_tiu6 = findViewById(R.id.tv_tiu6)
        tv_name6 = findViewById(R.id.tv_name6)

        token  = RoomDB.getInstance(this).getTokenDAO().getLastToken().token

        initStudents()

        setSupportActionBar(findViewById(R.id.app_bar))

        initFields(this)
    }

    private fun initFields(context: Context){

        val roomDto:RoomDto? = intent.getSerializableExtra("Room") as RoomDto?

        tvRoom.text = roomDto?.code
        tvTopic.text = roomDto?.theme

        initStudents()

        btnShare.setOnClickListener {

            val mDialogView = LayoutInflater.from(this).inflate(R.layout.share_modal, null)

            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setTitle("Compartir")

            val mAlertDialog = mBuilder.show()

            mDialogView.btnShare.setOnClickListener{
                mAlertDialog.dismiss()
                val tema :String = mDialogView.etTheme.text.toString()
                val mac = mDialogView.cbMac.isChecked
                val apple = mDialogView.cbApple.isChecked

                val features: MutableList<String> = ArrayList()

                if (mac) features.add("MAC")
                if (apple) features.add("Apple TV")

                val auxShare = Share(features, tema)
                if (roomDto != null) {
                    share(roomDto, auxShare, context)
                    tvTopic.text = tema
                }
            }
        }

        btnExit.setOnClickListener{
            val intento = Intent(context, MainActivity::class.java)
            startActivity(intento)
        }
    }

    private fun share(RoomObject: RoomDto, shared:Share, context:Context){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val token: String  = RoomDB.getInstance(this).getTokenDAO().getLastToken().token
        val roomService: RoomService
        roomService = retrofit.create(RoomService::class.java)
        val request = roomService.shareRoom(RoomObject.pubId, shared,getHeaderMap(token))

        request.enqueue(object : Callback<Reservation> {
            override fun onFailure(call: Call<Reservation>, t: Throwable) {
                val mAlertDialog = AlertDialog.Builder(context)
                mAlertDialog.setTitle("Ha ocurrido un error")
                mAlertDialog.setMessage(t.message)
                mAlertDialog.setPositiveButton("Ok") { dialog, id ->
                }
                mAlertDialog.show()
                println(t)
                println("Error en peticion de compartir")
            }

            override fun onResponse(call: Call<Reservation>, responseDetails: Response<Reservation>) {
                println("Compartir correcto")

                if (responseDetails.isSuccessful) {
                    Log.d("MSG", responseDetails.message())
                    Log.d("Error Body", responseDetails.errorBody().toString())
                    Log.d("Activity Success", responseDetails.raw().toString())
                    Log.d("Activity Success", responseDetails.body().toString())

                    val alertbox = AlertDialog.Builder(context)
                        .setTitle("Cubiculo compartido correctamente")
                        .setNegativeButton("Ok"){ arg0, arg1 ->
                        }
                }
                else
                {
                    Log.d("MSG", responseDetails.message())
                    Log.d("Error Body", responseDetails.errorBody().toString())
                    Log.d("Activity Success", responseDetails.raw().toString())
                    Log.d("Activity Success", responseDetails.body().toString())
                    println("shareRoom no devlolvio 200")
                    val mAlertDialog = AlertDialog.Builder(context)
                    mAlertDialog.setTitle("Ha ocurrido un error")
                    mAlertDialog.setMessage("El cubiculo aun no esta activo")
                    mAlertDialog.setPositiveButton("Ok") { dialog, id ->
                    }
                    mAlertDialog.show()
                }
            }
        })
    }

    private fun initStudents() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val roomService: RoomService
        roomService = retrofit.create(RoomService::class.java)
        val request = roomService.getPublics(getHeaderMap(token))

        request.enqueue(object : Callback<List<Reservation>>{
            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(
                call: Call<List<Reservation>>,
                responseDetails: Response<List<Reservation>>
            ) {
                Log.d("MSG", responseDetails.message())
                Log.d("Error Body", responseDetails.errorBody().toString())
                Log.d("Activity Success", responseDetails.raw().toString())
                Log.d("Activity Success", responseDetails.body().toString())

                val listPubs = responseDetails.body()

                val r = roomService.getUser(getHeaderMap(token))
                r.enqueue(object : Callback<UserDto>{
                    override fun onFailure(call: Call<UserDto>, t: Throwable) {
                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                    }

                    override fun onResponse(call: Call<UserDto>, response: Response<UserDto>) {
                        Log.d("MSG", response.message())
                        Log.d("Error Body", response.errorBody().toString())
                        Log.d("Activity Success", response.raw().toString())
                        Log.d("Activity Success", response.body().toString())

                        val auxUser = response.body() ?: null

                        var auxSeats : List<Seat> = ArrayList()

                        if (listPubs != null) {
                            listPubs.forEach {pub ->
                                pub.seats.forEach {seat ->
                                    if (auxUser != null) {
                                        if (seat.name == auxUser.name) {
                                            auxSeats = pub.seats
                                        }
                                    }
                                }
                            }
                        }
                        if (auxSeats.size == 1)
                        {
                            tv_name1.text = auxSeats[0].name
                            tv_tiu1.text = auxSeats[0].userCode
                        }
                        if (auxSeats.size == 2)
                        {
                            tv_name1.text = auxSeats[0].name
                            tv_tiu1.text = auxSeats[0].userCode
                            tv_name2.text = auxSeats[1].name
                            tv_tiu2.text = auxSeats[1].userCode
                        }
                        if (auxSeats.size == 3)
                        {
                            tv_name1.text = auxSeats[0].name
                            tv_tiu1.text = auxSeats[0].userCode
                            tv_name2.text = auxSeats[1].name
                            tv_tiu2.text = auxSeats[1].userCode
                            tv_name3.text = auxSeats[2].name
                            tv_tiu3.text = auxSeats[2].userCode
                        }
                        if (auxSeats.size == 4)
                        {
                            tv_name1.text = auxSeats[0].name
                            tv_tiu1.text = auxSeats[0].userCode
                            tv_name2.text = auxSeats[1].name
                            tv_tiu2.text = auxSeats[1].userCode
                            tv_name3.text = auxSeats[2].name
                            tv_tiu3.text = auxSeats[2].userCode
                            tv_name4.text = auxSeats[3].name
                            tv_tiu4.text = auxSeats[3].userCode
                        }
                        if (auxSeats.size == 5)
                        {
                            tv_name1.text = auxSeats[0].name
                            tv_tiu1.text = auxSeats[0].userCode
                            tv_name2.text = auxSeats[1].name
                            tv_tiu2.text = auxSeats[1].userCode
                            tv_name3.text = auxSeats[2].name
                            tv_tiu3.text = auxSeats[2].userCode
                            tv_name4.text = auxSeats[3].name
                            tv_tiu4.text = auxSeats[3].userCode
                            tv_name5.text = auxSeats[4].name
                            tv_tiu5.text = auxSeats[4].userCode
                        }
                        if (auxSeats.size == 6)
                        {
                            tv_name1.text = auxSeats[0].name
                            tv_tiu1.text = auxSeats[0].userCode
                            tv_name2.text = auxSeats[1].name
                            tv_tiu2.text = auxSeats[1].userCode
                            tv_name3.text = auxSeats[2].name
                            tv_tiu3.text = auxSeats[2].userCode
                            tv_name4.text = auxSeats[3].name
                            tv_tiu4.text = auxSeats[3].userCode
                            tv_name5.text = auxSeats[4].name
                            tv_tiu5.text = auxSeats[4].userCode
                            tv_name6.text = auxSeats[5].name
                            tv_tiu6.text = auxSeats[5].userCode
                        }
                    }
                })
            }
        })
    }

    private fun getHeaderMap(token : String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer $token"
        return headerMap
    }

}