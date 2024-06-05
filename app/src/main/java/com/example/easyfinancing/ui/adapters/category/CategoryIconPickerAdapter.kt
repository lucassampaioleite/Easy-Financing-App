package com.example.easyfinancing.ui.adapters.category

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.icons.Icons

class CategoryIconPickerAdapter (val context: Context, val list: MutableList<Icons>):
    RecyclerView.Adapter<CategoryIconPickerAdapter.IconViewHolder>() {
        private val viewHolders = mutableListOf<IconViewHolder>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.icon_category_picker, parent, false)
        viewHolders.add(IconViewHolder(view))
        return IconViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class IconViewHolder(itemView : View) : ViewHolder(itemView){
        var selected = false

        init {
            itemView.findViewById<ImageButton>(R.id.category_icon_selection).setOnClickListener {
                if (!selected){
                    for (i in 0 until  viewHolders.size){
                        viewHolders[i].unselect()
                        viewHolders[i].selected = false
                    }
                    select()
                    selected = true
                }else{
                    unselect()
                    selected = false
                }
            }
        }

        fun bind(icons: Icons) {
            itemView.findViewById<ImageButton>(R.id.category_icon_selection).setImageResource(icons.icon)
            itemView.findViewById<ImageButton>(R.id.category_icon_selection).setColorFilter(ContextCompat.getColor(itemView.context, R.color.blue_light))
        }

        private fun select(){
            itemView.findViewById<ImageButton>(R.id.category_icon_selection).background = ContextCompat.getDrawable(itemView.context, R.drawable.round_background_blue)
            itemView.findViewById<ImageButton>(R.id.category_icon_selection).setColorFilter(ContextCompat.getColor(itemView.context, R.color.white))
        }
        private fun unselect(){
            itemView.findViewById<ImageButton>(R.id.category_icon_selection).background = ContextCompat.getDrawable(itemView.context, R.drawable.transparent)
            itemView.findViewById<ImageButton>(R.id.category_icon_selection).setColorFilter(ContextCompat.getColor(itemView.context, R.color.blue_light))
        }
    }
}