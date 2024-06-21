package com.example.easyfinancing.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.easyfinancing.database.models.CategoryModel
import com.example.easyfinancing.ui.models.category.Category

@Dao
interface CategoryDao {

    @Insert
    suspend fun insertCategory(category: CategoryModel)

    @Query("SELECT * FROM CategoryModel")
    suspend fun getAllCategories() : List<CategoryModel>

    @Query("SELECT name FROM CategoryModel WHERE id = :id")
    suspend fun getCategoryName(id : Int) : String

    @Query("DELETE FROM CategoryModel WHERE id = :id")
    suspend fun deleteCategory(id : Int)
}