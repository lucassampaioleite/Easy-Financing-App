package com.example.easyfinancing.ui

import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R

import com.example.easyfinancing.ui.adapters.extract.AdapterCombined
import com.example.easyfinancing.ui.models.extract.MovDate
import com.example.easyfinancing.ui.models.extract.Movimentation

class ExtractActivity : AppCompatActivity() {

    lateinit var recyclerView_Extract : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)


        recyclerView_Extract = findViewById(R.id.recyclerView_extract)
        val movimentacoes : MutableList<Any> = mutableListOf()
        /*AREA DESTINADA A TESTES DA ACTIVITY*/
        for(i in 1..10){
            setNovaMovimentacao(arrayOf("Domingo, 19 mai 2024", "E", "Teste", "Teste", "R$ 0,00", "0"), movimentacoes)
        }
        for(i in 1..10){
            setNovaMovimentacao(arrayOf("Segunda, 20 mai 2024", "S", "Teste", "Teste", "R$ 0,00", "1"), movimentacoes)
        }
        /*FIM AREA DESTINADA A TESTES DA ACTIVITY*/
        recyclerView(movimentacoes)

        /* BOTAO NOVA MOVIMENTACAO -> CHAMA A ACTIVITY DE INCLUSADO DE NOVA MOVIMENTACAO */
        val addNewMovBtn : ImageButton = findViewById(R.id.addMov)
        addNewMovBtn.setOnClickListener{
            Toast.makeText(this, "callNewMovActivity()", Toast.LENGTH_SHORT).show()
        }

        /* BOTAO VOLTAR -> VOLTA PARA ACTIVITY ANTERIOR */
        val getBackBtn : ImageView = findViewById(R.id.back_button)
        getBackBtn.setOnClickListener{
            Toast.makeText(this, "callLastActivity()", Toast.LENGTH_SHORT).show()
        }
    }

    fun setNovaMovimentacao(novaMov : Array<String>, listMov : MutableList<Any>){
        fun icon_type(tipo : String) : Int{
            if(tipo == "E"){
                return R.drawable.arrow_drop_up
            }
            return R.drawable.arrow_drop_down
        }

        if(listMov.isEmpty()){
            listMov.add(MovDate(novaMov[0]))
            listMov.add(Movimentation(
                novaMov[5].toInt(),
                novaMov[0],
                icon_type(novaMov[1]),
                novaMov[2],
                novaMov[3],
                novaMov[4]
            ))
        }else{

            val lastItem = listMov.last()

            if(lastItem is Movimentation && lastItem.date == novaMov[0]){
                listMov.add(Movimentation(
                    novaMov[5].toInt(),
                    novaMov[0],
                    icon_type(novaMov[1]),
                    novaMov[2],
                    novaMov[3],
                    novaMov[4]
                ))
            }
            else{
                listMov.add(MovDate(novaMov[0]))
                listMov.add(Movimentation(
                    novaMov[5].toInt(),
                    novaMov[0],
                    icon_type(novaMov[1]),
                    novaMov[2],
                    novaMov[3],
                    novaMov[4]
                ))
            }
        }
    }

    fun recyclerView(list : MutableList<Any>){
        recyclerView_Extract.layoutManager = LinearLayoutManager(this)
        recyclerView_Extract.setHasFixedSize(true)
        val combinedAdapter = AdapterCombined(this, list)
        recyclerView_Extract.adapter = combinedAdapter
    }
}