package com.fyndr.fileshare.domain.on_boarding.use_cases.onboarding

import com.fyndr.fileshare.domain.datamanager.LocalUserManager

class SaveOnboardingUseCase(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(onBoardingValue: Boolean) {
        localUserManager.saveOnBoardingState(onBoardingValue)
    }
}