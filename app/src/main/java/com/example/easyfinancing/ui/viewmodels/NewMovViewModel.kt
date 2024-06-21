package com.example.easyfinancing.ui.viewmodels

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import java.util.Locale

class NewMovViewModel : ViewModel(){

    private val _movimentationList: MutableLiveData<Array<String>> = MutableLiveData()
    val movimentationList: LiveData<Array<String>> get() = _movimentationList

    var movId : Int = 0
    var movDate : LocalDate = LocalDate.now()
    var movType : Boolean = true
    var movDesc : String = "Sem descrição"
    var movValue : String = ""
    var movCatId : Int = 0
    var movRecurence : Int = 0
    var movCardId : Int = 0
    var movCardInstalments : Int = 0
    var movBudgetId : Int = 0

    fun getFormatedDate(date: LocalDate) : String{
        val formmater = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR"))
        return formmater.format(Date.from(date.atStartOfDay(ZoneId.systemDefault()).toInstant()))
    }

    fun getDateExpanded() : String{
        val formmater = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("pt", "BR"))
        val date = Date.from(movDate.atStartOfDay(ZoneId.systemDefault()).toInstant())
        var dateFormatted = formmater.format(date)
        dateFormatted = dateFormatted.replaceFirstChar{it.uppercase()}
        dateFormatted = dateFormatted.replace("-feira", "")

        return dateFormatted
    }
}