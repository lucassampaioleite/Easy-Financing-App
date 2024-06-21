package com.example.easyfinancing.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.easyfinancing.database.models.BudgetsModel

@Dao
interface BudgetsDAO {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(budgetsModel: BudgetsModel): Long

    @Query("SELECT * FROM TAB_BUDGETS")
    suspend fun findAll(): List<BudgetsModel>

    @Query("SELECT * FROM tab_budgets WHERE idBudgets = :idBudgets")
    suspend fun findById(idBudgets: Long): BudgetsModel

//    @Update
//    suspend fun update(budgetsModel: BudgetsModel): BudgetsModel

    @Delete
    suspend fun delete(budgetsModel: BudgetsModel)

}