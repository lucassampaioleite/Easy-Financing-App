package com.example.easyfinancing.ui.adapters.category

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.CategoryDao
import com.example.easyfinancing.database.daos.MovimetationDao
import com.example.easyfinancing.ui.models.category.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import kotlinx.coroutines.withTimeout
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

class CategoryAdapter(val context: Context, val list : MutableList<Category>, val balanceCallBack : (incomes : String, outcomes : String) -> Unit) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

        private var Entradas = 0f
        private var Saidas = 0f

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_category_item, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {

        val dataBase = AppDatabase.getInstance(holder.itemView.context)
        val categoryDao = dataBase.categoryDao()
        val movimentationDao = dataBase.movimentationDao()

        val category = list[position]

        holder.bind(list[position])

        fun collapse(view: View) {
            view.apply {
                pivotX = width.toFloat()
                animate()
                    .scaleX(0f)
                    .setDuration(100)
                    .setListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationEnd(animation: Animator) {
                            visibility = View.GONE
                        }
                    })
            }
        }

        fun expand(view: View) {
            view.apply {
                visibility = View.VISIBLE
                pivotX = width.toFloat()
                scaleX = 0f
                animate()
                    .scaleX(1f)
                    .setDuration(100)
                    .setListener(null)
            }
        }

        suspend fun getCategoryBalance(categoryId : Int) : Float {
            val MovsList = movimentationDao.getMovimentationByCategory(categoryId)
            var balance = 0f

            return withContext(Dispatchers.IO) {

                for (mov in MovsList) {
                    val valor = mov.valor
                        .replace("R$ ", "")
                        .replace(".", "")
                        .replace(",", ".").toFloat()

                    balance += if (mov.tipo) valor else -valor
                }
                balance
            }
        }

        holder.itemView.findViewById<CardView>(R.id.category_card).setOnClickListener {
            collapse(holder.itemView.findViewById<ImageButton>(R.id.delete_category))
        }

        holder.itemView.findViewById<CardView>(R.id.category_card).setOnLongClickListener {

            expand(holder.itemView.findViewById<ImageButton>(R.id.delete_category))

            holder.itemView.findViewById<ImageButton>(R.id.delete_category).setOnClickListener {

                CoroutineScope(Dispatchers.IO).launch {

                    if (!movimentationDao.verifyIfCategoryExist(category.id)){
                        categoryDao.deleteCategory(category.id)
                        withContext(Dispatchers.Main){
                            if (holder.adapterPosition != RecyclerView.NO_POSITION){
                                list.removeAt(holder.adapterPosition)
                                notifyItemRemoved(holder.adapterPosition)
                            }
                        }
                    }else{
                        withContext(Dispatchers.Main){
                            collapse(holder.itemView.findViewById<ImageButton>(R.id.delete_category))
                            Toast.makeText(holder.itemView.context, "Categoria em uso.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

            true
        }

        CoroutineScope(Dispatchers.Main).launch{
            val balance = getCategoryBalance(category.id)

            if (balance < 0){
                holder.itemView.findViewById<ImageView>(R.id.category_card_icon).background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.round_background_red)
                holder.itemView.findViewById<ImageView>(R.id.category_card_icon).rotation = 180.0f
                Saidas += Math.abs(balance)
            }else{
                Entradas += balance
            }

            val symbols = DecimalFormatSymbols(Locale("pt", "BR"))
            symbols.decimalSeparator = ','
            symbols.groupingSeparator = '.'

            holder.itemView.findViewById<TextView>(R.id.category_total_value).setText("R$ " + DecimalFormat("#,##0.00", symbols).format(Math.abs(balance)))



            balanceCallBack("R$ ${DecimalFormat("#,##0.00", symbols).format(Entradas)}",
                            "R$ ${DecimalFormat("#,##0.00", symbols).format(Saidas)}")
        }
    }

    override fun getItemCount(): Int = list.size

    inner class CategoryViewHolder(itemView: View) : ViewHolder(itemView){
        fun bind(category: Category){
            itemView.findViewById<TextView>(R.id.category_name).setText(category.name)
            itemView.findViewById<ImageView>(R.id.category_icon).setImageResource(category.icon)
            itemView.findViewById<ImageView>(R.id.category_icon).colorFilter = PorterDuffColorFilter(ContextCompat.getColor(itemView.context, R.color.blue_light), PorterDuff.Mode.SRC_ATOP)
            itemView.findViewById<TextView>(R.id.category_total_value)

            itemView.findViewById<ImageButton>(R.id.delete_category).visibility = View.GONE
        }
    }
}