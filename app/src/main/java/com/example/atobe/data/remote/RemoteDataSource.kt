package com.example.atobe.data.remote

import com.example.atobe.data.domain.model.Product

interface RemoteDataSource {
    suspend fun getProducts(
        limit: Int,
        skip: Int
    ): Result<List<Product>>

    suspend fun getProductDetails(id: Int): Result<Product>
}