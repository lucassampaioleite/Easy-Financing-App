package com.example.easyfinancing.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tab_budgets")
data class BudgetsModel(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "idBudgets")
    var idBudgetsModel: Long? = null,

    @ColumnInfo(name = "nameBudgets")
    var nameBudgets: String,

    @ColumnInfo(name = "valueBudgets")
    var valueBudgets: Double,
)