package com.example.easyfinancing.ui.models.extract

data class Movimentation(
    val id : Int,
    val date : String,
    val icon : Int,
    val mainDescription : String,
    val categoryName : String,
    val movAmount : String,

    val cardIcon : Boolean,
    val recurenceIcon : Boolean,
    val budgetIcon : Boolean
)