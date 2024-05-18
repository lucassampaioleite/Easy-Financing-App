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
import com.example.easyfinancing.databinding.ActivityResumeBinding

class ResumeActivity : AppCompatActivity(), OnClickListener {

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

        binding.imageArrowBack.setOnClickListener(this)
        binding.imageButtonClose.setOnClickListener(this)
        binding.imageButtonSave.setOnClickListener(this)

    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.image_arrow_back -> finish()
            R.id.image_button_close -> {
                startActivity(Intent(applicationContext, ExtratoActivity::class.java))
                finishAffinity()
            }
            R.id.imageButton_save -> Toast.makeText(this, "SALVO!", Toast.LENGTH_SHORT).show()
        }
    }
}