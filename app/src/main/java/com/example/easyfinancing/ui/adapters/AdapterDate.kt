package com.example.easyfinancing.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.MovDate

class AdapterDate (private val context: Context, private val movDate : MutableList<MovDate>):
    RecyclerView.Adapter<AdapterDate.DateViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DateViewHolder {
        val dateList = LayoutInflater.from(context).inflate(R.layout.activity_date, parent, false)
        val holder = DateViewHolder(dateList)
        return holder
    }

    override fun onBindViewHolder(holder: AdapterDate.DateViewHolder, position: Int) {
        holder.date.text = movDate[position].dateString
    }

    override fun getItemCount(): Int = movDate.size

    inner class DateViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val date = itemView.findViewById<TextView>(R.id.mov_date)
    }
}