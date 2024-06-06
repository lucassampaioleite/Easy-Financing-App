package com.example.easyfinancing.ui

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.adapters.card.CardAdapter
import com.example.easyfinancing.ui.models.card.CardBill
import com.google.android.material.bottomsheet.BottomSheetDialog

class CardActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    val cardList : MutableList<CardBill> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_card)

        setButtonFinishActivity()
        setButtonNewCard()

        recyclerView = findViewById(R.id.card_recycleview)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.setHasFixedSize(true)
        cardList.add(CardBill(1,"Inter", "07"))
        cardList.add(CardBill(2,"Bradesco", "15"))
        cardList.add(CardBill(3,"Itaú", "10"))
        cardList.add(CardBill(4,"Nubank", "25"))
        val cardAdapter = CardAdapter(this, cardList)
        recyclerView.adapter = cardAdapter

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }

    private fun setButtonFinishActivity(){
        findViewById<ImageButton>(R.id.back_button_card).setOnClickListener {
            finish()
        }
    }

    private fun setButtonNewCard(){
        findViewById<ImageButton>(R.id.add_new_card).setOnClickListener {

            val dialogView = layoutInflater.inflate(R.layout.dialog_new_card_form, null)

            val dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
            dialog.setContentView(dialogView)
            dialog.show()
        }
    }
}