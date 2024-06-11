package com.example.easyfinancing.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.easyfinancing.database.models.CardModel

@Dao
interface CardDao {

    @Insert
    suspend fun insertCard(card : CardModel)

    @Query("SELECT * FROM CardModel")
    suspend fun getAllCard() : MutableList<CardModel>

    @Query("DELETE FROM cardmodel WHERE id = :id")
    suspend fun deleteCard(id : Int)

    @Query("SELECT COUNT(*) FROM CardModel")
    suspend fun getNumberOfCards() : Int
}