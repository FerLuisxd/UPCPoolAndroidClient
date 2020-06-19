package com.example.upcpool.controllers.activities

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.upcpool.R

class RoomActivity : AppCompatActivity() {

    lateinit var tvNumber: TextView
    lateinit var tvInfo: TextView
    lateinit var tvTopic: TextView
    lateinit var btnYes: Button
    lateinit var btnNo: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_room)


        setSupportActionBar(findViewById(R.id.app_bar))
    }













}