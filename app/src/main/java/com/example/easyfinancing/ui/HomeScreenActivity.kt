package com.example.easyfinancing.ui

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.MovimetationDao
import com.example.easyfinancing.ui.adapters.extract.AdapterCombinedEx
import com.example.easyfinancing.ui.adapters.home_screen.AdapaterCombinedHs
import com.example.easyfinancing.ui.models.extract.MovDate
import com.example.easyfinancing.ui.models.extract.Movimentation
import com.example.easyfinancing.ui.models.home_screen.Page1
import com.example.easyfinancing.ui.models.home_screen.Page2
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var recyclerView_HomeScreen_Resumos: RecyclerView
    private lateinit var recyclerViewHSmovimentation :RecyclerView
    private val orcamentos = Orcamentos()
    private val faturas = Faturas()

    lateinit var dataBase : AppDatabase
    lateinit var addMovimentation : MovimetationDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        setButtonStartActivityExtract()
        setButtonStartActivityNewMovimentation()
        setButtonStartActivityCategories()

        setPeriodo("MAI 2024")
        setSaldoDisponivel("R$ 0,00")
        setSaldo("R$ 0,00")

        recyclerView_HomeScreen_Resumos = findViewById(R.id.resumes_homescreen)
        orcamentos.setValores("0,00", "0,00")
        faturas.setValores("0,00", "01/01")
        recyclerViewResumos()

        setEntradas("0,00")
        setSaidas("0,00")
    }

    override fun onResume() {
        super.onResume()

        val movimentacoes : MutableList<Any> = mutableListOf()

        this.dataBase = AppDatabase.getInstance(this)
        this.addMovimentation = dataBase.movimentationDao()

        recyclerViewHSmovimentation = findViewById(R.id.HSmovimentation)

        CoroutineScope(Dispatchers.Main).launch {
            for (mov in addMovimentation.getMovs().toTypedArray()){
                setNovaMovimentacao(arrayOf(mov.date, mov.tipo, mov.descricao1, mov.descricao2, mov.valor, mov.id.toString()), movimentacoes)
            }
            recyclerViewExtrato(movimentacoes)
        }
    }

    private fun setButtonStartActivityExtract(){
        findViewById<LinearLayout>(R.id.extract_content).setOnClickListener{
            val EXTRATO = Intent(this, ExtractActivity::class.java)
            startActivity(EXTRATO)
        }
    }

    private fun setButtonStartActivityNewMovimentation(){
        findViewById<ImageButton>(R.id.addMov).setOnClickListener{
            val NEW_MOV = Intent(this, NewMovActivity::class.java)
            startActivity(NEW_MOV)
        }
    }

    private fun setButtonStartActivityCategories(){
        findViewById<ConstraintLayout>(R.id.category_resume).setOnClickListener{
            val CATEGORIES = Intent(this, CategoriesActivity::class.java)
            startActivity(CATEGORIES)
        }
    }

    fun setPeriodo(periodo : String){
        findViewById<TextView>(R.id.period).text = periodo
    }

    fun setSaldoDisponivel(saldo : String){
        findViewById<TextView>(R.id.value_balance).text = saldo
    }

    fun setSaldo(saldo : String){
        findViewById<TextView>(R.id.value_low_balance).text = saldo
    }

    private inner class Orcamentos{
        private lateinit var valores : Page1

        private var reservado : String = "0,00"
        private var utilizado : String = "0,00"

        constructor(){
            setValores(reservado, utilizado)
        }

        fun getValores() : Page1{
            return valores
        }

        fun setValores(reservado : String, utilizado : String){
            this.reservado = reservado
            this.utilizado = utilizado
            valores = Page1("Reservado: ${this.reservado}", "Utilizado: ${this.utilizado}")
        }
    }

    private inner class Faturas{
        private lateinit var valores : Page2

        private var valor : String = "0,00"
        private var vencimento : String = "00/00"

        constructor(){
            setValores(valor, vencimento)
        }

        fun getValores() : Page2{
            return valores
        }

        fun setValores(valor : String, vencimento : String){
            valores = Page2(valor, vencimento)
        }
    }

    fun recyclerViewResumos(){
        recyclerView_HomeScreen_Resumos.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_HomeScreen_Resumos.setHasFixedSize(true)
        val combinedAdapterPages = AdapaterCombinedHs(this, mutableListOf(orcamentos.getValores(), faturas.getValores()))
        recyclerView_HomeScreen_Resumos.adapter = combinedAdapterPages

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView_HomeScreen_Resumos)
    }

    fun setEntradas(valor: String){
        findViewById<TextView>(R.id.income_value).text = valor
    }

    fun setSaidas(valor: String){
        findViewById<TextView>(R.id.outcome_value).text = valor
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

    fun recyclerViewExtrato(list : MutableList<Any>){
        recyclerViewHSmovimentation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewHSmovimentation.setHasFixedSize(true)
        val combinedAdapterExtract = AdapterCombinedEx(this, list)
        recyclerViewHSmovimentation.adapter = combinedAdapterExtract
    }
}