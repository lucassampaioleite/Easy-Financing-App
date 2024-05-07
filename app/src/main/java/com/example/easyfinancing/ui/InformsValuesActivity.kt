package com.example.easyfinancing.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easyfinancing.R
import com.example.easyfinancing.databinding.ActivityInformsValuesBinding

class InformsValuesActivity : AppCompatActivity() {

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

        binding.linearImageText.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(applicationContext, "FUNCIONOU!", Toast.LENGTH_SHORT).show()
            }
        })

        binding.textSaidas.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                Toast.makeText(applicationContext, "FUNCIONOU!", Toast.LENGTH_SHORT).show()
            }
        })

        binding.imageButtonBtNext.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(applicationContext, SelectDateActivity::class.java))
            }
        })
    }
}