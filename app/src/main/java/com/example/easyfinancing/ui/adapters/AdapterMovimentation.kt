package com.example.easyfinancing.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.example.easyfinancing.R
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.ui.models.Movimentation

class AdapterMovimentation(private val context : Context, private val movimentation : MutableList<Movimentation>) :
    RecyclerView.Adapter<AdapterMovimentation.MovimentationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovimentationViewHolder {
        val movList = LayoutInflater.from(context).inflate(R.layout.activity_extratct_movimentation, parent, false)
        val holder = MovimentationViewHolder(movList)
        return holder
    }

    override fun getItemCount(): Int = movimentation.size

    override fun onBindViewHolder(holder: MovimentationViewHolder, position: Int) {
        holder.img.setImageResource(movimentation[position].img)
        holder.mainDescription.text = movimentation[position].mainDescription
        holder.auxDescription.text = movimentation[position].auxDescription
        holder.movAmount.text = movimentation[position].movAmount
    }

    inner class MovimentationViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val img = itemView.findViewById<ImageView>(R.id.mov_icon)
        val mainDescription = itemView.findViewById<TextView>(R.id.mov_main_text)
        val auxDescription = itemView.findViewById<TextView>(R.id.mov_aux_text)
        val movAmount = itemView.findViewById<TextView>(R.id.mov_amount)
    }
}