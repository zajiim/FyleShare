package com.fyndr.fileshare.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fyndr.fileshare.presentation.home.HomeScreen

@Composable
fun FyleShareNavGraph(
    modifier: Modifier,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Destinations.Home
    ) {
        composable<Destinations.Home> {
            HomeScreen(modifier = modifier)
        }
    }

}