package com.example.upcpool.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.upcpool.R
import com.example.upcpool.entity.RoomDto
import com.example.upcpool.models.Available
import com.example.upcpool.models.Availables
import com.example.upcpool.models.Room
import java.util.*

class RoomAdapter(private val rooms: List<RoomDto>, private val context: Context, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    class ViewHolder(val view: View): RecyclerView.ViewHolder(view){
        val tvNumber = view.findViewById(R.id.tvNumber) as TextView
        val tvTopic = view.findViewById(R.id.tvTopic) as TextView
        val tvInfo = view.findViewById(R.id.tvInfo) as TextView
        val tvResources = view.findViewById(R.id.tvResources) as TextView
        val tvSitsLeft = view.findViewById(R.id.tvSitsLeft) as TextView
        val tvDate = view.findViewById(R.id.tvDate) as TextView

        val cvRoom = view.findViewById(R.id.cvRoom) as CardView
    }

    interface OnItemClickListener {
        fun onItemClicked(room: RoomDto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.prototype_cubiculo,parent,false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return rooms.count()
    }

    override fun onBindViewHolder(holder: RoomAdapter.ViewHolder, position: Int) {

        val room = rooms[position]
        holder.tvNumber.text = room.code
        holder.tvTopic.text = room.theme
        holder.tvInfo.text = room.office

        var auxResource: String = ""
        room.features.forEach(){
            auxResource += it
        }
        holder.tvResources.text = auxResource
        holder.tvSitsLeft.text = room.seats.toString()

        val date = room.date
        val cal = Calendar.getInstance()
        cal.setTime(date)
        cal.add(Calendar.HOUR, -5)
        val aux:Date = cal.getTime()
        //holder.tvDate.text = room.date.toString()
        holder.tvDate.text = aux.toString()


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
        notifyItemRangeChanged(position, getItemCount());
    }

}