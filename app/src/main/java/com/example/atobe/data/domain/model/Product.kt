/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data.domain.model

import com.example.atobe.data.local.product.model.ProductEntity

data class ProductCollection(
    val products: List<Product>,
    val total: Int
)

data class Product(
    val id: Int,
    val thumbnail: String,
    val title: String,
    val rating: Double,
    val price: Double,
    val discount: Double,
    val stock: Int
)

fun Product.toDatabaseEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        thumbnail = this@toDatabaseEntity.thumbnail,
        title = title,
        rating = rating,
        price = price,
        discount = discount,
        stock = stock
    )
}

fun ProductCollection.toDatabaseEntity(): List<ProductEntity> {
    return products.map { it.toDatabaseEntity() }
}
