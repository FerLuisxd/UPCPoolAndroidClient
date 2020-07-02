package com.example.upcpool.controllers.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upcpool.R
import com.example.upcpool.adapters.RoomAdapter
import com.example.upcpool.controllers.activities.DetailsActivity
import com.example.upcpool.controllers.activities.RoomActivity
import com.example.upcpool.database.RoomDB
import com.example.upcpool.entity.Features
import com.example.upcpool.entity.RoomDto
import com.example.upcpool.entity.Share
import com.example.upcpool.models.ApiResponseDetails
import com.example.upcpool.models.Availables
import com.example.upcpool.models.Reservation
import com.example.upcpool.models.Room
import com.example.upcpool.network.RoomService
import kotlinx.android.synthetic.main.join_modal.view.*
import kotlinx.android.synthetic.main.share_modal.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList
import kotlin.time.days


class SharedFragment : Fragment(), RoomAdapter.OnItemClickListener {
    var room: List<RoomDto> = ArrayList();
    lateinit var recyclerView: RecyclerView
    lateinit var token : String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shared, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvSharedRooms)
        token  = RoomDB.getInstance(view.context).getTokenDAO().getLastToken().token

        loadShared(view.context)

    }
    private fun getHeaderMap(token : String): Map<String, String> {
        val headerMap = mutableMapOf<String, String>()
        headerMap["Authorization"] = "Bearer $token"
        return headerMap
    }
    private fun loadShared(context: Context) {

        Log.d("LoadShared Function", "Init")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val roomService: RoomService
        roomService = retrofit.create(RoomService::class.java)
        val request = roomService.getPublics(getHeaderMap(token))

        request.enqueue(object : Callback<List<Reservation>> {
            override fun onFailure(call: Call<List<Reservation>>, t: Throwable) {
                Log.d("Fallo en request de loadShared", "Error: "+t.toString())
            }

            override fun onResponse(
                call: Call<List<Reservation>>,
                responseDetails: Response<List<Reservation>>
            ) {

                if (responseDetails.isSuccessful) {
                    Log.d("MSG", responseDetails.message())
                    Log.d("Error Body", responseDetails.errorBody().toString())
                    Log.d("Activity Success", responseDetails.raw().toString())
                    Log.d("Activity Success", responseDetails.body().toString())

                    val allPublics: List<Reservation> = responseDetails.body() ?: ArrayList()
                    val rooms: MutableList<RoomDto> = ArrayList()

                    allPublics.forEach() {
                        val auxRoom = RoomDto(it.room.id, it.room.office, it.room.code, it.room.seats - it.seats.size, it.publicFeatures, it.start)
                        auxRoom.pubId = it._id
                        auxRoom.theme = it.theme
                        rooms.add(auxRoom)
                    }

                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = RoomAdapter(rooms, context, this@SharedFragment)
                }
                else{
                    Log.d("El response de loadShared no fue 200", "Error: "+responseDetails)
                }
            }
        })
    }

    override fun onItemClicked(room: RoomDto) {
        /*val alertbox = this.context?.let {
            AlertDialog.Builder(it)
                .setTitle("Elige con que elemento quieres ingresar:")
                .setItems(arrayOf("MAC", "Apple TV", "Ambos", "Ninguno")) {_, pos->
                    when (pos) {
                        0 -> {
                            Log.d("OnItemClicked para unirte a un compartido", "Init")

                            val retrofit = Retrofit.Builder()
                                .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()

                            val roomService: RoomService
                            roomService = retrofit.create(RoomService::class.java)
                            val aux : MutableList<String> = ArrayList()
                            aux.add("MAC")
                            val feat = Features(aux)
                            val request = roomService.joinRoom(room.pubId, feat,getHeaderMap(token))

                            request.enqueue(object : Callback<Reservation> {
                                override fun onFailure(call: Call<Reservation>, t: Throwable) {
                                    Log.d("Unirse al cub pub", "Error: "+t.toString())
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

                                        val intento = Intent(context, RoomActivity::class.java)
                                        intento.putExtra("Room", room)
                                        startActivity(intento)
                                    }
                                    else
                                    {
                                        val alert = context?.let { it1 -> AlertDialog.Builder(it1)
                                            .setTitle("No has podido unirte a este cubiculo")
                                            .setNeutralButton("Ok") { arg0, arg1 ->

                                            }
                                            .show()
                                        }
                                    }
                                }
                            })
                        }

                        1 -> {
                            Log.d("OnItemClicked para unirte a un compartido", "Init")

                            val retrofit = Retrofit.Builder()
                                .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()

                            val roomService: RoomService
                            roomService = retrofit.create(RoomService::class.java)
                            val aux : MutableList<String> = ArrayList()
                            aux.add("Apple TV")
                            val feat = Features(aux)
                            val request = roomService.joinRoom(room.pubId, feat,getHeaderMap(token))

                            request.enqueue(object : Callback<Reservation> {
                                override fun onFailure(call: Call<Reservation>, t: Throwable) {
                                    Log.d("Unirse al cub pub", "Error: "+t.toString())
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

                                        val intento = Intent(context, RoomActivity::class.java)
                                        intento.putExtra("Room", room)
                                        startActivity(intento)
                                    }
                                    else
                                    {
                                        val alert = context?.let { it1 -> AlertDialog.Builder(it1)
                                            .setTitle("No has podido unirte a este cubiculo")
                                            .setNeutralButton("Ok") { arg0, arg1 ->

                                            }
                                            .show()
                                        }
                                    }
                                }
                            })
                        }
                        2-> {
                            Log.d("OnItemClicked para unirte a un compartido", "Init")

                            val retrofit = Retrofit.Builder()
                                .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()

                            val roomService: RoomService
                            roomService = retrofit.create(RoomService::class.java)
                            val aux : MutableList<String> = ArrayList()
                            aux.add("MAC")
                            aux.add("Apple TV")
                            val feat = Features(aux)
                            val request = roomService.joinRoom(room.pubId, feat,getHeaderMap(token))

                            request.enqueue(object : Callback<Reservation> {
                                override fun onFailure(call: Call<Reservation>, t: Throwable) {
                                    Log.d("Unirse al cub pub", "Error: "+t.toString())
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

                                        val intento = Intent(context, RoomActivity::class.java)
                                        intento.putExtra("Room", room)
                                        startActivity(intento)
                                    }
                                    else
                                    {
                                        val alert = context?.let { it1 -> AlertDialog.Builder(it1)
                                            .setTitle("No has podido unirte a este cubiculo")
                                            .setNeutralButton("Ok") { arg0, arg1 ->

                                            }
                                            .show()
                                        }
                                    }
                                }
                            })
                        }
                        3->{
                            Log.d("OnItemClicked para unirte a un compartido", "Init")

                            val retrofit = Retrofit.Builder()
                                .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build()

                            println("Si entro")

                            val roomService: RoomService
                            roomService = retrofit.create(RoomService::class.java)
                            val aux:List<String> = ArrayList()
                            val feat = Features(aux)
                            val request = roomService.joinRoom(room.pubId, feat,getHeaderMap(token))

                            request.enqueue(object : Callback<Reservation> {
                                override fun onFailure(call: Call<Reservation>, t: Throwable) {
                                    println("Me caigo")
                                    Log.d("Unirse al cub pub", "Error: "+t.toString())
                                }

                                override fun onResponse(
                                    call: Call<Reservation>,
                                    responseDetails: Response<Reservation>
                                ) {
                                    println("Si paso")
                                    if (responseDetails.isSuccessful) {
                                        Log.d("MSG", responseDetails.message())
                                        Log.d("Error Body", responseDetails.errorBody().toString())
                                        Log.d("Activity Success", responseDetails.raw().toString())
                                        Log.d("Activity Success", responseDetails.body().toString())

                                        val intento = Intent(context, RoomActivity::class.java)
                                        intento.putExtra("Room", room)
                                        startActivity(intento)
                                    }
                                    else
                                    {
                                        Log.d("MSG", responseDetails.message())
                                        Log.d("Error Body", responseDetails.errorBody().toString())
                                        Log.d("Activity Success", responseDetails.raw().toString())
                                        Log.d("Activity Success", responseDetails.body().toString())
                                        val alert = context?.let { it1 -> AlertDialog.Builder(it1)
                                            .setTitle("No has podido unirte a este cubiculo")
                                            .setNeutralButton("Ok") { arg0, arg1 ->

                                            }
                                            .show()
                                        }
                                    }
                                }
                            })
                        }
                    }
                }
                .show()
        }*/

        val mDialogView = LayoutInflater.from(this.context).inflate(R.layout.join_modal, null)

        val mBuilder = this.context?.let {
            AlertDialog.Builder(it)
                .setView(mDialogView)
                .setTitle("Elige tus recursos")
        }

        val mAlertDialog = mBuilder?.show()

        mDialogView.btnJoin.setOnClickListener{
            if (mAlertDialog != null) {
                mAlertDialog.dismiss()
            }

            val mac = mDialogView.checkMac.isChecked
            val apple = mDialogView.checkApple.isChecked

            val features: MutableList<String> = ArrayList()

            if (mac) features.add("MAC")
            if (apple) features.add("Apple TV")

            val auxFeatures = Features(features)
            if (room != null) {
                join(room, auxFeatures)
            }
        }
    }

    private fun join(room: RoomDto, features: Features){
        val retrofit = Retrofit.Builder()
            .baseUrl("https://upc-pool-ferluisxd.cloud.okteto.net/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val token: String?  =
            this.context?.let { RoomDB.getInstance(it).getTokenDAO().getLastToken().token }
        val roomService: RoomService
        roomService = retrofit.create(RoomService::class.java)
        val request =
            token?.let { getHeaderMap(it) }?.let { roomService.joinRoom(room.pubId, features, it) }

        if (request != null) {
            request.enqueue(object : Callback<Reservation> {
                override fun onFailure(call: Call<Reservation>, t: Throwable) {
                    println("Me caigo")
                    Log.d("Unirse al cub pub", "Error: "+t.toString())
                    val mAlertDialog = context?.let { AlertDialog.Builder(it) }
                    if (mAlertDialog != null) {
                        mAlertDialog.setTitle("Ha ocurrido un error")
                        mAlertDialog.setMessage(t.message)
                        mAlertDialog.setPositiveButton("Ok") { dialog, id ->
                        }
                        mAlertDialog.show()
                    }
                }
                override fun onResponse(call: Call<Reservation>, responseDetails: Response<Reservation>) {
                    println("Si paso")
                    if (responseDetails.isSuccessful) {
                        Log.d("MSG", responseDetails.message())
                        Log.d("Error Body", responseDetails.errorBody().toString())
                        Log.d("Activity Success", responseDetails.raw().toString())
                        Log.d("Activity Success", responseDetails.body().toString())
                        val mAlertDialog = context?.let { AlertDialog.Builder(it) }
                        if (mAlertDialog != null) {
                            mAlertDialog.setTitle("Has ingresado al cubículo")
                            mAlertDialog.setPositiveButton("Ok") { dialog, id ->
                                val intento = Intent(context, RoomActivity::class.java)
                                intento.putExtra("Room", room)
                                startActivity(intento)
                            }
                            mAlertDialog.show()
                        }
                    }
                    else
                    {
                        Log.d("MSG", responseDetails.message())
                        Log.d("Error Body", responseDetails.errorBody().toString())
                        Log.d("Activity Success", responseDetails.raw().toString())
                        Log.d("Activity Success", responseDetails.body().toString())
                        val alert = context?.let { it1 -> AlertDialog.Builder(it1)
                            .setTitle("No has podido unirte a este cubiculo")
                            .setNeutralButton("Ok") { arg0, arg1 ->

                            }
                            .show()
                        }
                    }
                }
            })
        }

    }
}
