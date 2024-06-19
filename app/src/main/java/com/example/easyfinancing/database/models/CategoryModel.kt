package com.example.easyfinancing.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CategoryModel (
    val icon : Int,
    val name : String
){
    @PrimaryKey(autoGenerate = true)
    var id : Int = 0
}