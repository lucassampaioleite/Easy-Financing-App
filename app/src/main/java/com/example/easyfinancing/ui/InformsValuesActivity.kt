package com.example.easyfinancing.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easyfinancing.R
import com.example.easyfinancing.databinding.ActivityInformsValuesBinding

class InformsValuesActivity : AppCompatActivity(), OnClickListener {

    private lateinit var binding: ActivityInformsValuesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityInformsValuesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.linearImageText.setOnClickListener(this)
        binding.textSaidas.setOnClickListener(this)
        binding.textEntradas.setOnClickListener(this)
        binding.imageButtonBtNext.setOnClickListener(this)
        binding.imageButtonArrowBack.setOnClickListener(this)
        binding.imageButtonBtClose.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.text_saidas -> handlerFilter(view.id)
            R.id.text_entradas -> handlerFilter(view.id)
            R.id.linear_image_text -> Toast.makeText(applicationContext,
                "FUNCIONOU!", Toast.LENGTH_SHORT).show()
            R.id.imageButton_arrow_back -> finish()
            R.id.imageButton_btNext -> startActivity(Intent(applicationContext,
                SelectDateActivity::class.java))
            R.id.imageButton_btClose -> finish()
        }
    }

    private fun handlerFilter(id: Int) {
        when(id) {
            R.id.text_saidas -> {
                binding.textSaidas.setBackgroundColor(ContextCompat.getColor(this, R.color.green_blue_dark))
                binding.textEntradas.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_light))
            }
            else -> {
                binding.textEntradas.setBackgroundColor(ContextCompat.getColor(this, R.color.green_blue_dark))
                binding.textSaidas.setBackgroundColor(ContextCompat.getColor(this, R.color.blue_light))
            }
        }
    }


}