package com.example.easyfinancing.ui.adapters.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.database.models.BudgetsModel
import com.example.easyfinancing.ui.models.budget.Budget

class DialogBudgetAdapter(val context: Context, val list: MutableList<BudgetsModel>, val onItemCliked : (BudgetsModel) -> Unit) :
    RecyclerView.Adapter<DialogBudgetAdapter.DialogBudgetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogBudgetViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_budget_new_mov, parent, false)
        return DialogBudgetViewHolder(view)
    }

    override fun onBindViewHolder(holder: DialogBudgetViewHolder, position: Int) {
        val budget = list[position]
        holder.bind(list[position])

        holder.cardView.setOnClickListener {
            onItemCliked(budget)
        }
    }

    override fun getItemCount(): Int = list.size

    inner class DialogBudgetViewHolder(itemView: View) : ViewHolder(itemView){
        val cardView : CardView = itemView.findViewById(R.id.budget_cardview_dialog)
        fun bind(dialogBudget : BudgetsModel){
            itemView.findViewById<TextView>(R.id.dialog_recycle_budget_name).setText(dialogBudget.nameBudgets)
            itemView.findViewById<TextView>(R.id.dialog_recycle_budget_percent).setText("0%")
        }
    }
}