/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data.remote

import com.example.atobe.data.remote.satellite.model.toDomain
import javax.inject.Inject

class RemoteDataSourceImpl @Inject constructor(private val productApi: ProductApi) :
    RemoteDataSource {

    override suspend fun getProducts(limit: Int) = runCatching {
        productApi.getProducts(limit).toDomain()
    }
}