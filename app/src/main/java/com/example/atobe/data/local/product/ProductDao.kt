package com.example.atobe.data.local.product

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.atobe.data.local.product.model.ProductEntity
import com.example.atobe.data.local.product.model.TotalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM Product ORDER BY id ASC LIMIT :limit OFFSET :skip")
    fun getProducts(limit: Int, skip: Int): Flow<List<ProductEntity>>

    @Query("SELECT * FROM Product WHERE id = :id")
    fun getProductDetails(id: Int): Flow<ProductEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(product: List<ProductEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProduct(satellite: ProductEntity)

    @Query("SELECT * FROM Total")
    fun getTotal(): Flow<TotalEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTotal(total: TotalEntity)
}
