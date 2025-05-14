package com.example.atobe.data.remote

import com.example.atobe.data.domain.model.ProductCollection

interface RemoteDataSource {
    suspend fun getProducts(limit: Int): Result<ProductCollection>
}