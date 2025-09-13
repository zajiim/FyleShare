package com.fyndr.fileshare.domain.use_cases.onboarding

import com.fyndr.fileshare.domain.datamanager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadOnboardingUseCase(
    private val localUserManager: LocalUserManager
    ) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readOnBoardingState()
    }
}