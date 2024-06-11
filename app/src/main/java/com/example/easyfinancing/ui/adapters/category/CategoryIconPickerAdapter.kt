package com.example.easyfinancing.ui.adapters.category

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.models.icons.Icons

class CategoryIconPickerAdapter (val context: Context, val list: MutableList<Icons>, val iconSelected : (Icons) -> Unit):
    RecyclerView.Adapter<CategoryIconPickerAdapter.IconViewHolder>() {
        private val viewHolders = mutableListOf<IconViewHolder>()

        var lastItemSelected = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recycle_icon_category_picker, parent, false)
        viewHolders.add(IconViewHolder(view))
        return IconViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        val icon = list[position]
        holder.bind(list[position])

        holder.iconView.setOnClickListener {

            if (lastItemSelected != RecyclerView.NO_POSITION){
                viewHolders[lastItemSelected].iconView.background = ContextCompat.getDrawable(viewHolders[lastItemSelected].iconView.context, R.drawable.transparent)
                viewHolders[lastItemSelected].iconView.setColorFilter(ContextCompat.getColor(viewHolders[lastItemSelected].iconView.context, R.color.blue_light))
            }

            holder.iconView.background = ContextCompat.getDrawable(holder.iconView.context, R.drawable.round_background_blue)
            holder.iconView.setColorFilter(ContextCompat.getColor(holder.iconView.context, R.color.white))

            lastItemSelected = position

            iconSelected(icon)
        }
    }

    inner class IconViewHolder(itemView : View) : ViewHolder(itemView) {

        val iconView = itemView.findViewById<ImageButton>(R.id.category_icon_selection)

        fun bind(icon: Icons) {
            iconView.setImageResource(icon.icon)
        }
    }
}