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

    @Query("DELETE FROM movimentationmodel WHERE id = :movId")
    suspend fun deleteMov(movId : Int)

    @Query("UPDATE movimentationmodel SET data = :data, tipo = :tipo, descricao = :descricao, categoriaId = :categoriaId, valor = :valor, recorrencia = :recorrencia, cartaoId = :cartaoId, cartaoParcelas = :cartaoParcelas, orcamentoId = :orcamentoId WHERE id = :id")
    suspend fun updateMov(data: String, tipo: Boolean, descricao: String, categoriaId: Int, valor: String, recorrencia: Int, cartaoId: Int, cartaoParcelas: Int, orcamentoId: Int, id: Int)
}