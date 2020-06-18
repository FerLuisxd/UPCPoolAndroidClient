package com.example.upcpool.controllers.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upcpool.R
import com.example.upcpool.adapters.RoomAdapter
import com.example.upcpool.adapters.RoomAdapter2
import com.example.upcpool.controllers.activities.MainActivity
import com.example.upcpool.database.RoomDB
import com.example.upcpool.entity.RoomDto
import com.example.upcpool.models.Availables
import com.example.upcpool.models.Reservation
import com.example.upcpool.models.Room
import com.example.upcpool.network.RoomService
import com.google.android.material.bottomnavigation.BottomNavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(){

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getReserva(view.context, view)





    }

    private fun getReserva(context: Context, view: View){

        Log.d("Init getReserva HomeFragment", "Init")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        val roomService: RoomService
        roomService = retrofit.create(RoomService::class.java)
        val request = roomService.getReservations()

        request.enqueue(object : Callback<List<Reservation>> {

            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                Log.d("Activity Fail", "Error: "+t.toString())
            }

            override fun onResponse(call: Call<List<Reservation>>, responseDetails: Response<List<Reservation>>) {

                if (responseDetails.isSuccessful) {
                    Log.d("MSG", responseDetails.message())
                    Log.d("Error Body", responseDetails.errorBody().toString())
                    Log.d("Activity Success", responseDetails.raw().toString())
                    Log.d("Activity Success", responseDetails.body().toString())

                    val allReservations: List<Reservation> = responseDetails.body() ?: ArrayList()
                    if (allReservations.isNotEmpty())
                    {
                        var reservation : Reservation = allReservations[0]

                        allReservations.forEach{
                            if(it.start < reservation.start)
                            {
                                reservation=it
                            }
                        }

                        view.findViewById<TextView>(R.id.tv_code).text = reservation.room.code
                        view.findViewById<TextView>(R.id.tv_seats).text = reservation.room.seats.toString()
                        view.findViewById<TextView>(R.id.tv_date).text = reservation.start.toString()
                        view.findViewById<TextView>(R.id.tv_office).text = reservation.room.office
                        view.findViewById<TextView>(R.id.tv_recurso1).text = reservation.room.features[0]
                        view.findViewById<TextView>(R.id.tv_recurso2).text = reservation.room.features[1]

                        view.findViewById<Button>(R.id.btn_room).isEnabled = false
                        view.findViewById<Button>(R.id.btn_room).visibility = Button.INVISIBLE
                        view.findViewById<ConstraintLayout>(R.id.actual_room).visibility = ConstraintLayout.VISIBLE

                    }
                }

                else{
                    Log.d("Home fragment error", "Error: "+responseDetails)
                }
            }

        })

    }

    /*override fun onItemClicked(room: Room) {
        RoomDB.getInstance(vieww.context).getRoomDAO().deleteRooms(room)
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        Log.d("Onclick Favoritos","No hacer nada");
    }*/
}
