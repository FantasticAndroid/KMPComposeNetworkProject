package com.first.network

import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

private val PREF_KEY_CENSOR_DATA = stringPreferencesKey("censor_data")

class CensorViewModel(private val censorUseCase: CensorUseCase, private val dataStore : DataStore<Preferences>) : ViewModel() {

    val uiState = mutableStateOf<UiState?>(null)
    val censorData = dataStore.data
        .map {
            it[PREF_KEY_CENSOR_DATA] ?: ""
        }

    fun getCensoredText(text: String) {
        viewModelScope.launch {
            uiState.value = UiState.Loading
            censorUseCase(text).onSuccess { result ->
                dataStore.edit { it[PREF_KEY_CENSOR_DATA] = result }
                uiState.value = UiState.Success(result)
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