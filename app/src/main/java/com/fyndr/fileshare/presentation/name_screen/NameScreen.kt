package com.fyndr.fileshare.presentation.name_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fyndr.fileshare.presentation.components.UserAvatar
import com.fyndr.fileshare.ui.theme.DarkBackground
import com.fyndr.fileshare.utils.VSpacer

@Composable
fun NameScreen(
    modifier: Modifier = Modifier,
    viewModel: NameScreenViewModel = hiltViewModel(),
    onNameSubmitted: () -> Unit = {}
) {
    val state = viewModel.state.collectAsState()
    LaunchedEffect(state.value.shouldNavigate) {
        if (state.value.shouldNavigate) {
            onNameSubmitted()
            viewModel.onEvent(NameScreenEvents.OnNavigationDone)
        }
    }
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        verticalArrangement = Arrangement.Center
    ) {

        UserAvatar(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterHorizontally),
            name = state.value.name,
            color = Color.Red,
            size = 100.dp,
            isLoading = state.value.isLoading,
            onAnimationComplete = {
                viewModel.onEvent(NameScreenEvents.OnAnimationComplete)
            }
        )

        VSpacer(40.dp)

        if (!state.value.isLoading) {
            TextField(
                value = state.value.name,
                onValueChange = { name ->
                    if (!state.value.isLoading) {
                        viewModel.onEvent(NameScreenEvents.OnNameChange(name))
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                maxLines = 1,
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent, // Remove underline when focused
                    unfocusedIndicatorColor = Color.Transparent, // Remove underline when not focused
                    disabledIndicatorColor = Color.Transparent, // Remove underline when disabled
                    focusedContainerColor = Color.LightGray.copy(alpha = 0.8f), // Darker background when focused
                    unfocusedContainerColor = Color.LightGray.copy(alpha = 0.6f), // Darker background when not focused
                    cursorColor = MaterialTheme.colorScheme.primary, // Optional: customize cursor color
                    focusedTextColor = Color.Black, // Optional: customize text color
                    unfocusedTextColor = Color.Black // Optional: customize text color
                )
            )
        }

        TextButton(
            modifier = Modifier
                .align(Alignment.End)
                .padding(end = 24.dp), onClick = {
                viewModel.onEvent(NameScreenEvents.OnSubmitClick)
            }) {
            if (state.value.name.length > 2) {
                Text(text = "Submit")
            }
        }
    }

}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun PreviewNameScreen() {
    NameScreen(modifier = Modifier.safeDrawingPadding())
}