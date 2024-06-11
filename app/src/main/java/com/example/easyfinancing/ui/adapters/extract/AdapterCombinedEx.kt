package com.example.easyfinancing.ui.adapters.extract

import android.content.Context
import android.content.SyncResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.CategoryDao
import com.example.easyfinancing.ui.models.extract.MovDate
import com.example.easyfinancing.ui.models.extract.Movimentation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class AdapterCombinedEx (private val context : Context, private val list : MutableList<Any>, val onItemClicked : (Movimentation) -> Unit) :
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
            is MovimentationViewHolder -> {
                val movimentation = list[position] as Movimentation

                holder.bind(movimentation)

                holder.cardView.setOnLongClickListener{
                    onItemClicked(movimentation)
                    true
                }
            }
        }
    }

    inner class DateViewHolder(itemView: View) : ViewHolder(itemView){
        val _date = itemView.findViewById<TextView>(R.id.mov_date)

        fun bind(date: MovDate) {
            _date.text = getDateExpanded(date.dateString)
        }

        fun getDateExpanded(date: String) : String{
            val formmater = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("pt", "BR"))
            var dateFormatted = formmater.format(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date))
            dateFormatted = dateFormatted.replaceFirstChar{it.uppercase()}
            dateFormatted = dateFormatted.replace("-feira", "")

            return dateFormatted
        }
    }

    inner class MovimentationViewHolder(itemView: View) : ViewHolder(itemView) {
        val cardView : CardView = itemView.findViewById(R.id.mov_card)

        val dataBase : AppDatabase = AppDatabase.getInstance(context)
        val categoryDao : CategoryDao = dataBase.categoryDao()

        fun bind(movimentation: Movimentation) {
            itemView.findViewById<ImageView>(R.id.mov_icon).setImageResource(icon_type(movimentation.type))
            itemView.findViewById<TextView>(R.id.mov_main_text).text = movimentation.mainDescription
            if (movimentation.categoryId != 0) {
                itemView.findViewById<TextView>(R.id.mov_aux_text).text = getCategoryName(movimentation.categoryId)
            } else {
                itemView.findViewById<TextView>(R.id.mov_aux_text).visibility = View.GONE
            }

            if (movimentation.cardId !=0) {
                itemView.findViewById<TextView>(R.id.mov_amount).text = getInstalmentValue(movimentation.movAmount, movimentation.cardInstalments)
            }else{
                itemView.findViewById<TextView>(R.id.mov_amount).text = movimentation.movAmount
            }

            if (movimentation.cardId == 0) itemView.findViewById<ImageView>(R.id.mov_card_icon).visibility = View.GONE
            if (movimentation.recurenceId  == 0) itemView.findViewById<ImageView>(R.id.mov_recurence_icon).visibility = View.GONE
            if (movimentation.budgetId  == 0) itemView.findViewById<ImageView>(R.id.mov_budget_icon).visibility = View.GONE
        }

        fun icon_type(tipo : Boolean) : Int{
            if(tipo){
                return R.drawable.arrow_drop_up
            }
            return R.drawable.arrow_drop_down
        }

        fun getCategoryName(CategoryId : Int) : String{

            var CategoryName : String

            runBlocking {
                CategoryName = withContext(Dispatchers.IO){
                    categoryDao.getCategoryName(CategoryId)
                }
            }

            return CategoryName
        }

        fun getInstalmentValue(valor : String, parcelas : Int) : String{

            val valorFloat = valor.replace("R$ ", "").replace(".", "").replace(",", ".").toFloat()
            val valorParcela = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(valorFloat / parcelas)

            return valorParcela
        }
    }
}