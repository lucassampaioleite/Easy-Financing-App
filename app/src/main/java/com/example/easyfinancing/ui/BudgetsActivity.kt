package com.example.easyfinancing.ui

import android.os.Bundle
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
            calculateGraphic(calculaPorcentagemDeValorUtilizadoSobreValorReservado()).toInt()
        "${calculaPorcentagemDeValorUtilizadoSobreValorReservado()}%".also { binding.textPorcento.text = it }

    }

    private fun calculateGraphic(porcentageDeUtilizadoSobreReservado: Double): Double {
        val porcentagemUtilizadaParaTamanhoViewBarraMoving =
            (porcentageDeUtilizadoSobreReservado * WIDTH_MAX) / PORCENTAGEM_TOTAL_WIDTH

        return porcentagemUtilizadaParaTamanhoViewBarraMoving
    }

    private fun calculaPorcentagemDeValorUtilizadoSobreValorReservado(): Double { // receberá os parâmetros do banco
        val valueReservado = 5000.00 // este valor virá do banco de dados
        val valueUtilizado = 1738.00 // este valor virá do banco de dados

        return (valueUtilizado * PORCENTAGEM_TOTAL_WIDTH) / valueReservado
    }

}