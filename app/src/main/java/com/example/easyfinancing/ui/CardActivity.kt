package com.example.easyfinancing.ui

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.CardDao
import com.example.easyfinancing.database.models.CardModel
import com.example.easyfinancing.ui.adapters.card.CardAdapter
import com.example.easyfinancing.ui.models.card.CardBill
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CardActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView

    lateinit var dataBase : AppDatabase
    lateinit var addCard : CardDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        this.dataBase = AppDatabase.getInstance(this)
        this.addCard = dataBase.cardDao()

        setButtonFinishActivity()
        setButtonNewCard()
    }

    override fun onResume() {
        super.onResume()
        val cardList : MutableList<CardBill> = mutableListOf()
        val cardAdapter = CardAdapter(this, cardList)

        recyclerView = findViewById(R.id.card_recycleview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = cardAdapter

        CoroutineScope(Dispatchers.Main).launch {
            val cards = addCard.getAllCard()

            for (card in cards){
                cardList.add(CardBill(card.id, card.nickname, card.dueDate))
            }
            cardAdapter.notifyDataSetChanged()
        }

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }

    private fun setButtonFinishActivity(){
        findViewById<ImageButton>(R.id.back_button_card).setOnClickListener {
            finish()
        }
    }

    private fun setButtonNewCard(){

        fun saveCard(nickname : String, dueDate : String){
            CoroutineScope(Dispatchers.Main).launch {
                addCard.insertCard(CardModel(nickname, dueDate))
                refreshCardList()
            }
        }

        findViewById<ImageButton>(R.id.add_new_card).setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_new_card_form, null)
            val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)

            dialogView.findViewById<Button>(R.id.new_card_save_button).setOnClickListener {
                val cardNickname = dialogView.findViewById<EditText>(R.id.card_name).text.toString()
                val cardDueDate = dialogView.findViewById<EditText>(R.id.card_dueDate).text.toString()

                if (cardNickname != "" && cardDueDate  != ""){
                    saveCard(cardNickname, cardDueDate)
                    dialog.dismiss()
                }else{
                    Toast.makeText(this, "Aconteceu um erro.\nFavor, verificar as informações.",Toast.LENGTH_SHORT).show()
                }
            }

            dialog.setContentView(dialogView)
            dialog.show()
        }
    }

    private fun refreshCardList(){
        val cardList: MutableList<CardBill> = mutableListOf()

        CoroutineScope(Dispatchers.Main).launch {
            val cards = addCard.getAllCard()
            for (card in cards) {
                cardList.add(CardBill(card.id, card.nickname, card.dueDate))
            }
            val cardAdapter = recyclerView.adapter as CardAdapter
            cardAdapter.updateCardList(cardList)
        }
    }
}