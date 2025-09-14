package com.fyndr.fileshare.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.fyndr.fileshare.ui.theme.DarkBackground

@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    viewmodel: OnBoardingViewmodel = hiltViewModel(),
    onWelcomeClicked: () -> Unit = {}
) {
    val state = viewmodel.state.collectAsState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(DarkBackground),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Welcome to Fyndr",
            modifier = Modifier.clickable {
                viewmodel.onEvent(OnBoardingEvents.SaveOnBoardingEvent)
                onWelcomeClicked()
            },
        )

    }
}