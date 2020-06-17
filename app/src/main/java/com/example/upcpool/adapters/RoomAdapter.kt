package com.example.upcpool.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.upcpool.R
import com.example.upcpool.models.Available
import com.example.upcpool.models.Availables
import com.example.upcpool.models.Room

class RoomAdapter(private val availables: List<Availables>, private val rooms: List<Room>, private val context: Context, private val itemClickListener: OnItemClickListener) : RecyclerView.Adapter<RoomAdapter.ViewHolder>() {

    var auxHelper: MutableList<Int> = ArrayList()

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
        fun onItemClicked(room: Room)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.prototype_cubiculo,parent,false)

        var count: Int = 0
        availables.forEach {
            it.available.forEach {
                auxHelper.add(count)
            }
            count++
        }

        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        var count: Int = 0
        availables.forEach {
            it.available.forEach {
                count++
            }
        }
        return count
    }

    override fun onBindViewHolder(holder: RoomAdapter.ViewHolder, position: Int) {

        val room = rooms[position]
        holder.tvNumber.text = room.code
        holder.tvTopic.text = "TODO"
        holder.tvInfo.text = room.office
        holder.tvResources.text = room.features[0] + room.features[1]
        holder.tvSitsLeft.text = room.seats.toString()
        holder.tvDate.text = availables[auxHelper[position]].start.toString()


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