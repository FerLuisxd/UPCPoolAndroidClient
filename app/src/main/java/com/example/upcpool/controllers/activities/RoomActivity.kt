package com.example.upcpool.controllers.activities

import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.upcpool.R
import com.example.upcpool.controllers.fragments.HomeFragment
import com.example.upcpool.controllers.fragments.RoomFragment
import com.example.upcpool.controllers.fragments.SharedFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class RoomActivity : AppCompatActivity() {

    lateinit var tvNumber: TextView
    lateinit var tvInfo: TextView
    lateinit var tvTopic: TextView
    lateinit var btnYes: Button
    lateinit var btnNo: Button

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        navigateTo(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_in_room)


        setSupportActionBar(findViewById(R.id.app_bar))
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

}