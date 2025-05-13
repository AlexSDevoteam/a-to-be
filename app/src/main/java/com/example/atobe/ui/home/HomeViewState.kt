/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.ui.home

import com.example.atobe.data.domain.model.Product

sealed interface HomeViewState {
    data object Loading : HomeViewState

    data class Success(
        val products: List<Product>,
        val total: Int = 0
    ) : HomeViewState

    data class Error(val errorMessage: String) : HomeViewState
}

data class HomeViewPagination(
    val limit: Int = 30,
    val page: Int = 0
)