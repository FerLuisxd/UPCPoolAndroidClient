package com.example.upcpool.controllers.activities

import android.content.Intent
import android.drm.DrmStore
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.upcpool.R
import com.example.upcpool.controllers.fragments.RoomFragment
import com.example.upcpool.controllers.fragments.HomeFragment
import com.example.upcpool.controllers.fragments.SharedFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.app_bar.*


class MainActivity : AppCompatActivity() {

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        navigateTo(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.bottom_navigationView)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        navigateTo(navView.menu.findItem(R.id.menu_home))
        supportActionBar?.setHomeButtonEnabled(true);
        setSupportActionBar(findViewById(R.id.app_bar))

        Log.d("ENTRO", "Si entro we2")

        logo_superior.setOnClickListener{
            val mAlertDialog = AlertDialog.Builder(this)
            mAlertDialog.setTitle("¿Desea cerrar sesión?")
            mAlertDialog.setPositiveButton("Si"){a, b ->


                val intento = Intent(this, RoomStart::class.java)
                startActivity(intento)
            }
            mAlertDialog.setNegativeButton("No"){a, b ->

            }
            mAlertDialog.show()
        }

        /*val bottomNavigationView =
            findViewById<View>(R.id.bottom_navigationView) as BottomNavigationView
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_home -> setContentView(R.layout.activity_main)
                R.id.menu_reserve -> setContentView(R.layout.room_details)
                R.id.menu_shared -> setContentView(R.layout.fragment_room)
            }
            true
        }*/
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
