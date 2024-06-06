package com.example.easyfinancing.ui.adapters.dialogs

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.category.Category

class DialogCategoryAdapter(val context: Context, val list: MutableList<Category>) :
    RecyclerView.Adapter<DialogCategoryAdapter.DialogCategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DialogCategoryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_category_new_mov, parent, false)
        return DialogCategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: DialogCategoryViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    inner class DialogCategoryViewHolder(itemView: View) : ViewHolder(itemView){
        fun bind(dialogCategory : Category){
            itemView.findViewById<ImageView>(R.id.dialog_recycle_category_icon).setImageResource(dialogCategory.icon)
            itemView.findViewById<TextView>(R.id.dialog_recycle_category_name).setText(dialogCategory.name)
        }
    }
}