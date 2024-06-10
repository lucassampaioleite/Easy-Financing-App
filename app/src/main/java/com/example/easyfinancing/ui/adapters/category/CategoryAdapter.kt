package com.example.easyfinancing.ui.adapters.category

import android.content.Context
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.category.Category

class CategoryAdapter(val context: Context, val list : MutableList<Category>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryVieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryVieHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_category_item, parent, false)
        return CategoryVieHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryVieHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class CategoryVieHolder(itemView: View) : ViewHolder(itemView){
        fun bind(category: Category){

            itemView.findViewById<TextView>(R.id.category_name).setText(category.name)
            itemView.findViewById<ImageView>(R.id.category_icon).setImageResource(category.icon)
            itemView.findViewById<ImageView>(R.id.category_icon).colorFilter = PorterDuffColorFilter(ContextCompat.getColor(itemView.context, R.color.blue_light), PorterDuff.Mode.SRC_ATOP)
            itemView.findViewById<TextView>(R.id.category_total_value)

            if(category.type != 0){
                itemView.findViewById<ImageView>(R.id.category_card_icon).background = ContextCompat.getDrawable(itemView.context, R.drawable.round_background_red)
                itemView.findViewById<ImageView>(R.id.category_card_icon).rotation = 180.0f
            }
        }
    }
}