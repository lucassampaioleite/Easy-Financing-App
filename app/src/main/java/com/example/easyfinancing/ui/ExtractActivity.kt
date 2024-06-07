package com.example.easyfinancing.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.MovimetationDao
import com.example.easyfinancing.ui.adapters.extract.AdapterCombinedEx
import com.example.easyfinancing.ui.models.extract.MovDate
import com.example.easyfinancing.ui.models.extract.Movimentation
import com.example.easyfinancing.ui.viewmodels.NewMovViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

class ExtractActivity : AppCompatActivity() {

    lateinit var recyclerView_Extract : RecyclerView

    lateinit var dataBase : AppDatabase
    lateinit var addMovimentation : MovimetationDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)

        this.dataBase = AppDatabase.getInstance(this)
        this.addMovimentation = dataBase.movimentationDao()

        /* BOTAO VOLTAR -> VOLTA PARA ACTIVITY ANTERIOR */
        findViewById<ImageView>(R.id.back_button).setOnClickListener{finish()}

        /* BOTAO NOVA MOVIMENTACAO -> CHAMA A ACTIVITY DE INCLUSADO DE NOVA MOVIMENTACAO */
        findViewById<ImageButton>(R.id.addMov).setOnClickListener{
            val NEW_MOV = Intent(this, NewMovActivity::class.java)
            startActivity(NEW_MOV)
        }
    }

    override fun onResume() {
        super.onResume()

        val movimentacoes : MutableList<Any> = mutableListOf()

        recyclerView_Extract = findViewById(R.id.recyclerView_extract)

        /*CONSULTA BANCO DE DADOS TABELA MOVIMENTAÇÕES*/
        CoroutineScope(Dispatchers.Main).launch {
            for (mov in addMovimentation.getMovs().toTypedArray()){
                setNovaMovimentacao(
                    Movimentation(mov.id, LocalDate.parse(mov.data), mov.tipo, mov.descricao, mov.categoriaId, mov.valor, mov.cartaoId, mov.cartaoParcelas, mov.recorrencia, mov.orcamentoId),
                    movimentacoes
                )
            }
            recyclerView(movimentacoes)
        }
    }

    fun recyclerView(list : MutableList<Any>){
        recyclerView_Extract.layoutManager = LinearLayoutManager(this)
        recyclerView_Extract.setHasFixedSize(true)

        val combinedAdapter = AdapterCombinedEx(this, list){

            val intent = Intent(this, NewMovActivity::class.java).apply {
                val mov = Movimentation(it.id, it.date, it.type, it.mainDescription, it.categoryId, it.movAmount, it.cardId, it.cardInstalments, it.recurenceId, it.budgetId)
                putExtra("mov", mov)
            }

            startActivity(intent)
        }
        recyclerView_Extract.adapter = combinedAdapter
    }

    fun setNovaMovimentacao(novaMov : Movimentation, listMov : MutableList<Any>){

        if(listMov.isEmpty()){
            listMov.add(MovDate(novaMov.date.toString()))
            listMov.add(novaMov)
        }else{

            val lastItem = listMov.last()

            if(lastItem is Movimentation && lastItem.date == novaMov.date){
                listMov.add(novaMov)
            }
            else{
                listMov.add(MovDate(novaMov.date.toString()))
                listMov.add(novaMov)
            }
        }
    }
}