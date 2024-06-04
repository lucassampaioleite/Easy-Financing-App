package com.example.easyfinancing.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.easyfinancing.database.AppDatabase
import com.example.easyfinancing.database.daos.MovimetationDao
import com.example.easyfinancing.database.models.MovimentationModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NewMovViewModel : ViewModel(){

    private val _movimentationList: MutableLiveData<Array<String>> = MutableLiveData()
    val movimentationList: LiveData<Array<String>> get() = _movimentationList
    private lateinit var currentMovimentationList: Array<String>

    var movType : Boolean = true
    var movValue : String = ""
    var movDesc : String = "Sem descrição"
    var movDate : Date = Date()

    fun setChosenMovDate(chosenDate : Date){
        this.movDate = chosenDate
    }

    fun getDateFormmated() : String{
        val formmater = SimpleDateFormat("EEEE, dd MMM yyyy", Locale("pt", "BR"))
        var dateFormatted = formmater.format(movDate)
        dateFormatted = dateFormatted.replaceFirstChar{it.uppercase()}
        dateFormatted = dateFormatted.replace("-feira", "")

        return dateFormatted
    }

    private fun typedSelection(type : Boolean) : String{
        return if (type) "E" else "S"
    }

    override fun onCleared() {
        super.onCleared()

        Log.i("movimentationList", "A viewModel foi destruida")
    }
}