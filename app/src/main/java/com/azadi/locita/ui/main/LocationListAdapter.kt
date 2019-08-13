package com.azadi.locita.ui.main

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import com.azadi.locita.R
import com.azadi.locita.base.GlobalData
import com.azadi.locita.data.db.DataBaseBuilder
import com.azadi.locita.data.db.LocationModel
import com.google.android.gms.common.data.DataHolder


class LocationListAdapter(
    context: Context
) : RecyclerView.Adapter<LocationListAdapter.LocationViewHolder>() {

    var mContext: Context = context
    val inflater: LayoutInflater = LayoutInflater.from(context)
    var list = emptyList<LocationModel>() // Cached copy of words
    var database: DataBaseBuilder? = null

    inner class LocationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtBody: TextView = itemView.findViewById(R.id.txtBody)
        val btnRemove: Button = itemView.findViewById(R.id.btnRemove)
        val layout: LinearLayout = itemView.findViewById(R.id.layoutItemLocation)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val itemView = inflater.inflate(R.layout.item_list, parent, false)
        return LocationViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {

        val current = list[position]

//        holder.txtBody.text = current.title + "     /   " + current.lat + " , " + current.lng
        holder.txtBody.text = current.title




        holder.layout.setOnClickListener(View.OnClickListener {

            if (GlobalData.location != null){
                var latOrigin = GlobalData.location!!.latitude.toString()
                var lngOrigin =GlobalData.location!!.longitude.toString()
                var latDestionation = current.lat
                var lngDestination = current.lng
                val url = "http://maps.google.com/maps?saddr=" +
                        latOrigin + "," + lngOrigin +
                        "&daddr=" +
                        latDestionation + "," + lngDestination
                val intent = Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
                )
                mContext.startActivity(intent)
            }
            else {
                Toast.makeText(mContext,"Error",Toast.LENGTH_LONG).show()
            }


        })





        holder.btnRemove.setOnClickListener(View.OnClickListener {
            //            database = Room.databaseBuilder(mContext, DataBaseBuilder::class.java, "locita_database")
//                .allowMainThreadQueries().build()
            DataBaseBuilder.getDatabase(mContext).locationDao().delete(current.id)
            var data: List<LocationModel> = DataBaseBuilder.getDatabase(mContext).locationDao().getAll()
            list = data
            notifyDataSetChanged()
        })
    }


    internal fun setLocationList(locationList: List<LocationModel>) {
        this.list = locationList
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

}