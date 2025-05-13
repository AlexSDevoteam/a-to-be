package com.example.atobe.data.remote

import com.example.atobe.data.domain.model.Product
import com.example.atobe.data.domain.model.ProductCollection

interface RemoteDataSource {
    suspend fun getProducts(
        limit: Int,
        skip: Int
    ): Result<ProductCollection>

    suspend fun getProductDetails(id: Int): Result<Product>
}