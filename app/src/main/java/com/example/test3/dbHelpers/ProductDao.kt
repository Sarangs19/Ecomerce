package com.example.test3.dbHelpers

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.StateFlow

@Dao
interface ProductDao {

    @Insert
    suspend fun insertProduct(productDb: ProductDb)

    @Update
    suspend fun updateProduct(productDb: ProductDb)

    @Delete
    suspend fun deleteProduct(productDb: ProductDb)

    @Query("Select * from product")
    fun getDbProduct(): ProductDb

}