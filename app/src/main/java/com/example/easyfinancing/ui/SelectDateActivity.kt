package com.example.easyfinancing.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easyfinancing.R
import com.example.easyfinancing.databinding.ActivitySelectDateBinding

class SelectDateActivity : AppCompatActivity() {

    private lateinit var bindingDate: ActivitySelectDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        bindingDate = ActivitySelectDateBinding.inflate(layoutInflater)
        setContentView(bindingDate.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindingDate.btAvancar.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(applicationContext, ResumeActivity::class.java))
            }
        })

        bindingDate.calendario.setOnDateChangeListener(object : OnDateChangeListener {
            override fun onSelectedDayChange(
                view: CalendarView,
                year: Int,
                month: Int,
                dayOfMonth: Int
            ) {
                Toast.makeText(applicationContext, "$year-${month+1}-$dayOfMonth", Toast.LENGTH_SHORT).show()
            }

        })


    }
}