package com.example.easyfinancing.ui

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfinancing.R
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.BudgetsDAO
import com.example.easyfinancing.database.daos.CardDao
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
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale

class HomeScreenActivity : AppCompatActivity() {
    private lateinit var recyclerView_HomeScreen_Resumos: RecyclerView
    private lateinit var recyclerViewHSmovimentation :RecyclerView
    private val orcamentos = Orcamentos()
    private val faturas = Faturas()

    lateinit var dataBase : AppDatabase
    lateinit var addMovimentation : MovimetationDao
    lateinit var cardDao: CardDao
    lateinit var budgetDao : BudgetsDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        setButtonStartActivityExtract()
        setButtonStartActivityNewMovimentation()
        setButtonStartActivityCategories()

        findViewById<ImageButton>(R.id.menu).setOnClickListener {
            openMenuPopUp()
        }

        recyclerView_HomeScreen_Resumos = findViewById(R.id.resumes_homescreen)
    }

    override fun onResume() {
        super.onResume()

        val movimentacoes : MutableList<Any> = mutableListOf()

        this.dataBase = AppDatabase.getInstance(this)
        this.addMovimentation = dataBase.movimentationDao()
        this.cardDao = dataBase.cardDao()
        this.budgetDao = dataBase.budgetsDao()

        recyclerViewHSmovimentation = findViewById(R.id.HSmovimentation)

        CoroutineScope(Dispatchers.Main).launch {
            for (mov in addMovimentation.getMovs().toTypedArray()){
                setNovaMovimentacao(
                    Movimentation(mov.id, LocalDate.parse(mov.data), mov.tipo, mov.descricao, mov.categoriaId, mov.valor, mov.cartaoId, mov.cartaoParcelas, mov.recorrencia, mov.orcamentoId),
                    movimentacoes
                )
            }
            recyclerViewExtrato(movimentacoes)

            setSaldoDisponivel(movimentacoes)
            setEntradas(movimentacoes)
            setSaidas(movimentacoes)

            var TotalBill = 0f

            for(mov in addMovimentation.getMovimentationCards()){
                TotalBill += if (mov.tipo) (formatNumberToFloat(mov.valor)/mov.cartaoParcelas) else -(formatNumberToFloat(mov.valor)/mov.cartaoParcelas)
            }

            faturas.setValores(formatFloatToReais(Math.abs(TotalBill)), cardDao.getNumberOfCards().toString())

            var TotalBudgetsReserved = 0.0
            var TotalBudgetsUsed = 0f

            for (budget in budgetDao.findAll()){
                TotalBudgetsReserved += budget.valueBudgets
            }

            for (mov in addMovimentation.getMovimentationBudgets()){
                TotalBudgetsUsed += if (!mov.tipo) formatNumberToFloat(mov.valor) else 0f
            }

            orcamentos.setValores(formatFloatToReais(TotalBudgetsReserved.toFloat()), formatFloatToReais(TotalBudgetsUsed))
            recyclerViewResumos()
        }

        setPeriodo("MAI 2024")
        setSaldo("R$ 0,00")
    }

    private fun openMenuPopUp(){
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_main_menu, null)
        val dialog = Dialog(this)

        view.findViewById<ImageButton>(R.id.extract_side_menu_inner).setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, ExtractActivity::class.java))
        }

        view.findViewById<ImageButton>(R.id.budget_side_menu_inner).setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, BudgetsActivity::class.java))
        }

        view.findViewById<ImageButton>(R.id.card_side_menu_inner).setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, CardActivity::class.java))
        }

        view.findViewById<ImageButton>(R.id.categories_side_menu_inner).setOnClickListener {
            dialog.dismiss()
            startActivity(Intent(this, CategoriesActivity::class.java))
        }

        dialog.setContentView(view)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
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

    fun setSaldoDisponivel(movimentation: MutableList<Any>){
        var Saldo = 0f

        for (mov in movimentation){
            if (mov is Movimentation){
                if(mov.cardId != 0){
                    Saldo += if (mov.type) (formatNumberToFloat(mov.movAmount)/mov.cardInstalments) else -(formatNumberToFloat(mov.movAmount)/mov.cardInstalments)
                }else{
                    Saldo += if (mov.type) formatNumberToFloat(mov.movAmount) else -formatNumberToFloat(mov.movAmount)
                }
            }
        }

        findViewById<TextView>(R.id.value_balance).text = "R$ ${formatFloatToReais(Saldo)}"
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

        setupSnapHelper(recyclerView_HomeScreen_Resumos)
    }

    private fun setupSnapHelper(recyclerView: RecyclerView) {
        if (recyclerView.onFlingListener != null) {
            recyclerView.onFlingListener = null
        }

        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(recyclerView)
    }

    fun setEntradas(movimentation: MutableList<Any>){
        var TotalEntradas = 0f

        for (mov in movimentation){
            if (mov is Movimentation && mov.type){
                TotalEntradas += formatNumberToFloat(mov.movAmount)
            }
        }

        findViewById<TextView>(R.id.income_value).text = formatFloatToReais(TotalEntradas)
    }

    fun setSaidas(movimentation: MutableList<Any>){
        var TotalSaidas = 0f

        for (mov in movimentation){
            if (mov is Movimentation && !mov.type){
                if(mov.cardId != 0){
                    TotalSaidas += (formatNumberToFloat(mov.movAmount)/mov.cardInstalments)
                }else{
                    TotalSaidas += formatNumberToFloat(mov.movAmount)
                }
            }
        }

        findViewById<TextView>(R.id.outcome_value).text = formatFloatToReais(TotalSaidas)
    }

    fun formatNumberToFloat(number : String) : Float{
        return number
            .replace("R$ ", "")
            .replace(".", "")
            .replace(",", ".").toFloat()
    }

    fun formatFloatToReais(number : Float) : String{
        val symbols = DecimalFormatSymbols(Locale("pt", "BR"))
        symbols.decimalSeparator = ','
        symbols.groupingSeparator = '.'

        return "${DecimalFormat("#,##0.00", symbols).format(number)}"
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

    fun recyclerViewExtrato(list : MutableList<Any>){
        recyclerViewHSmovimentation.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerViewHSmovimentation.setHasFixedSize(true)
        val combinedAdapterExtract = AdapterCombinedEx(this, list){
            startActivity(Intent(this, ExtractActivity::class.java))
        }
        recyclerViewHSmovimentation.adapter = combinedAdapterExtract
    }
}