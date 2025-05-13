package com.example.atobe.data.local.product.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.atobe.data.domain.model.Product

@Entity(tableName = "Product")
data class ProductEntity(
    @PrimaryKey
    val id: Int,
    val thumbnail: String,
    val title: String,
    val rating: Double,
    val price: Double,
    val discount: Double,
    val stock: Int
)

fun ProductEntity.toDomain(): Product {
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

@Entity(tableName = "Total")
data class TotalEntity(
    @PrimaryKey
    val id: Int = 1,
    val total: Int
)
