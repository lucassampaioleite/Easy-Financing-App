package com.example.easyfinancing.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easyfinancing.R
import com.example.easyfinancing.databinding.ActivityResumeBinding

class ResumeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResumeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityResumeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.imageArrowBack.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(applicationContext, SelectDateActivity::class.java))
            }
        })

        binding.imageButtonRight.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                startActivity(Intent(applicationContext, ExtratoActivity::class.java))
            }
        })
    }
}