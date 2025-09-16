package com.fyndr.fileshare.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fyndr.fileshare.presentation.home.HomeScreen
import com.fyndr.fileshare.presentation.onboarding.OnboardingScreen
import com.fyndr.fileshare.presentation.send_or_receive.SendOrReceiveScreen

@Composable
fun FyleShareNavGraph(
    modifier: Modifier,
    navController: NavHostController,
    isOnboardingCompleted: Boolean = false,
) {
    NavHost(
        navController = navController,
        startDestination = if (isOnboardingCompleted) Destinations.Home else Destinations.Onboarding,
        enterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(600)
            )
        }, exitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(600)
            )
        }, popEnterTransition = {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(600)
            )
        }, popExitTransition = {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(600)
            )
        }
    ) {

        composable<Destinations.Home> {
            HomeScreen(
                modifier = modifier,
                onSendClick = {
                    navController.navigate(Destinations.SendOrReceive)
                },
                onReceiveClick = {
                    navController.navigate(Destinations.SendOrReceive)
                }
            )
        }

        composable<Destinations.Onboarding> {
            OnboardingScreen(
                modifier = modifier, onWelcomeClicked = {
                    navController.navigate(Destinations.Home) {
                        popUpTo(Destinations.Onboarding) {
                            inclusive = true
                        }
                    }
                })
        }

        composable<Destinations.SendOrReceive> {
            SendOrReceiveScreen(
                modifier = modifier
            )
        }


    }
}