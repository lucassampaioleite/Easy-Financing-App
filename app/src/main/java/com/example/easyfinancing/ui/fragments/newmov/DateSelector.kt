package com.example.easyfinancing.ui.fragments.newmov

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.viewmodels.NewMovViewModel
import java.time.LocalDate
import java.time.ZoneId
import java.util.Calendar

class DateSelector : Fragment() {
    val viewModel: NewMovViewModel by activityViewModels()
    lateinit var date : CalendarView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_date_selector, container, false)

        date = view.findViewById(R.id.calendario)

        date.date = viewModel.movDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()

        date.setOnDateChangeListener { _, year, month, dayOfMonth ->
            viewModel.movDate = LocalDate.of(year, month+1, dayOfMonth)
        }

        return view
    }
}