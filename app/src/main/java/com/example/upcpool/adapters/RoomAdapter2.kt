package com.example.upcpool.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.upcpool.R
import com.example.upcpool.models.Room

class RoomAdapter2(private val rooms: List<Room>, private val context: Context, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RoomAdapter2.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvTitle = view.findViewById(R.id.tvTitle) as TextView
        val tvOverview = view.findViewById(R.id.tvOverview) as TextView

        val cvRoom = view.findViewById(R.id.cvRoom2) as CardView
    }

    interface OnItemClickListener {
        fun onItemClicked(room: Room)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter2.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.prototype_room,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: RoomAdapter2.ViewHolder, position: Int) {

        val room = rooms[position]

        holder.tvTitle.text = room.code
        holder.tvOverview.text = room.office

        holder.cvRoom.setOnClickListener {
            itemClickListener.onItemClicked(room)
        }
    }
}