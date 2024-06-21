package com.example.easyfinancing.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CardModel(
    val nickname : String,
    val dueDate : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}
