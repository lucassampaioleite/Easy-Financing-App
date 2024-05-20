package com.example.easyfinancing.ui.models.extract

data class Movimentation(
    val id : Int,
    val date : String,
    val img : Int,
    val mainDescription : String,
    val auxDescription : String,
    val movAmount : String
)