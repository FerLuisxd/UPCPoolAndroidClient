package com.example.upcpool.controllers.activities

import android.content.Intent
import android.media.Image
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.upcpool.R
import com.example.upcpool.controllers.fragments.RoomFragment
import com.example.upcpool.controllers.fragments.SaveFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class RoomStart : AppCompatActivity() {

    lateinit var image : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_main)

        val navView: ImageView = findViewById(R.id.imageView2)
        navView.setOnClickListener{
            val intent = Intent( this, MainActivity::class.java)
            startActivity(intent)
            Log.d("ENTRO", "Si entro we2")
        }

    }

}
