package com.example.easyfinancing.ui.adapters.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.card.CardBill

class DialogCardAdapter(val context: Context, val list: MutableList<CardBill>, val onItemClicked : (CardBill) -> Unit) :
    RecyclerView.Adapter<DialogCardAdapter.DialogCardViewHolder>() {

        val viewHolders : MutableList<DialogCardViewHolder> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogCardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_card_new_mov, parent, false)
        viewHolders.add(DialogCardViewHolder(view))
        return DialogCardViewHolder(view)
    }

    override fun onBindViewHolder(holder: DialogCardViewHolder, position: Int) {
        val card = list[position]
        holder.bind(list[position])

        holder.cardView.setOnClickListener {
            onItemClicked(card)

            for (i in 0 until viewHolders.size){
                viewHolders[i].cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.light_background))
                viewHolders[i].cardView.findViewById<TextView>(R.id.dialog_recycle_card_name).setTextColor(ContextCompat.getColor(context, R.color.blue_light))
            }

            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.blue_light))
            holder.cardView.findViewById<TextView>(R.id.dialog_recycle_card_name).setTextColor(ContextCompat.getColor(context, R.color.white))
        }
    }

    override fun getItemCount(): Int = list.size

    inner class DialogCardViewHolder(itemView: View) : ViewHolder(itemView){
        val cardView : CardView = itemView.findViewById(R.id.card_cardview_dialog)

        fun bind(dialogCard : CardBill){
            itemView.findViewById<TextView>(R.id.dialog_recycle_card_name).setText(dialogCard.nickname)
        }
    }
}