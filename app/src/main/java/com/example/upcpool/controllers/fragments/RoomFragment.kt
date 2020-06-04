package com.example.upcpool.controllers.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upcpool.R
import com.example.upcpool.adapters.RoomAdapter
import com.example.upcpool.controllers.activities.DetailsActivity
import com.example.upcpool.models.ApiResponseDetails
import com.example.upcpool.models.Room
import com.example.upcpool.network.RoomService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class RoomFragment : Fragment(), RoomAdapter.OnItemClickListener {
    var room: List<Room> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var editText: EditText
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_room, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.rvRooms)

        loadRooms(view.context,"terminator")

        editText = view.findViewById(R.id.editText)
        editText.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                loadRooms(view.context,s.toString())
            }

            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }

    private fun loadRooms(context: Context, string: String){

        Log.d("Init load", "Init")

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.theroomdb.org/3/search/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        //DECLARAMOS NUESTRO OBJETO RoomService
        val roomService: RoomService
        roomService = retrofit.create(RoomService::class.java)
        val request = roomService.getRooms("3cae426b920b29ed2fb1c0749f258325",string)

        
        request.enqueue(object : Callback<ApiResponseDetails> {

            override fun onFailure(call: Call<ApiResponseDetails>, t: Throwable) {
                Log.d("Activity Fail", "Error: "+t.toString())
            }

            override fun onResponse(call: Call<ApiResponseDetails>, responseDetails: Response<ApiResponseDetails>) {

                if (responseDetails.isSuccessful) {
                    Log.d("MSG", responseDetails.message())
                    Log.d("Error Body", responseDetails.errorBody().toString())
                    Log.d("Activity Success", responseDetails.raw().toString())
                    Log.d("Activity Success", responseDetails.body().toString())
                    Log.d("Activity Success 1", responseDetails.body()?.results.toString())
                    Log.d("Activity Success 2", responseDetails.body()?.results?.size.toString())


                    val rooms: List<Room> = responseDetails.body()!!.results ?: ArrayList()
                    println("aaa" + responseDetails.body())
                    recyclerView.layoutManager = LinearLayoutManager(context)
                    recyclerView.adapter = RoomAdapter(rooms,context,this@RoomFragment)
                }

                else{
                    Log.d("Activity Fail", "Error: "+responseDetails.code())
                }
            }

        })

    }

    override fun onItemClicked(room: Room) {
        Log.d("Principal", "Seleccionando detalle ID: "+room.id)
        val intento = Intent(context, DetailsActivity::class.java)
        intento.putExtra("Room",room)
        startActivity(intento)
    }


}
