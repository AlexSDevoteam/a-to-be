/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.ui.details

import androidx.lifecycle.ViewModel
import com.example.atobe.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: ProductRepository,
) : ViewModel() {

    fun getSatelliteDetails(id: Int) = repository.getProductDetails(id)
}
