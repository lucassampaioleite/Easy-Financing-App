package com.example.easyfinancing.ui.adapters.card

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.ui.adapters.category.CategoryIconPickerAdapter
import com.example.easyfinancing.ui.adapters.extract.AdapterCombinedEx
import com.example.easyfinancing.ui.models.card.CardBill
import com.example.easyfinancing.ui.models.extract.MovDate
import com.example.easyfinancing.ui.models.extract.Movimentation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

class CardAdapter(val context: Context, var listCard : MutableList<CardBill>) : Adapter<CardAdapter.CardViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_card_item, parent,false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int = listCard.size

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(listCard[position])

        fun collapse(view: View) {
            view.apply {
                pivotY = 0f
                animate()
                    .scaleY(0f)
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
                pivotX = 0f
                scaleY = 0f
                animate()
                    .scaleY(1f)
                    .setDuration(100)
                    .setListener(null)
            }
        }

        holder.itemView.findViewById<ImageView>(R.id.card_icon_image).setOnClickListener {
            collapse(holder.itemView.findViewById(R.id.delete_card_button))
        }

        holder.itemView.findViewById<ImageView>(R.id.card_icon_image).setOnLongClickListener{
            expand(holder.itemView.findViewById(R.id.delete_card_button))
            true
        }
    }

    inner class CardViewHolder(itemView: View) : ViewHolder(itemView){

        val dataBase = AppDatabase.getInstance(context)
        val movDao = dataBase.movimentationDao()
        val cardDao = dataBase.cardDao()

        fun bind(card: CardBill){
            itemView.findViewById<ImageButton>(R.id.delete_card_button).visibility = View.GONE
            itemView.findViewById<TextView>(R.id.card_name).setText(card.nickname)
            itemView.findViewById<TextView>(R.id.card_due).setText(card.due)

            val recyclerView : RecyclerView = itemView.findViewById(R.id.card_movimentation)
            val movimentation : MutableList<Any> = mutableListOf()

            CoroutineScope(Dispatchers.Main).launch {
                val MovList = movDao.getMovimentationByCard(card.id)

                var billTotal = 0f

                for (mov in MovList){
                    setNovaMovimentacao(
                        Movimentation(
                            mov.id,
                            LocalDate.parse(mov.data),
                            mov.tipo,
                            mov.descricao,
                            mov.categoriaId,
                            mov.valor,
                            mov.cartaoId,
                            mov.cartaoParcelas,
                            mov.recorrencia,
                            mov.orcamentoId),
                        movimentation
                    )

                    billTotal += if (mov.tipo) (formatValue(mov.valor)/mov.cartaoParcelas) else -(formatValue(mov.valor)/mov.cartaoParcelas)
                }

                recyclerView.layoutManager = LinearLayoutManager(context)
                recyclerView.setHasFixedSize(true)

                val MovsAdapte = AdapterCombinedEx(context, movimentation){}
                recyclerView.adapter = MovsAdapte

                val symbols = DecimalFormatSymbols(Locale("pt", "BR"))
                symbols.decimalSeparator = ','
                symbols.groupingSeparator = '.'

                itemView.findViewById<TextView>(R.id.card_bill_total).setText(
                    "R$ ${DecimalFormat("#,##0.00", symbols).format(Math.abs(billTotal))}"
                )
            }

            itemView.findViewById<ImageButton>(R.id.delete_card_button).setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    if (!movDao.verifyIfCardUsed(card.id)){
                        cardDao.deleteCard(card.id)
                        withContext(Dispatchers.Main){
                            if (adapterPosition != RecyclerView.NO_POSITION){
                                listCard.removeAt(adapterPosition)
                                notifyItemRemoved(adapterPosition)
                            }
                        }
                    }else{
                        withContext(Dispatchers.Main){
                            Toast.makeText(context, "Cartão em uso.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    fun setNovaMovimentacao(novaMov : Movimentation, listMov : MutableList<Any>){

        if(listMov.isEmpty()){
            listMov.add(MovDate(novaMov.date.toString()))
            listMov.add(novaMov)
        }else{

            val lastItem = listMov.last()

            if(lastItem is Movimentation && lastItem.date == novaMov.date){
                listMov.add(novaMov)
            }
            else{
                listMov.add(MovDate(novaMov.date.toString()))
                listMov.add(novaMov)
            }
        }
    }

    fun updateCardList(newCardList : MutableList<CardBill>){
        listCard = newCardList
        notifyDataSetChanged()
    }

    fun formatValue(value : String) : Float {
        return value
            .replace("R$ ", "")
            .replace(".", "")
            .replace(",", ".").toFloat()
    }
}