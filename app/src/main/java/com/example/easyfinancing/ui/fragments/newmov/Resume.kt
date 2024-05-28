package com.example.easyfinancing.ui.fragments.newmov

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.viewmodels.NewMovViewModel

class Resume : Fragment() {
    lateinit var viewModel: NewMovViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_new_mov_resume, container, false)
        viewModel = ViewModelProvider(requireActivity()).get(NewMovViewModel::class.java)

        view.findViewById<TextView>(R.id.mov_date_resume).setText(viewModel.getDateFormmated())
        view.findViewById<ImageView>(R.id.mov_type_resume).setImageResource(getType(viewModel.movType))
        view.findViewById<TextView>(R.id.mov_value_resume).setText(viewModel.movValue)
        view.findViewById<TextView>(R.id.mov_desc_resume).setText(viewModel.movDesc)

        return view
    }

    fun getType(type : Boolean): Int {
        if (type){
            return R.drawable.arrow_drop_up
        }
        return R.drawable.arrow_drop_down
    }
}