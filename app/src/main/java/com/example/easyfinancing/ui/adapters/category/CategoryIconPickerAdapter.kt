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
        var selectedItemPosition = RecyclerView.NO_POSITION
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.icon_category_picker, parent, false)
        viewHolders.add(IconViewHolder(view))
        return IconViewHolder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: IconViewHolder, position: Int) {
        holder.bind(list[position], position == selectedItemPosition)
    }

    inner class IconViewHolder(itemView : View) : ViewHolder(itemView) {

        init {

            itemView.findViewById<ImageButton>(R.id.category_icon_selection).setOnClickListener {

                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {

                    selectedItemPosition = position

                    notifyDataSetChanged()

                }

            }

        }

        fun bind(icon: Icons, isSelected: Boolean) {

            val iconButton = itemView.findViewById<ImageButton>(R.id.category_icon_selection)

            iconButton.setImageResource(icon.icon)

            if (isSelected) {

                iconButton.background =
                    ContextCompat.getDrawable(itemView.context, R.drawable.round_background_blue)

                iconButton.setColorFilter(ContextCompat.getColor(itemView.context, R.color.white))

            } else {

                iconButton.background =
                    ContextCompat.getDrawable(itemView.context, R.drawable.transparent)

                iconButton.setColorFilter(
                    ContextCompat.getColor(
                        itemView.context,
                        R.color.blue_light
                    )
                )

            }

        }
    }
}