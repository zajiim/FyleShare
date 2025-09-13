package com.fyndr.fileshare.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fyndr.fileshare.presentation.home.HomeScreen
import com.fyndr.fileshare.presentation.onboarding.OnboardingScreen

@Composable
fun FyleShareNavGraph(
    modifier: Modifier, navController: NavHostController, isOnboardingCompleted: Boolean = false
) {
    NavHost(
        navController = navController,
        startDestination = if (isOnboardingCompleted) Destinations.Home else Destinations.Onboarding
    ) {

        composable<Destinations.Home> {
            HomeScreen(modifier = modifier)
        }

        composable<Destinations.Onboarding> {
            OnboardingScreen(
                modifier = modifier, onWelcomeClicked = {
                    navController.navigate(Destinations.Home) {
                        popUpTo(Destinations.Onboarding) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}