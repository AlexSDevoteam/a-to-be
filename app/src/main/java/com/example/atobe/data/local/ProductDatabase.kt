package com.example.atobe.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.atobe.data.local.product.ProductDao
import com.example.atobe.data.local.product.model.ProductEntity
import com.example.atobe.data.local.product.model.TotalEntity

@Database(entities = [ProductEntity::class, TotalEntity::class], version = 1, exportSchema = false)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
}