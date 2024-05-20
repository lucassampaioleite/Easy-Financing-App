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
import com.example.easyfinancing.databinding.ActivityBudgetsBinding

private const val WIDTH_MAX: Double = 550.00
private const val PORCENTAGEM_TOTAL_WIDTH: Int = 100

class BudgetsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBudgetsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityBudgetsBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.viewBarraGraphicMoving.layoutParams.width =
            calculateGraphicWidth(calculaPorcentagemDeValorUtilizadoSobreValorReservado()).toInt()
        "${calculaPorcentagemDeValorUtilizadoSobreValorReservado()}%".also { binding.textPorcento.text = it }


        binding.fabAddOrcamento.setOnClickListener(object : OnClickListener {
            override fun onClick(v: View?) =
                startActivity(Intent(applicationContext, AccessBudgetsActivity::class.java))
//                binding.frameAccessBudgets.visibility = VISIBLE // Faz o frame que registra orçamento ficar visível a tela
//                binding.cadViewReservaEmergencia.visibility = View.GONE // Faz o frame que registra orçamento ficar sair da tela
//                binding.fabAddOrcamento.visibility = View.GONE // Faz o frame que registra orçamento ficar faz sair da tela
        })

    }

    private fun calculateGraphicWidth(porcentageDeUtilizadoSobreReservado: Double): Double {
        val porcentagemUtilizadaParaTamanhoViewBarraMoving =
            (porcentageDeUtilizadoSobreReservado * WIDTH_MAX) / PORCENTAGEM_TOTAL_WIDTH

        return porcentagemUtilizadaParaTamanhoViewBarraMoving
    }

    private fun calculaPorcentagemDeValorUtilizadoSobreValorReservado(): Double { // receberá os parâmetros do banco
        val valueReservado = 5000.00 // este valor virá do banco de dados
        val valueUtilizado = 1738.00 // este valor virá do banco de dados

        return (valueUtilizado * PORCENTAGEM_TOTAL_WIDTH) / valueReservado
    }

//
//    private fun openAccessBudgets(fragment: Fragment) {
//        val fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(R.id.frame_access_budgets, fragment)
//        fragmentTransaction.commit()
//    }

}