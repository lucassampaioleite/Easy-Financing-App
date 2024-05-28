package com.example.easyfinancing.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfinancing.ui.models.extract.Movimentation
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewMovViewModel : ViewModel(){

    var movimentationList : List<String> = mutableListOf()

    var movType : Boolean = true
    var movValue : String = ""
    var movDesc : String = "Sem descrição"
    var movDate : Date = Date()

    fun setDataValues(type : Boolean,value : String, description : String){
        this.movType = type
        this.movValue = value
        this.movDesc = description
    }

    fun setChosenMovDate(chosenDate : Date){
        this.movDate = chosenDate
    }

    fun getDate() : String{
        val formmater = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return formmater.format(movDate)
    }

    fun getDateFormmated() : String{
        val formmater = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("pt", "BR"))
        var dateFormatted = formmater.format(movDate)
        dateFormatted = dateFormatted.replaceFirstChar{it.toUpperCase()}
        dateFormatted = dateFormatted.replace("-feira", "")

        return dateFormatted
    }
}