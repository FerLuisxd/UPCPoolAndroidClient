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
import androidx.fragment.app.Fragment
import com.example.upcpool.R
import com.example.upcpool.controllers.fragments.HomeFragment
import com.example.upcpool.controllers.fragments.RoomFragment
import com.example.upcpool.controllers.fragments.SharedFragment
import com.example.upcpool.database.RoomDB
import com.example.upcpool.entity.RoomDto
import com.example.upcpool.entity.Share
import com.example.upcpool.models.Reservation
import com.example.upcpool.network.RoomService
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.share_modal.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RoomActivity : AppCompatActivity() {

    lateinit var tvRoom: TextView
    lateinit var tvTopic: TextView
    lateinit var btnShare: FloatingActionButton

    lateinit var tvInfo: TextView
    lateinit var btnYes: Button
    lateinit var btnNo: Button

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        navigateTo(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_room)

        tvRoom = findViewById(R.id.tvRoom)
        tvTopic = findViewById(R.id.tvTopic)
        btnShare = findViewById(R.id.btn_Share)



        val navView: BottomNavigationView = findViewById(R.id.bottom_navigationView)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        supportActionBar?.setHomeButtonEnabled(true);

        setSupportActionBar(findViewById(R.id.app_bar))

        initFields(this)
    }

    private fun initFields(context: Context){

        val roomDto:RoomDto? = intent.getSerializableExtra("Room") as RoomDto?

        tvRoom.text = roomDto?.code
        tvTopic.text = roomDto?.theme


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

    private fun getFragmentFor(item: MenuItem): Fragment {
        return when(item.itemId) {
            R.id.menu_reserve -> RoomFragment()
            R.id.menu_home -> HomeFragment()
            R.id.menu_shared -> SharedFragment()
            else -> HomeFragment()
        }
    }

    private fun navigateTo(item: MenuItem): Boolean {
        item.isChecked = true
        return supportFragmentManager
            .beginTransaction()
            .replace(R.id.flFragment, getFragmentFor(item))
            .commit() > 0
    }

    private fun getHeaderMap(token : String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer $token"
        return headerMap
    }

}