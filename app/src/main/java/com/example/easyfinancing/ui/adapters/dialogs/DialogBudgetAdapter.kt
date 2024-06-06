package com.example.easyfinancing.ui.adapters.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.budget.Budget

class DialogBudgetAdapter(val context: Context, val list: MutableList<Budget>) :
    RecyclerView.Adapter<DialogBudgetAdapter.DialogBudgetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogBudgetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_budget_new_mov, parent, false)
        return DialogBudgetViewHolder(view)
    }

    override fun onBindViewHolder(holder: DialogBudgetViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class DialogBudgetViewHolder(itemView: View) : ViewHolder(itemView){
        fun bind(dialogBudget : Budget){
            itemView.findViewById<TextView>(R.id.dialog_recycle_budget_name).setText(dialogBudget.name)
            itemView.findViewById<TextView>(R.id.dialog_recycle_budget_percent).setText("0%")
        }
    }
}