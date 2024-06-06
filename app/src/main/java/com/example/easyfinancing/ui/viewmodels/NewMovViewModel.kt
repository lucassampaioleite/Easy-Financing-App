package com.example.easyfinancing.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewMovViewModel : ViewModel(){

    private val _movimentationList: MutableLiveData<Array<String>> = MutableLiveData()
    val movimentationList: LiveData<Array<String>> get() = _movimentationList

    var movDate = Date()
    var movType : Boolean = true
    var movDesc : String = "Sem descrição"
    var movValue : String = ""
    var movCatId : Int = 0
    var movRecurence : Int = 0
    var movCardId : Int = 0
    var movCardInstalments : Int = 1
    var movBudgetId : Int = 0

    fun getFormatedDate(date: Date) : String{
        val formmater = SimpleDateFormat("yyyy-MM-dd", Locale("pt", "BR"))
        return formmater.format(date)
    }

    fun getDateExpanded() : String{
        val formmater = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("pt", "BR"))
        var dateFormatted = formmater.format(movDate)
        dateFormatted = dateFormatted.replaceFirstChar{it.uppercase()}
        dateFormatted = dateFormatted.replace("-feira", "")

        return dateFormatted
    }
}