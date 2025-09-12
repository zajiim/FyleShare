package com.fyndr.fileshare.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class Destinations() {
    @Serializable
    object Home : Destinations()

    @Serializable
    object Onboarding : Destinations()
}