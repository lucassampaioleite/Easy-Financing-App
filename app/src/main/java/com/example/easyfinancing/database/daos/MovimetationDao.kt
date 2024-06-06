package com.example.easyfinancing.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.easyfinancing.database.models.MovimentationModel

@Dao
interface MovimetationDao {

    @Insert
    suspend fun insertMov(movimentation: MovimentationModel)

    @Query("SELECT * FROM movimentationmodel ORDER BY data")
    suspend fun getMovs() : List<MovimentationModel>

}