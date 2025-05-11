/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data.local

import com.example.atobe.data.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface LocalDataSource {
    fun getProducts(
        limit: Int,
        skip: Int
    ): Flow<List<Product>>

    fun getProductById(id: Int): Flow<Product?>
    suspend fun setProducts(products: List<Product>)
    suspend fun setProduct(product: Product)
}