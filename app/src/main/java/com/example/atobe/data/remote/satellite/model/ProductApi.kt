/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.data.remote.satellite.model

import com.example.atobe.data.domain.model.Product
import com.example.atobe.data.domain.model.ProductCollection
import com.google.gson.annotations.SerializedName

data class ProductCollectionApi(
    val products: List<ProductApi>,
    val total: Int
)

data class ProductApi(
    val id: Int,
    val thumbnail: String,
    val title: String,
    val rating: Double,
    val price: Double,
    @SerializedName("discountPercentage") val discount: Double,
    val stock: Int
)

fun ProductApi.toDomain(): Product {
    return Product(
        id = id,
        thumbnail = thumbnail,
        title = title,
        rating = rating,
        price = price,
        discount = discount,
        stock = stock
    )
}

fun ProductCollectionApi.toDomain(): ProductCollection {
    return ProductCollection(
        products = products.map { it.toDomain() },
        total = total
    )

}