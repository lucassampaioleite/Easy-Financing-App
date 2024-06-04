package com.example.easyfinancing.ui.adapters.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.category.Category

class CategoryAdapter(val context: Context, val list : MutableList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryVieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVieHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.activity_category_item, parent, false)
        return CategoryVieHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryVieHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class CategoryVieHolder(itemView: View) : ViewHolder(itemView){
        fun bind(category: Category){

        }
    }
}