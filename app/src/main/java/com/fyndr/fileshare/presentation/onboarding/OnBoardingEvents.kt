package com.fyndr.fileshare.presentation.onboarding

sealed class OnBoardingEvents {
    data object SaveOnBoardingEvent : OnBoardingEvents()
}