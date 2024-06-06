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
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class ExtractActivity : AppCompatActivity() {

    lateinit var recyclerView_Extract : RecyclerView
    val viewModel: NewMovViewModel by viewModels()

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
                    arrayOf(
                        mov.id.toString(),
                        getDateExpanded(mov.data),
                        mov.tipo,
                        mov.descricao,
                        getCategoryName(mov.categoriaId),
                        if (mov.cartaoId != 0) getInstalmentValue(mov.valor, mov.cartaoParcelas) else mov.valor,
                        mov.cartaoId.toString(),
                        mov.recorrencia.toString(),
                        mov.orcamentoId.toString()
                    )
                    , movimentacoes)
            }
            recyclerView(movimentacoes)
        }
    }

    fun recyclerView(list : MutableList<Any>){
        recyclerView_Extract.layoutManager = LinearLayoutManager(this)
        recyclerView_Extract.setHasFixedSize(true)
        val combinedAdapter = AdapterCombinedEx(this, list)
        recyclerView_Extract.adapter = combinedAdapter
    }

    fun setNovaMovimentacao(novaMov : Array<String>, listMov : MutableList<Any>){
        fun icon_type(tipo : String) : Int{
            if(tipo == "E"){
                return R.drawable.arrow_drop_up
            }
            return R.drawable.arrow_drop_down
        }

        if(listMov.isEmpty()){
            listMov.add(MovDate(novaMov[1]))
            listMov.add(Movimentation(
                novaMov[0].toInt(),
                novaMov[1],
                icon_type(novaMov[2]),
                novaMov[3],
                novaMov[4],
                novaMov[5],
                novaMov[6] != "0",
                novaMov[7] != "0",
                novaMov[8] != "0"
            ))
        }else{

            val lastItem = listMov.last()

            if(lastItem is Movimentation && lastItem.date == novaMov[1]){
                listMov.add(Movimentation(
                    novaMov[0].toInt(),
                    novaMov[1],
                    icon_type(novaMov[2]),
                    novaMov[3],
                    novaMov[4],
                    novaMov[5],
                    novaMov[6] != "0",
                    novaMov[7] != "0",
                    novaMov[8] != "0"
                ))
            }
            else{
                listMov.add(MovDate(novaMov[1]))
                listMov.add(Movimentation(
                    novaMov[0].toInt(),
                    novaMov[1],
                    icon_type(novaMov[2]),
                    novaMov[3],
                    novaMov[4],
                    novaMov[5],
                    novaMov[6] != "0",
                    novaMov[7] != "0",
                    novaMov[8] != "0"
                ))
            }
        }
    }

    fun getInstalmentValue(valor : String, parcelas : Int) : String{

        val valorFloat = valor.replace("R$ ", "").replace(".", "").replace(",", ".").toFloat()
        val valorParcela = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(valorFloat / parcelas)

        return valorParcela
    }

    fun getCategoryName(categoryId : Int) : String{
        //Essa funçao faz um consulta na tabela de categoria no Bando de Dados
        //E retorna o nome da categoria com base no Id

        val categoryName : String = "Categoria"

        return categoryName
    }

    fun getDateExpanded(date: String) : String{
        val formmater = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("pt", "BR"))
        var dateFormatted = formmater.format(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).parse(date))
        dateFormatted = dateFormatted.replaceFirstChar{it.uppercase()}
        dateFormatted = dateFormatted.replace("-feira", "")

        return dateFormatted
    }
}