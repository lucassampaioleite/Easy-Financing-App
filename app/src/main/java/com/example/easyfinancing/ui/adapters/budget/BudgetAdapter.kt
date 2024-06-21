package com.example.easyfinancing.ui.adapters.budget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.budget.Budget

class BudgetAdapter(private val context : Context, private val list : MutableList<Budget>) :
    RecyclerView.Adapter<BudgetAdapter.BudgetViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_budget_item, parent, false)
        return BudgetViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: BudgetViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class BudgetViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun bind(budget: Budget) {
            itemView.findViewById<TextView>(R.id.budget_title).setText(budget.name)
            itemView.findViewById<TextView>(R.id.budget_total).setText(budget.value)
        }

    }
}