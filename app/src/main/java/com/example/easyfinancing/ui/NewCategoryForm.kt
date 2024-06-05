package com.example.easyfinancing.ui

import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.adapters.category.CategoryIconPickerAdapter
import com.example.easyfinancing.ui.models.icons.Icons

class NewCategoryForm : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    val icons : MutableList<Icons> = mutableListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_new_item_form)

        setCloseFormButton()

        recyclerView = findViewById(R.id.new_category_icon)
        recyclerView.layoutManager = GridLayoutManager(this, 5)
        recyclerView.setHasFixedSize(true)
        iconsLib()
        val adpater = CategoryIconPickerAdapter(this, icons)
        recyclerView.adapter = adpater
    }

    private fun setCloseFormButton(){
        findViewById<ImageButton>(R.id.arrow_back_category_new_item).setOnClickListener {
            finish()
        }
    }

    private fun iconsLib(){
        icons.add(Icons(R.drawable.cat_ic_key))
        icons.add(Icons(R.drawable.cat_ic_book))
        icons.add(Icons(R.drawable.cat_ic_account_balance))
        icons.add(Icons(R.drawable.cat_ic_nest_remote))
        icons.add(Icons(R.drawable.cat_ic_beach))
        icons.add(Icons(R.drawable.cat_ic_celebration))
        icons.add(Icons(R.drawable.cat_ic_checkroom))
        icons.add(Icons(R.drawable.cat_ic_build))
        icons.add(Icons(R.drawable.cat_ic_clear_day))
        icons.add(Icons(R.drawable.cat_ic_cleaning_services))
        icons.add(Icons(R.drawable.cat_ic_cloud_done))
        icons.add(Icons(R.drawable.cat_ic_credit_card))
        icons.add(Icons(R.drawable.cat_ic_dentistry))
        icons.add(Icons(R.drawable.cat_ic_currency_bitcoin))
        icons.add(Icons(R.drawable.cat_ic_electric))
        icons.add(Icons(R.drawable.cat_ic_directions_car))
        icons.add(Icons(R.drawable.cat_ic_exercise))
        icons.add(Icons(R.drawable.cat_ic_favorite))
        icons.add(Icons(R.drawable.cat_ic_family_history))
        icons.add(Icons(R.drawable.cat_ic_local_bar))
        icons.add(Icons(R.drawable.cat_ic_flight))
        icons.add(Icons(R.drawable.cat_ic_fitness_center))
        icons.add(Icons(R.drawable.cat_ic_house))
        icons.add(Icons(R.drawable.cat_ic_local_dining))
        icons.add(Icons(R.drawable.cat_ic_local_parking))
        icons.add(Icons(R.drawable.cat_ic_local_shipping))
        icons.add(Icons(R.drawable.cat_ic_luggage))
        icons.add(Icons(R.drawable.cat_ic_medical_services))
        icons.add(Icons(R.drawable.cat_ic_mode_cool))
        icons.add(Icons(R.drawable.cat_ic_monitor))
        icons.add(Icons(R.drawable.cat_ic_local_gas))
        icons.add(Icons(R.drawable.cat_ic_pill))
        icons.add(Icons(R.drawable.cat_ic_pregnant_woman))
        icons.add(Icons(R.drawable.cat_ic_water_drop))
        icons.add(Icons(R.drawable.cat_ic_shopping))
        icons.add(Icons(R.drawable.cat_ic_lock))
        icons.add(Icons(R.drawable.cat_ic_sports_soccer))
        icons.add(Icons(R.drawable.cat_ic_subway))
        icons.add(Icons(R.drawable.cat_ic_wallet))
        icons.add(Icons(R.drawable.cat_ic_shopping_bag))
    }
}