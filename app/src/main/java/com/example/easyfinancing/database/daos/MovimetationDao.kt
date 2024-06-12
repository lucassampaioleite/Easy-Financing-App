package com.example.easyfinancing.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.easyfinancing.database.models.MovimentationModel

@Dao
interface MovimetationDao {

    @Insert
    suspend fun insertMov(movimentation: MovimentationModel)

    @Query("SELECT * FROM movimentationmodel ORDER BY data")
    suspend fun getMovs() : List<MovimentationModel>

    @Query("SELECT valor FROM movimentationmodel WHERE orcamentoId = :id AND tipo = false")
    suspend fun findAllValues(id: Long?): List<String>
  
    @Query("SELECT EXISTS (SELECT 1 FROM movimentationmodel WHERE categoriaId = :categoryId LIMIT 1) ")
    suspend fun verifyIfCategoryExist(categoryId : Int) : Boolean

    @Query("SELECT * FROM movimentationmodel WHERE categoriaId = :categoryId")
    suspend fun getMovimentationByCategory(categoryId: Int) : List<MovimentationModel>

    @Query("SELECT EXISTS (SELECT 1 FROM movimentationmodel WHERE cartaoId = :cartaoId LIMIT 1) ")
    suspend fun verifyIfCardUsed(cartaoId : Int) : Boolean

    @Query("SELECT * FROM movimentationmodel WHERE cartaoId = :cardId ORDER BY data")
    suspend fun getMovimentationByCard(cardId: Int) : List<MovimentationModel>

    @Query("SELECT * FROM movimentationmodel WHERE cartaoId != 0")
    suspend fun getMovimentationCards() : List<MovimentationModel>

    @Query("SELECT * FROM movimentationmodel WHERE orcamentoId != 0")
    suspend fun getMovimentationBudgets() : List<MovimentationModel>

    @Query("DELETE FROM movimentationmodel WHERE id = :movId")
    suspend fun deleteMov(movId : Int)

    @Query("SELECT codigoParcelamento FROM movimentationmodel WHERE id = :id")
    suspend fun getInstalmentsCode(id : Int) : String

    @Query("DELETE FROM movimentationmodel WHERE codigoParcelamento = :codigoParcelamento")
    suspend fun deleteCardMov(codigoParcelamento : String)
    @Query("SELECT cartaoId FROM MovimentationModel WHERE id = :id")
    suspend fun verifyIfMovHasCard(id : Int) : Int

    @Query("UPDATE movimentationmodel SET data = :data, tipo = :tipo, descricao = :descricao," +
            " categoriaId = :categoriaId, valor = :valor, recorrencia = :recorrencia, " +
            "cartaoId = :cartaoId, cartaoParcelas = :cartaoParcelas, orcamentoId = :orcamentoId" +
            " WHERE id = :id")
    suspend fun updateMov(data: String, tipo: Boolean, descricao: String, categoriaId: Int,
                          valor: String, recorrencia: Int, cartaoId: Int, cartaoParcelas: Int,
                          orcamentoId: Int, id: Int)
}