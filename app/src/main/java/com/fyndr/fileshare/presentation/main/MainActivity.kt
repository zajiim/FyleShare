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
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.rememberNavController
import com.fyndr.fileshare.presentation.navigation.FyleShareNavGraph
import com.fyndr.fileshare.ui.theme.FileShareTheme

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
                    val isOnboardingCompleted = mainActivityViewModel.state.collectAsState()
                    FyleShareNavGraph(
                        modifier = Modifier.Companion.padding(innerPadding),
                        navController = navController,
                        isOnboardingCompleted = isOnboardingCompleted.value.isOnboardingCompleted
                    )
                }
            }
        }
    }
}
