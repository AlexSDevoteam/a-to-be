/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.ui.details

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.atobe.R
import com.example.atobe.data.domain.model.Product

@Composable
fun DetailsView(
    viewModel: DetailsViewModel,
    id: Int,
    modifier: Modifier
) {
    val product = viewModel.getSatelliteDetails(id = id).collectAsState(
        Product(
            id = id,
            title = "",
            price = 0.0,
            thumbnail = "",
            discount = 0.0,
            rating = 0.0,
            stock = 0
        )
    ).value
    val details = listOf(
        "Name" to product?.title,
        "Product" to product?.id,
        "Price" to product?.price,
        "Discount" to product?.discount,
        "Rating" to product?.rating,
        "Stock" to product?.stock,
        "Thumbnail" to product?.thumbnail
    )
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(details) { (label, value) ->
            if (label == "Thumbnail") {
                if (!LocalContext.current.isNetworkAvailable()) {
                    return@items
                }
                return@items AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(value)
                        .crossfade(true)
                        .build(),
                    contentDescription = product?.title,
                    modifier = Modifier
                        .size(120.dp)
                        .padding(top = 8.dp),
                    error = painterResource(R.drawable.error),
                    onLoading = {
                        Icons.Outlined.Refresh
                    },
                )
            }
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp, vertical = 8.dp),
            ) {
                Text(
                    "$label: ",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.alignByBaseline()
                )
                Text(
                    text = value.toString(),
                    fontSize = 20.sp,
                )
            }
        }
    }
}

fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val network = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
    return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
}
