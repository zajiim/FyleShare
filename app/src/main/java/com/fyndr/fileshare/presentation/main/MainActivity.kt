package com.fyndr.fileshare.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.fyndr.fileshare.presentation.main.components.LoadingScreen
import com.fyndr.fileshare.presentation.navigation.FyleShareNavGraph
import com.fyndr.fileshare.ui.theme.FileShareTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val mainActivityViewModel: MainActivityViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FileShareTheme {
                Scaffold(
                    modifier = Modifier.Companion.fillMaxSize()
                ) { innerPadding ->
                    val navController = rememberNavController()
                    val state by mainActivityViewModel.state.collectAsState()
                    if (state.isLoading) {
                        LoadingScreen(modifier = Modifier.padding(innerPadding))
                    } else {
                        FyleShareNavGraph(
                            modifier = Modifier.padding(innerPadding),
                            navController = navController,
                            isOnboardingCompleted = state.isOnboardingCompleted
                        )
                    }
                }
            }
        }
    }
}
