package com.example.easyfinancing.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.MovDate
import com.example.easyfinancing.ui.models.Movimentation

class AdapterCombined (private val context : Context, private val list : MutableList<Any>) :
    RecyclerView.Adapter<ViewHolder>() {

    private val VIEW_TYPE_DATE = 1
    private val VIEW_TYPE_MOV = 2

    override fun getItemViewType(position: Int): Int {
        return when(list[position]){
            is MovDate -> VIEW_TYPE_DATE
            is Movimentation -> VIEW_TYPE_MOV
            else -> throw IllegalArgumentException("Invalid item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_DATE -> {
                val view = LayoutInflater.from(context).inflate(R.layout.activity_date, parent, false)
                DateViewHolder(view)
            }
            VIEW_TYPE_MOV -> {
                val view = LayoutInflater.from(context).inflate(R.layout.activity_extratct_movimentation, parent, false)
                MovimentationViewHolder(view, list)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder){
            is DateViewHolder -> holder.bind(list[position] as MovDate)
            is MovimentationViewHolder -> holder.bind(list[position] as Movimentation)
        }
    }

    inner class DateViewHolder(itemView: View) : ViewHolder(itemView){
        val _date = itemView.findViewById<TextView>(R.id.mov_date)

        fun bind(date: MovDate) {
            _date.text = date.dateString
        }
    }

    class MovimentationViewHolder(itemView: View, private val list : MutableList<Any>) :
        ViewHolder(itemView) {
        val img = itemView.findViewById<ImageView>(R.id.mov_icon)
        val mainDescription = itemView.findViewById<TextView>(R.id.mov_main_text)
        val auxDescription = itemView.findViewById<TextView>(R.id.mov_aux_text)
        val movAmount = itemView.findViewById<TextView>(R.id.mov_amount)

        init{
            /*itemView.setOnLongClickListener(){
                val position = adapterPosition

                if(position != RecyclerView.NO_POSITION && list[position] is Movimentation){
                    val item = list[position] as Movimentation
                    outerCardView.setCardBackgroundColor(ContextCompat.getColor(itemView.context, R.color.blue_line))
                }

                true
            }*/

            itemView.setOnClickListener(){
                val position = adapterPosition

                if(position != RecyclerView.NO_POSITION && list[position] is Movimentation){
                    val item = list[position] as Movimentation

                    Toast.makeText(itemView.context, "callResumeActivityById(${item.id})", Toast.LENGTH_SHORT).show()
                }
            }
        }
        fun bind(movimentation: Movimentation) {
            img.setImageResource(movimentation.img)
            mainDescription.text = movimentation.mainDescription
            auxDescription.text = movimentation.auxDescription
            movAmount.text = movimentation.movAmount
        }
    }
}