package com.example.easyfinancing.ui.adapters.extract

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.extract.MovDate
import com.example.easyfinancing.ui.models.extract.Movimentation

class AdapterCombinedEx (private val context : Context, private val list : MutableList<Any>) :
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
                val view = LayoutInflater.from(context).inflate(R.layout.recycle_mov_date, parent, false)
                DateViewHolder(view)
            }
            VIEW_TYPE_MOV -> {
                val view = LayoutInflater.from(context).inflate(R.layout.recycle_extratct_mov, parent, false)
                MovimentationViewHolder(view)
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

    inner class MovimentationViewHolder(itemView: View) : ViewHolder(itemView) {

        fun bind(movimentation: Movimentation) {
            itemView.findViewById<ImageView>(R.id.mov_icon).setImageResource(movimentation.icon)
            itemView.findViewById<TextView>(R.id.mov_main_text).text = movimentation.mainDescription
            itemView.findViewById<TextView>(R.id.mov_aux_text).text = movimentation.categoryName
            itemView.findViewById<TextView>(R.id.mov_amount).text = movimentation.movAmount

            if (!movimentation.cardIcon) itemView.findViewById<ImageView>(R.id.mov_card_icon).setImageDrawable(null)
            if (!movimentation.recurenceIcon) itemView.findViewById<ImageView>(R.id.mov_recurence_icon).setImageDrawable(null)
            if (!movimentation.budgetIcon) itemView.findViewById<ImageView>(R.id.mov_budget_icon).setImageDrawable(null)
        }
    }
}