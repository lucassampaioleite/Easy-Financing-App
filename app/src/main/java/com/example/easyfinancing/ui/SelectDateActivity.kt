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

class SelectDateActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivitySelectDateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivitySelectDateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btVoltar.setOnClickListener(this)
        binding.btCancelar.setOnClickListener(this)
        binding.btAvancar.setOnClickListener(this)
        binding.calendario.setOnDateChangeListener(object : OnDateChangeListener {
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

    override fun onClick(view: View) {
        when (view.id) {
            R.id.bt_voltar -> finish()
            R.id.bt_cancelar -> {
                startActivity(Intent(applicationContext, ExtratoActivity::class.java))
                finishAffinity()
            }
            R.id.bt_avancar -> startActivity(Intent(applicationContext, ResumeActivity::class.java))
        }
    }

}