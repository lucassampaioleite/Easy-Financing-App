package com.example.easyfinancing.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
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

class ExtractActivity : AppCompatActivity() {

    lateinit var recyclerView_Extract : RecyclerView
    val viewModel: NewMovViewModel by viewModels()
    val movimentacoes : MutableList<Any> = mutableListOf()

    lateinit var dataBase : AppDatabase
    lateinit var addMovimentation : MovimetationDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_extract)

        this.dataBase = AppDatabase.getInstance(this)
        this.addMovimentation = dataBase.movimentationDao()


        recyclerView_Extract = findViewById(R.id.recyclerView_extract)
        /*AREA DESTINADA A TESTES DA ACTIVITY*/

        CoroutineScope(Dispatchers.Main).launch {
            for (mov in addMovimentation.getMovs().toTypedArray()){
                setNovaMovimentacao(arrayOf(mov.date, mov.tipo, mov.descricao1, mov.descricao2, mov.valor, mov.id.toString()), movimentacoes)
            }
            recyclerView(movimentacoes)
        }
        /*FIM AREA DESTINADA A TESTES DA ACTIVITY*/

        /* BOTAO NOVA MOVIMENTACAO -> CHAMA A ACTIVITY DE INCLUSADO DE NOVA MOVIMENTACAO */
        val addNewMovBtn : ImageButton = findViewById(R.id.addMov)
        addNewMovBtn.setOnClickListener{
            Toast.makeText(this, "callNewMovActivity()", Toast.LENGTH_SHORT).show()
        }

        /* BOTAO VOLTAR -> VOLTA PARA ACTIVITY ANTERIOR */
        findViewById<ImageView>(R.id.back_button).setOnClickListener{finish()}

        findViewById<ImageButton>(R.id.addMov).setOnClickListener{
            val NEW_MOV = Intent(this, NewMovActivity::class.java)
            startActivity(NEW_MOV)
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
        val combinedAdapter = AdapterCombinedEx(this, list)
        recyclerView_Extract.adapter = combinedAdapter
    }
}