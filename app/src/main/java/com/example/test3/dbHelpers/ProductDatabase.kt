package com.example.test3.dbHelpers

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlin.coroutines.coroutineContext

@Database(entities = [ProductDb::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object{
        @Volatile
        private var INSTANCE : ProductDatabase? = null

        fun getDatabase(context: Context) : ProductDatabase{
                if(INSTANCE==null){
                    synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        ProductDatabase::class.java, "productDB").build()
                }
            }
            return INSTANCE!!
        }
    }

}