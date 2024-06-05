package com.example.easyfinancing.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.adapters.category.CategoryAdapter
import com.example.easyfinancing.ui.models.category.Category

class CategoriesActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    val categories : MutableList<Category> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        setButtonCallBack()
        setButtonNewCategory()

        recyclerView = findViewById(R.id.categories_recycleview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
        categories.add(Category(R.drawable.cat_ic_credit_card, "Cartão de Credito", "R$ 0,00"))
        val categoryAdapter = CategoryAdapter(this, categories)
        recyclerView.adapter = categoryAdapter
    }

    private fun setButtonCallBack(){
        findViewById<ImageButton>(R.id.arrow_back_category).setOnClickListener {
            finish()
        }
    }

    private fun setButtonNewCategory() {
        findViewById<ImageButton>(R.id.add_new_category).setOnClickListener {
            val NEW_CATEGORY = Intent(this, NewCategoryForm::class.java)
            startActivity(NEW_CATEGORY)
        }
    }
}