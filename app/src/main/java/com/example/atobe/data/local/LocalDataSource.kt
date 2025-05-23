/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data.local

import com.example.atobe.data.domain.model.Product
import com.example.atobe.data.domain.model.ProductCollection
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getProducts(
        limit: Int,
        skip: Int
    ): Flow<List<Product>>
    fun getProductById(id: Int): Flow<Product?>
    suspend fun setProducts(products: ProductCollection)
    suspend fun setProduct(product: Product)
    fun getTotal(): Flow<Int>
    suspend fun setTotal(total: Int)

}