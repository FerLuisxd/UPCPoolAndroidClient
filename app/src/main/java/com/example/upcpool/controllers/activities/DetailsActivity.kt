package com.example.upcpool.controllers.activities

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.upcpool.R
import com.example.upcpool.models.Room
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.example.upcpool.database.RoomDB

class DetailsActivity : AppCompatActivity() {

    lateinit var tvOverview: TextView
    lateinit var tvTitle: TextView
    lateinit var tvID: TextView
    lateinit var fabInsert: FloatingActionButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.room_details)
        tvOverview = findViewById(R.id.tvInfo)
        tvTitle = findViewById(R.id.tvNumber)
        tvID = findViewById(R.id.tvTopic)
        //fabInsert = findViewById(R.id.fab)
        supportActionBar?.setHomeButtonEnabled(true);

        initFields(this)
    }

    private fun initFields(context: Context){

        val RoomObject: Room? = intent.getSerializableExtra("Room") as Room?

       /* val picBuilder = Picasso.Builder(context)
        picBuilder.downloader(OkHttp3Downloader(context))
        picBuilder.build().load(RoomObject?.logo)
            .placeholder((R.drawable.ic_launcher_background))
            .error(R.drawable.ic_launcher_background)
            .into(ivRoomDetail);*/

        tvTitle.text = RoomObject?.code
        if(RoomObject?.id != null)
            tvTitle.text = tvTitle.text as String? + "("+RoomObject.id+")"
        tvID.text = "ID: "+RoomObject?.id.toString()
        tvOverview.text = "Resumen: "+RoomObject?.office


        fabInsert.setOnClickListener {
            saveRoom(RoomObject)
            finish()
        }
    }

    private fun saveRoom(RoomObject: Room?){
        //TODO Registro en base de datos
        if (RoomObject != null) {
            Log.d("Insert favorito","Insertando favorito: "+RoomObject.toString());
            var roomList = RoomDB.getInstance(this).getRoomDAO().getAllRooms()
            var boolean = true
            for (room in roomList){
                if(room.code == RoomObject.office)
                    boolean =false
            }
            if(boolean)
                RoomDB.getInstance(this).getRoomDAO().insertRoom(RoomObject)
        }
    }
}