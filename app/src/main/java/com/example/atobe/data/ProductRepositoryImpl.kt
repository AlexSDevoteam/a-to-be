/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data

import com.example.atobe.data.domain.model.Product
import com.example.atobe.data.domain.model.ProductCollection
import com.example.atobe.data.local.LocalDataSource
import com.example.atobe.data.remote.RemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
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
        page: Int
    ): Flow<ProductCollection> {
        val skip = page * limit
        val products = localDataSource.getProducts(limit = limit, skip = skip).onStart {
            fetchProductsIfNeeded(
                limit = limit,
                skip = skip
            )
        }
        return products.map {
            ProductCollection(products = it, total = localDataSource.getTotal().first())
        }
    }

    private suspend fun fetchProductsIfNeeded(limit: Int, skip: Int) {
        return mutex.withLock {
            val localProducts = localDataSource.getProducts(
                limit, skip
            ).first()

            if (localProducts.isNotEmpty()) {
                return@withLock
            }

            val remoteProductResult =
                remoteDataSource.getProducts(limit = 1).getOrThrow()

            localDataSource.setTotal(remoteProductResult.total)
            val allRemoteResults =
                remoteDataSource.getProducts(limit = remoteProductResult.total).getOrThrow()
            if (allRemoteResults.products.size != remoteProductResult.total) {
                return@withLock
            }
            localDataSource.setProducts(allRemoteResults)
        }
    }

    override fun getProductDetails(id: Int): Flow<Product?> {
        return localDataSource.getProductById(id)
    }
}