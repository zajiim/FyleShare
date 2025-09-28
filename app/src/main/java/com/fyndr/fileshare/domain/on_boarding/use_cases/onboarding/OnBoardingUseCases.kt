package com.fyndr.fileshare.domain.on_boarding.use_cases.onboarding

import com.fyndr.fileshare.domain.naming.use_case.ReadNameStateUseCase
import com.fyndr.fileshare.domain.naming.use_case.SaveNameStateUseCase

data class OnBoardingUseCases(
    val readOnboardingUseCase: ReadOnboardingUseCase,
    val saveOnboardingUseCase: SaveOnboardingUseCase,
    val readNameStateUseCase: ReadNameStateUseCase,
    val saveNameStateUseCase: SaveNameStateUseCase
)
