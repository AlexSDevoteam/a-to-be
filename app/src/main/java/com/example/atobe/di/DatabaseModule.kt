package com.example.atobe.di

import android.content.Context
import androidx.room.Room
import com.example.atobe.data.local.ProductDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideProductDao(database: ProductDatabase) = database.productDao()

    @Provides
    @Singleton
    fun provideProductDatabase(@ApplicationContext appContext: Context): ProductDatabase {
        return Room.databaseBuilder(
            appContext,
            ProductDatabase::class.java,
            "product_database"
        ).build()
    }
}