package com.example.easyfinancing.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.example.easyfinancing.database.daos.BudgetsDAO
import com.example.easyfinancing.database.daos.CardDao
import com.example.easyfinancing.database.daos.CategoryDao
import com.example.easyfinancing.database.daos.MovimetationDao
import com.example.easyfinancing.database.models.BudgetsModel
import com.example.easyfinancing.database.models.CardModel
import com.example.easyfinancing.database.models.CategoryModel
import com.example.easyfinancing.database.models.MovimentationModel

@Database(entities = [MovimentationModel::class, CategoryModel::class, CardModel::class, BudgetsModel::class], version = 1)

abstract class AppDatabase : RoomDatabase() {

    abstract fun movimentationDao() : MovimetationDao
    abstract fun categoryDao() : CategoryDao
    abstract fun budgetsDao(): BudgetsDAO
    abstract fun cardDao() : CardDao

    companion object {

        private const val DATABASE_NAME: String = "financa_facil_db"

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .build()
    }



}