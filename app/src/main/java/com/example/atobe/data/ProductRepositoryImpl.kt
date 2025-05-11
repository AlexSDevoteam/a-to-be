/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data

import com.example.atobe.data.domain.model.Product
import com.example.atobe.data.local.LocalDataSource
import com.example.atobe.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
) : ProductRepository {
    private val mutex = Mutex()

    override fun getProducts(
        limit: Int,
        skip: Int
    ): Flow<List<Product>> {
        return localDataSource.getProducts(limit = limit, skip = skip).onStart {
            fetchProductsIfNeeded(
                limit = limit,
                skip = skip
            )
        }
    }

    private suspend fun fetchProductsIfNeeded(limit: Int, skip: Int) {
        return mutex.withLock {
            val localSatellites = localDataSource.getProducts(
                limit, skip
            ).first()

            if (localSatellites.isNotEmpty()) {
                return@withLock
            }

            val remoteSatellites = remoteDataSource.getProducts(
                skip = skip,
                limit = limit
            ).getOrThrow()

            localDataSource.setProducts(remoteSatellites)
        }
    }

    override fun getProductDetails(id: Int): Flow<Product?> {
        return localDataSource.getProductById(id).onStart {
            fetchSatelliteDetailsIfNeeded(id)
        }
    }

    private suspend fun fetchSatelliteDetailsIfNeeded(id: Int) {
        return mutex.withLock {
            val localSatellite = localDataSource.getProductById(id).firstOrNull()
            if (localSatellite != null) {
                return@withLock
            }
            val remoteSatellite = remoteDataSource.getProductDetails(id).getOrThrow()
            localDataSource.setProduct(remoteSatellite)
        }
    }

}