package com.first.network

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.first.network.client.createHttpClient
import kotlinx.coroutines.launch

class CensorViewModel() : ViewModel() {

    private val censorUseCase: CensorUseCase = CensorUseCase(CensorRepository(createHttpClient(getNetworkEngine())))

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
    data class Success(val data: CensoredResponse) : UiState()
    data class Error(val message: String?) : UiState()
}