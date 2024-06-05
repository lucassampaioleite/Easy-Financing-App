package com.example.easyfinancing.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.easyfinancing.database.daos.MovimetationDao
import com.example.easyfinancing.database.models.MovimentationModel

@Database(entities = [MovimentationModel::class], version = 1)
abstract class AppDatabase :RoomDatabase() {

    abstract fun movimentationDao() : MovimetationDao

    companion object {

        private const val DATABASE_NAME: String = "movimentation-db"

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
            ).build()
    }

}