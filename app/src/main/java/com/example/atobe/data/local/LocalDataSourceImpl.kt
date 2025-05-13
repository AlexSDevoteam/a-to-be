/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data.local

import com.example.atobe.data.domain.model.Product
import com.example.atobe.data.domain.model.ProductCollection
import com.example.atobe.data.domain.model.toDatabaseEntity
import com.example.atobe.data.local.product.ProductDao
import com.example.atobe.data.local.product.model.toDomain
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LocalDataSourceImpl @Inject constructor(
    private val productDao: ProductDao
) : LocalDataSource {


    override fun getProducts(
        limit: Int,
        skip: Int
    ): Flow<List<Product>> {
        return productDao.getProducts(limit, skip).map { list -> list.map { it.toDomain() } }
    }

    override fun getProductById(id: Int): Flow<Product?> {
        return productDao.getProductDetails(id).map { it?.toDomain() }
    }

    override suspend fun setProducts(products: ProductCollection) {
        productDao.insertProducts(products.toDatabaseEntity())
    }

    override suspend fun setProduct(product: Product) {
        productDao.insertProduct(product.toDatabaseEntity())
    }
}