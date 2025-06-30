package com.first.network

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class CensorViewModel(private val censorUseCase: CensorUseCase) : ViewModel() {

    val uiState = mutableStateOf<UiState?>(null)

    fun getCensoredText(text: String) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            censorUseCase(text).onSuccess {
                uiState.value = UiState.Success(it)
            }.onFailure {
                uiState.value = UiState.Error(it.message)
            }
        }
    }
}

sealed class UiState {
    object Loading : UiState()
    data class Success(val data: String) : UiState()
    data class Error(val message: String?) : UiState()
}