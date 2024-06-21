package com.example.easyfinancing.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.CategoryDao
import com.example.easyfinancing.ui.adapters.category.CategoryAdapter
import com.example.easyfinancing.ui.models.category.Category
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoriesActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    lateinit var dataBase : AppDatabase
    lateinit var categoryDao : CategoryDao
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_categories)

        this.dataBase = AppDatabase.getInstance(this)
        this.categoryDao = dataBase.categoryDao()

        setButtonCallBack()
        setButtonNewCategory()
    }

    override fun onResume() {
        super.onResume()

        val categories : MutableList<Category> = mutableListOf()

        recyclerView = findViewById(R.id.categories_recycleview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.setHasFixedSize(true)
            CoroutineScope(Dispatchers.IO).launch {
                val DB_categories = categoryDao.getAllCategories()

                for (i in DB_categories.indices){
                    categories.add(
                        Category(DB_categories[i].id, DB_categories[i].icon, DB_categories[i].name, "R$ 0,00", 1)
                    )
                }
            }

        val categoryAdapter = CategoryAdapter(this, categories){entradas, saidas ->
            findViewById<TextView>(R.id.income_total_value).text = entradas.toString()
            findViewById<TextView>(R.id.outcome_total_value).text = saidas.toString()
        }

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