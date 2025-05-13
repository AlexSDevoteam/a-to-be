/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.atobe.R
import kotlin.math.ceil

@Composable
fun HomeView(
    viewModel: HomeViewModel = hiltViewModel(),
    onDetailsClick: (Int) -> Unit,
    modifier: Modifier,
) {
    val state = viewModel.uiState.collectAsState().value

    when (state) {
        is HomeViewState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is HomeViewState.Error -> Box(
            modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = state.errorMessage,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, bottom = 8.dp),
                )
                Button(onClick = {
                    viewModel.retry()
                }) {
                    Text(stringResource(R.string.retry))
                }
            }
        }

        is HomeViewState.Success -> {
            val paginationState = viewModel.paginationState.collectAsState().value
            Column(modifier = modifier.fillMaxSize()) {
                LazyColumn(
                    modifier = Modifier.weight(1f)
                ) {
                    items(state.products) { product ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onDetailsClick(product.id)
                                }
                                .padding(16.dp)
                        ) {
                            Text(text = product.title, style = MaterialTheme.typography.titleMedium)
                            Text(
                                text = "Product: ${product.id}",
                                fontSize = 16.sp,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            )
                            Row(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp)
                            ) {
                                Text(
                                    text = "Rating: ${product.rating}",
                                    fontSize = 16.sp,
                                )
                                Icon(
                                    imageVector = chooseRatingIcon(product.rating),
                                    contentDescription = "rating",
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }
                        }
                        HorizontalDivider(thickness = 2.dp)
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = { viewModel.previousPage() },
                        enabled = paginationState.page > 0
                    ) {
                        Text("Previous")
                    }

                    Text("Page ${paginationState.page + 1} of ${ceil(state.total / paginationState.limit.toDouble()).toInt()}")

                    Button(
                        onClick = { viewModel.nextPage() },
                        enabled = (paginationState.page + 1) * paginationState.limit < state.total
                    ) {
                        Text("Next")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Items per page:", style = MaterialTheme.typography.bodyMedium)

                    Button(
                        onClick = { viewModel.selectNumberOfProductsShown(10) },
                        enabled = paginationState.limit != 10
                    ) { Text("10") }

                    Button(
                        onClick = { viewModel.selectNumberOfProductsShown(30) },
                        enabled = paginationState.limit != 30
                    ) { Text("30") }

                    Button(
                        onClick = { viewModel.selectNumberOfProductsShown(50) },
                        enabled = paginationState.limit != 50
                    ) { Text("50") }
                }
            }
        }
    }
}

fun chooseRatingIcon(rating: Double): ImageVector {
    return when {
        rating < 3 -> Icons.Default.Clear
        rating in 3.0..4.0 -> Icons.Default.ThumbUp
        else -> Icons.Default.Star

    }
}
