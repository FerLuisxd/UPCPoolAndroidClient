package com.example.upcpool.controllers.fragments

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.upcpool.R
import com.example.upcpool.controllers.activities.RoomActivity
import com.example.upcpool.database.RoomDB
import com.example.upcpool.entity.UserDto
import com.example.upcpool.models.Availables
import com.example.upcpool.models.Reservation
import com.example.upcpool.models.Seat
import com.example.upcpool.network.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment : Fragment(){

    lateinit var token : String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        token  = RoomDB.getInstance(view.context).getTokenDAO().getLastToken().token

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
                        view.findViewById<ConstraintLayout>(R.id.actual_reserve).visibility = ConstraintLayout.VISIBLE

                        val layout = view.findViewById<ConstraintLayout>(R.id.actual_reserve)
                        layout.setOnClickListener{
                            if (reservation.active)
                            {
                                val intento = Intent(context, RoomActivity::class.java)
                                intento.putExtra("Room", reservation.room)
                                startActivity(intento)
                            }
                            else {
                                val r = roomService.getUser()
                                r.enqueue(object : Callback<UserDto> {
                                    override fun onFailure(call: Call<UserDto>, t: Throwable) {
                                        println("Fallo en obtener el user")
                                    }

                                    override fun onResponse(
                                        call: Call<UserDto>,
                                        responseDetails: Response<UserDto>
                                    ) {
                                        if (responseDetails.isSuccessful) {
                                            Log.d("MSG", responseDetails.message())
                                            Log.d("Error Body", responseDetails.errorBody().toString())
                                            Log.d("Activity Success", responseDetails.raw().toString())
                                            Log.d("Activity Success", responseDetails.body().toString())
                                            val usuario = responseDetails.body()
                                            println("Pudo obtener el user")
                                            if (usuario != null) {
                                                println("Entre")
                                                if (usuario.inRoom){
                                                    val intento = Intent(context, RoomActivity::class.java)
                                                    intento.putExtra("Room", reservation.room)
                                                    startActivity(intento)
                                                }
                                                else
                                                {
                                                    activar(reservation, context)
                                                }
                                            }
                                        }
                                        else
                                        {
                                            println("La respuesta no fue 200")
                                        }
                                    }
                                })
                            }
                        }

                    }
                }

                else{
                    Log.d("Home fragment error", "Error: "+responseDetails)
                }
            }

        })

    }
    private fun getHeaderMap(token : String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer $token"
        return headerMap
    }
    private fun activar(reservation : Reservation, context : Context)
    {
        println("Entro a la funcion de activar")
        if (reservation != null) {

            val alertbox = this.context?.let {
                AlertDialog.Builder(it)
                    .setMessage("¿Desea activar el cubículo?")
                    .setPositiveButton("Si") { arg0, arg1 ->

                        val retrofit = Retrofit.Builder()
                            .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()

                        val roomService: RoomService
                        roomService = retrofit.create(RoomService::class.java)
                        val request = roomService.activateReservation(reservation._id,getHeaderMap(token))

                        request.enqueue(object : Callback<Reservation> {
                            override fun onFailure(call: Call<Reservation>, t: Throwable) {
                                Log.d("Home Fragment Fail en Activar reserva", "Error: "+t.toString())
                            }

                            override fun onResponse(
                                call: Call<Reservation>,
                                responseDetails: Response<Reservation>
                            ) {
                                if (responseDetails.isSuccessful) {
                                    Log.d("MSG", responseDetails.message())
                                    Log.d("Error Body", responseDetails.errorBody().toString())
                                    Log.d("Activity Success", responseDetails.raw().toString())
                                    Log.d("Activity Success", responseDetails.body().toString())

                                    val activo = responseDetails.body() ?: null

                                    if (activo != null) {
                                        if (activo.active)
                                        {
                                            val mAlertDialog = AlertDialog.Builder(context)
                                            mAlertDialog.setTitle("Cubiculo activado!!!")
                                            mAlertDialog.setPositiveButton("Ok") { dialog, id ->
                                                val intento = Intent(context, RoomActivity::class.java)
                                                intento.putExtra("Room", activo.room)
                                                startActivity(intento)
                                            }
                                            mAlertDialog.show()
                                        }
                                        else {
                                            val mAlertDialog = AlertDialog.Builder(context)
                                            mAlertDialog.setTitle("Cubiculo activado!!!")
                                            mAlertDialog.setMessage("Falta que el otro estudiante active el cubiculo")
                                            mAlertDialog.setPositiveButton("Ok") { dialog, id ->
                                                val intento = Intent(context, RoomActivity::class.java)
                                                intento.putExtra("Room", activo.room)
                                                startActivity(intento)
                                            }
                                            mAlertDialog.show()
                                        }
                                    }
                                }
                                else{
                                    Log.d("Error en HomeFragment, Activar reserva, respuesta no es 200", "Error: "+responseDetails)
                                }
                            }
                        })
                    }
                    .setNegativeButton(
                        "No" // do something when the button is clicked
                    ) { arg0, arg1 ->
                    }
                    .show()
            }

        }

    }

    /*override fun onItemClicked(room: Room) {
        RoomDB.getInstance(vieww.context).getRoomDAO().deleteRooms(room)
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        Log.d("Onclick Favoritos","No hacer nada");
    }*/
}
