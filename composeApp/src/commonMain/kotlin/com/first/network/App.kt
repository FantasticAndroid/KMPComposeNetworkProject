package com.first.network

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    /*val censorViewModel = viewModel{
        CensorViewModel()
    }*/

    val censorViewModel  : CensorViewModel = koinViewModel()
    MaterialTheme {

        // Read if any previous censor text available in datastore (Datastore read)
        val censorData by censorViewModel.censorData.collectAsState(initial = "")

        val uiState by censorViewModel.uiState
        var uncensoredText by remember {
            mutableStateOf("")
        }

        val scope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text("Last Censored Text: $censorData")

            TextField(
                value = uncensoredText,
                onValueChange = { uncensoredText = it },
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth(),
                placeholder = {
                    Text("Uncensored text")
                }
            )
            Button(onClick = {
                scope.launch {
                    censorViewModel.getCensoredText(uncensoredText)
                }
            }) {
                if (uiState is UiState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(15.dp),
                        strokeWidth = 1.dp,
                        color = Color.White
                    )
                } else {
                    Text("Do Censor!")
                }
            }

            when (uiState) {
                is UiState.Loading -> {}
                is UiState.Error -> Text((uiState as UiState.Error).message ?: "Null Error")
                is UiState.Success -> Text((uiState as UiState.Success).data)
                null -> {
                }
            }

            Text(text = "Platform: ${getPlatform().name}")
        }
    }
}