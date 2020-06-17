package com.example.upcpool.controllers.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.upcpool.R
import com.example.upcpool.adapters.RoomAdapter
import com.example.upcpool.adapters.RoomAdapter2
import com.example.upcpool.controllers.activities.MainActivity
import com.example.upcpool.database.RoomDB
import com.example.upcpool.models.Availables
import com.example.upcpool.models.Room

class HomeFragment : Fragment(), RoomAdapter2.OnItemClickListener {

    var rooms: List<Room> = ArrayList()
    lateinit var recyclerView: RecyclerView
    lateinit var vieww : View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_save, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vieww = view
        rooms = RoomDB.getInstance(view.context).getRoomDAO().getAllRooms()

        recyclerView = view.findViewById(R.id.RoomSave)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter  = RoomAdapter2(rooms,view.context,this)

    }
    override fun onItemClicked(room: Room) {
        RoomDB.getInstance(vieww.context).getRoomDAO().deleteRooms(room)
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        Log.d("Onclick Favoritos","No hacer nada");
    }
}
