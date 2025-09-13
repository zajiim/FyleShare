package com.fyndr.fileshare.domain.use_cases.onboarding

import com.fyndr.fileshare.domain.datamanager.LocalUserManager

class SaveOnboardingUseCase(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(onBoardingValue: Boolean) {
        localUserManager.saveOnBoardingState(onBoardingValue)
    }
}