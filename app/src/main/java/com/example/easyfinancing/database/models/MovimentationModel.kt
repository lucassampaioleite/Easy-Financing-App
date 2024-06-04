package com.example.easyfinancing.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovimentationModel (
    val date : String,
    val tipo : String,
    val descricao1 : String,
    val descricao2 : String,
    val valor : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}