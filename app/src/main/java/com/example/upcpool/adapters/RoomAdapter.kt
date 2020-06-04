package com.example.upcpool.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.upcpool.R
import com.example.upcpool.models.Room
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso

class RoomAdapter(private val rooms: List<Room>, private val context: Context, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvTitle = view.findViewById(R.id.tvTitle) as TextView
        val tvOverview = view.findViewById(R.id.tvOverview) as TextView

        val cvRoom = view.findViewById(R.id.cvRoom) as CardView
    }

    interface OnItemClickListener {
        fun onItemClicked(room: Room)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.prototype_room,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rooms.size
    }

    override fun onBindViewHolder(holder: RoomAdapter.ViewHolder, position: Int) {

        val room = rooms[position]

        holder.tvTitle.text = room.title
        holder.tvOverview.text = room.overview

    /*    val picBuilder = Picasso.Builder(context)
        picBuilder.downloader(OkHttp3Downloader(context))
        picBuilder.build().load(room.logo)
            .placeholder((R.drawable.ic_launcher_background))
            .error(R.drawable.ic_launcher_background)
            .into(holder.ivLogo);*/

        holder.cvRoom.setOnClickListener {
            itemClickListener.onItemClicked(room)
        }
    }
    public fun updateDelete(position: Int){
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, rooms.size);
    }

}