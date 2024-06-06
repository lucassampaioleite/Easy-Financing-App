package com.example.easyfinancing.ui.adapters.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.card.CardBill

class DialogCardAdapter(val context: Context, val list: MutableList<CardBill>) :
    RecyclerView.Adapter<DialogCardAdapter.DialogCardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogCardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_card_new_mov, parent, false)
        return DialogCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DialogCardViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class DialogCardViewHolder(itemView: View) : ViewHolder(itemView){
        fun bind(dialogCard : CardBill){
            itemView.findViewById<TextView>(R.id.dialog_recycle_card_name).setText(dialogCard.nickname)
        }
    }
}