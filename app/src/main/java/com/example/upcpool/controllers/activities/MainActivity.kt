package com.example.upcpool.controllers.activities

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.upcpool.R
import com.example.upcpool.controllers.fragments.RoomFragment
import com.example.upcpool.controllers.fragments.SaveFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        navigateTo(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigation)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigateTo(navView.menu.findItem(R.id.menu_profile))
        supportActionBar?.setHomeButtonEnabled(true);
        Log.d("ENTRO", "Si entro we2")

        val bottomNavigationView =
            findViewById<View>(R.id.bottom_navigation) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> setContentView(R.layout.activity_main)
                R.id.menu_profile -> setContentView(R.layout.room_details)
                R.id.menu_shared -> setContentView(R.layout.fragment_room)
            }
            true
        }
    }




    private fun getFragmentFor(item: MenuItem): Fragment {
        return when(item.itemId) {
            R.id.menu_profile -> RoomFragment()
            R.id.menu_home -> SaveFragment()
            else -> RoomFragment()
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
