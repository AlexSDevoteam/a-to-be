/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data.remote

import com.example.atobe.data.remote.satellite.model.ProductApi
import com.example.atobe.data.remote.satellite.model.ProductCollectionApi
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductApi {
    @GET("products")
    suspend fun getProducts(
        @Query(value = "limit") limit: Int,
        @Query(value = "skip") skip: Int,
    ): ProductCollectionApi

    @GET("products/{id}")
    suspend fun getProductDetails(@Path("id") id: Int): ProductApi
}