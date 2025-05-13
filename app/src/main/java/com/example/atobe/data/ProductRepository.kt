/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data

import com.example.atobe.data.domain.model.Product
import com.example.atobe.data.domain.model.ProductCollection
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getProducts(
        limit: Int,
        page: Int,
    ): Flow<ProductCollection>

    fun getProductDetails(id: Int): Flow<Product?>
}