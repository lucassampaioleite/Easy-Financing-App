package com.example.easyfinancing.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovimentationModel (
    val data : String,
    val tipo : Boolean,
    val descricao : String,
    val categoriaId : Int,
    val valor : String,
    val recorrencia : Int,
    val cartaoId : Int,
    val cartoParcela : Int,
    val cartaoParcelas : Int,
    val codigoParcelamento : String,
    val orcamentoId : Int
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}