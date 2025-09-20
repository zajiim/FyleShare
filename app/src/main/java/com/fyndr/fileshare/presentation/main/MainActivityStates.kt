package com.fyndr.fileshare.presentation.main

data class MainActivityStates(
    val isOnboardingCompleted: Boolean = false,
    val isNameSaved: Boolean = false,
    val isLoading: Boolean = true
)