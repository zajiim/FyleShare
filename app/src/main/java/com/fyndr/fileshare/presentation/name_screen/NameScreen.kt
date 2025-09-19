package com.fyndr.fileshare.presentation.name_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fyndr.fileshare.ui.theme.DarkBackground

@Composable
fun NameScreen(
    modifier: Modifier = Modifier,
    viewModel: NameScreenViewModel = hiltViewModel(),
    onNameSubmitted: () -> Unit = {}
) {
    val state = viewModel.state.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground)
    ) {
        TextField(
            value = state.value.name,
            onValueChange = { name ->
                viewModel.onEvent(NameScreenEvents.OnNameChange(name))
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        )

        TextButton(
            onClick = {
                viewModel.onEvent(NameScreenEvents.OnSubmitClick)
                onNameSubmitted()
            }
        ) {
            Text(text = "Submit")
        }
    }

}