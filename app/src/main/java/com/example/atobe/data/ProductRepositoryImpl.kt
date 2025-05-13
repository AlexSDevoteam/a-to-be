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
import kotlinx.coroutines.flow.firstOrNull
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
    private var total = 0

    override fun getProducts(
        limit: Int,
        page: Int
    ): Flow<ProductCollection> {
        val products = localDataSource.getProducts(limit = limit, skip = page).onStart {
            fetchProductsIfNeeded(
                limit = limit,
                skip = page * limit
            )
        }
        return products.map {
            ProductCollection(products = it, total = total)
        }
    }

    private suspend fun fetchProductsIfNeeded(limit: Int, skip: Int) {
        return mutex.withLock {
            val localProducts = localDataSource.getProducts(
                limit, skip
            ).first()

            if (localProducts.isNotEmpty() && localProducts.size >= limit) {
                return@withLock
            }

            val remoteProductResult = remoteDataSource.getProducts(
                skip = skip,
                limit = limit
            ).getOrThrow()

            localDataSource.setProducts(remoteProductResult)
            total = remoteProductResult.total
        }
    }

    override fun getProductDetails(id: Int): Flow<Product?> {
        return localDataSource.getProductById(id).onStart {
            fetchProductDetailsIfNeeded(id)
        }
    }

    private suspend fun fetchProductDetailsIfNeeded(id: Int) {
        return mutex.withLock {
            val localProduct = localDataSource.getProductById(id).firstOrNull()
            if (localProduct != null) {
                return@withLock
            }
            val remoteProduct = remoteDataSource.getProductDetails(id).getOrThrow()
            localDataSource.setProduct(remoteProduct)
        }
    }

}