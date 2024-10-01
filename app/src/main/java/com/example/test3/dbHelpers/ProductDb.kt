package com.example.test3.dbHelpers

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "product")
data class ProductDb(
    @PrimaryKey(autoGenerate = true)
    val id : Long,
    val title : String,
    val desc : String
)
