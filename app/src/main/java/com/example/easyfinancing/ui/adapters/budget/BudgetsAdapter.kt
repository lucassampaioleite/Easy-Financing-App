package com.example.easyfinancing.ui.adapters.budget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.models.BudgetsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BudgetsAdapter(

    val context: Context,
    private val budgets: MutableList<BudgetsModel>

) : RecyclerView.Adapter<BudgetsAdapter.BudgetsViewHolder>() {

    class BudgetsViewHolder(elementsView: View) : RecyclerView.ViewHolder(elementsView) {
        fun bindingBudgets(budgets: BudgetsModel) {
            itemView.findViewById<TextView>(R.id.budget_title).text = budgets.nameBudgets
            itemView.findViewById<TextView>(R.id.budget_total).text =
                budgets.valueBudgets.toString()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BudgetsViewHolder {
        val showView =
            LayoutInflater.from(context).inflate(
                R.layout.recycle_budget_item, parent,
                false
            )
        return BudgetsViewHolder(showView)
    }

    override fun getItemCount(): Int = budgets.size

    override fun onBindViewHolder(holder: BudgetsViewHolder, position: Int) {
        val dataBase = AppDatabase.getInstance(holder.itemView.context)
        val budgetsDAO = dataBase.budgetsDao()
        val movimentationDAO = dataBase.movimentationDao()

        holder.bindingBudgets(budgets[position])

        var valueEntityTotal = 0.0

        CoroutineScope(Dispatchers.Main).launch {
            val valueMovimentation = movimentationDAO.findAllValues(budgets[position].idBudgetsModel)

            for (i in valueMovimentation.indices) {
                valueEntityTotal += adjustElements(valueMovimentation[i])
            }

            "R$ ${valueEntityTotal}".also {
                holder.itemView.findViewById<TextView>(R.id.budget_used).text = it
            }
        }
    }

    private fun adjustElements(value: String): Double =
        value
            .replace("R$", "")
            .replace(".", "")
            .replace(",", ".")
            .toDouble()
}