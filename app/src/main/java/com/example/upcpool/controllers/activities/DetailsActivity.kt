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

        tvNumber.text = RoomObject?.code
        tvInfo.text = RoomObject?.office
        tvTopic.text = RoomObject?.seats.toString()


        btnYes.setOnClickListener {
            saveRoom(RoomObject)
            finish()
        }
    }

    private fun saveRoom(RoomObject: Room?){
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