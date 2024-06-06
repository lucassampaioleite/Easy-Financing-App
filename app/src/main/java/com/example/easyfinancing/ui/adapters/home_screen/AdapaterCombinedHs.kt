package com.example.easyfinancing.ui.adapters.home_screen

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.BudgetsActivity
import com.example.easyfinancing.ui.CardActivity
import com.example.easyfinancing.ui.models.home_screen.Page1
import com.example.easyfinancing.ui.models.home_screen.Page2

class AdapaterCombinedHs (private val context : Context, private val list: MutableList<Any>):
    RecyclerView.Adapter<ViewHolder>() {

    private val VIEW_PAGE1 = 1
    private val VIEW_PAGE2 = 2

    override fun getItemViewType(position: Int): Int {
        return when(list[position]){
            is Page1 -> VIEW_PAGE1
            is Page2 -> VIEW_PAGE2
            else -> throw IllegalArgumentException("Aconteceu um erro ao buscar os resumos")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_PAGE1 -> {
                val view = LayoutInflater.from(context).inflate(R.layout.recycle_hs_budget, parent, false)
                Page1ViewHolder(view)
            }
            VIEW_PAGE2 -> {
                val view = LayoutInflater.from(context).inflate(R.layout.recycle_hs_cardbill, parent, false)
                Page2ViewHolder(view)
            }
            else -> throw IllegalArgumentException("Aconteceu um erro ao buscar os resumos")
        }
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder){
            is Page1ViewHolder -> holder.bind(list[position] as Page1)
            is Page2ViewHolder -> holder.bind(list[position] as Page2)
        }
    }

    inner class Page1ViewHolder(itemView: View) : ViewHolder(itemView){
        val totalReservado = itemView.findViewById<TextView>(R.id.budget_total)
        val totaUsado = itemView.findViewById<TextView>(R.id.budget_used)
        val barText = itemView.findViewById<TextView>(R.id.budget_bar_porcent)
        val bar = itemView.findViewById<View>(R.id.inner_budget_bar)

        init {
            itemView.setOnClickListener {
                val BUDGETSACTIVITY = Intent(itemView.context, BudgetsActivity::class.java)
                itemView.context.startActivity(BUDGETSACTIVITY)
            }
        }

        fun bind(page1: Page1) {
            totalReservado.text = page1.TotalReservado
            totaUsado.text = page1.TotalUtilizado
            barText.text = "${(CalculatePercentual(page1.TotalReservado, page1.TotalUtilizado) * 100).toInt()}%"
            val innerBarWidth : Int = (600 * CalculatePercentual(page1.TotalReservado, page1.TotalUtilizado)).toInt()
            bar.layoutParams.width = if (innerBarWidth == 0) 1 else innerBarWidth
            bar.requestLayout()
        }

        private fun CalculatePercentual(total: String, used: String) : Float{
            val totalFloat : Float = (total.split(" ")[1].replace("." , "").replace(",", ".")).toFloat()
            val usedFloat : Float = (used.split(" ")[1].replace("." , "").replace(",", ".")).toFloat()
            val percentual : Float = usedFloat / totalFloat

            return percentual
        }
    }

    inner class Page2ViewHolder(itemView: View) : ViewHolder(itemView){
        val valorTotal = itemView.findViewById<TextView>(R.id.TotalBill)
        val vencimento = itemView.findViewById<TextView>(R.id.DueDate)

        init {
            itemView.setOnClickListener {
                val CARDBILLACTIVITY = Intent(itemView.context, CardActivity::class.java)
                itemView.context.startActivity(CARDBILLACTIVITY)
            }
        }

        fun bind(page2: Page2) {
            valorTotal.text = page2.ValorTotal
            vencimento.text = page2.VenctoData
        }
    }
}