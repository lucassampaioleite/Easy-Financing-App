package com.example.easyfinancing.ui

import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.easyfinancing.R
import com.example.easyfinancing.databinding.ActivityAccessBudgetsBinding

class AccessBudgetsActivity : AppCompatActivity() {

    private lateinit var bindingAccessBudgets: ActivityAccessBudgetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        bindingAccessBudgets = ActivityAccessBudgetsBinding.inflate(layoutInflater)

        setContentView(bindingAccessBudgets.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindingAccessBudgets.imageButtonCloseAccessBudgets.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) {
                finish()
            }
        })

    }
}