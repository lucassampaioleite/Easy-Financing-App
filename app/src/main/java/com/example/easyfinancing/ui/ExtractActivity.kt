package com.example.easyfinancing.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.ui.adapters.AdapterCombined
import com.example.easyfinancing.ui.models.MovDate
import com.example.easyfinancing.ui.models.Movimentation

class ExtractActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)

        val recyclerView_Extract = findViewById<RecyclerView>(R.id.recyclerView_extract)
        recyclerView_Extract.layoutManager = LinearLayoutManager(this)
        recyclerView_Extract.setHasFixedSize(true)

        val itensList : MutableList<Any> = mutableListOf()

        /*AREA DESTINADA A TESTES DA ACTIVITY*/
        val date = MovDate("Sábado, 18 mai 2024")

        itensList.add(date)

        for(i in 1..5){
            val movimentation = Movimentation(
                R.drawable.arrow_drop_up,
                "Descricao Principal",
                "Descricao Auxiliar",
                "R$ 0,01"
            )

            itensList.add(movimentation)
        }

        val newdate = MovDate("Domingo, 19 mai 2024")

        itensList.add(newdate)

        for(i in 1..10){
            val movimentation = Movimentation(
                R.drawable.arrow_drop_up,
                "Descricao Principal",
                "Descricao Auxiliar",
                "R$ 0,01"
            )

            itensList.add(movimentation)
        }
        /*FIM AREA DESTINADA A TESTES DA ACTIVITY*/
        val combinedAdapter = AdapterCombined(this, itensList)
        recyclerView_Extract.adapter = combinedAdapter

        val addNewMovBtn : ImageButton = findViewById(R.id.addMov)
        addNewMovBtn.setOnClickListener{
            Toast.makeText(this, "callNewMovActivity()", Toast.LENGTH_SHORT).show()
        }
    }
}