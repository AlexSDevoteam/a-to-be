/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data

import com.example.atobe.data.domain.model.Product
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(
        limit: Int,
        skip: Int
    ): Flow<List<Product>>

    fun getProductDetails(id: Int): Flow<Product?>
}