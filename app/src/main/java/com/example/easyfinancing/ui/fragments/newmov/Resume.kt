package com.example.easyfinancing.ui.fragments.newmov

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.viewmodels.NewMovViewModel

class Resume : Fragment() {
    val viewModel: NewMovViewModel by activityViewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_mov_resume, container, false)
        view.findViewById<TextView>(R.id.mov_date_resume).setText(viewModel.getDateFormmated())
        view.findViewById<ImageView>(R.id.mov_type_resume).setImageResource(getType(viewModel.movType))
        view.findViewById<TextView>(R.id.mov_value_resume).setText(viewModel.movValue)
        view.findViewById<TextView>(R.id.mov_desc_resume).setText(viewModel.movDesc)

        var recurenceIndex = 0
        view.findViewById<ImageButton>(R.id.recurence_selection_inner).setOnClickListener {
            recurenceIndex++
            when(recurenceIndex){
                1 -> {
                    view.findViewById<ImageButton>(R.id.recurence_selection_inner).background = ContextCompat.getDrawable(requireContext(), R.drawable.round_background_blue)
                    view.findViewById<ImageButton>(R.id.recurence_selection_inner).setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_dark))
                    view.findViewById<TextView>(R.id.text_recurence_selection).setText("Diário")
                }
                2 -> view.findViewById<TextView>(R.id.text_recurence_selection).setText("Semanal")
                3 -> view.findViewById<TextView>(R.id.text_recurence_selection).setText("Mensal")
                else -> {
                    view.findViewById<ImageButton>(R.id.recurence_selection_inner).background = ContextCompat.getDrawable(requireContext(), R.drawable.round_dark_blue)
                    view.findViewById<ImageButton>(R.id.recurence_selection_inner).setColorFilter(ContextCompat.getColor(requireContext(), R.color.blue_light))
                    view.findViewById<TextView>(R.id.text_recurence_selection).setText("Recorrência")
                    recurenceIndex = 0
                }
            }
        }

        return view
    }

    fun getType(type : Boolean): Int {
        if (type){
            return R.drawable.arrow_drop_up
        }
        return R.drawable.arrow_drop_down
    }
}