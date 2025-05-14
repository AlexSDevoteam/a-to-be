/*
 * Copyright (c) 2024 Sensormatic Electronics, LLC.  All rights reserved.
 * Reproduction is forbidden without written approval of Sensormatic Electronics, LLC.
 */

package com.example.atobe.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.atobe.data.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ProductRepository
) : ViewModel() {

    private var debounceJob: Job? = null
    private val _paginationState = MutableStateFlow(HomeViewPagination())
    val paginationState: StateFlow<HomeViewPagination> = _paginationState
    private val retryTrigger = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val uiState = retryTrigger.onStart { emit(Unit) }.flatMapLatest {
        paginationState.map { state ->
            HomeViewPagination(limit = state.limit, page = state.page)
        }.distinctUntilChanged()
            .flatMapLatest { state ->
                repository.getProducts(
                    limit = state.limit,
                    page = state.page,
                )
            }.map {
                HomeViewState.Success(it.products, it.total)
            }.onStart<HomeViewState> { emit(HomeViewState.Loading) }
            .catch { error ->
                emit(
                    HomeViewState.Error(
                        errorMessage = "An error occurred: ${error.message}"
                    )
                )
            }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = HomeViewState.Loading
    )

    fun retry() = retryTrigger.tryEmit(Unit)

    fun nextPage() = debounce(200) {
        _paginationState.update {
            it.copy(page = it.page + 1)
        }
    }

    fun previousPage() = debounce(200) {
        _paginationState.update {
            it.copy(page = it.page - 1)
        }
    }

    fun selectNumberOfProductsShown(selectedNumber: Int) = debounce(300) {
        _paginationState.update {
            it.copy(limit = selectedNumber, page = 0)
        }
    }


    private fun debounce(debounceDelay: Long, action: suspend () -> Unit) {
        debounceJob?.cancel() // Cancel the previous job if it's still active
        debounceJob = viewModelScope.launch {
            delay(debounceDelay) // Wait for the debounce delay
            action() // Execute the action
        }
    }
}
