package com.fyndr.fileshare.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyndr.fileshare.domain.datamanager.LocalUserManager
import com.fyndr.fileshare.domain.naming.use_case.ReadNameStateUseCase
import com.fyndr.fileshare.domain.on_boarding.use_cases.onboarding.OnBoardingUseCases
import com.fyndr.fileshare.domain.on_boarding.use_cases.onboarding.ReadOnboardingUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val onboardingUseCase: OnBoardingUseCases
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityStates())
    val state: StateFlow<MainActivityStates> = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            combine(
                onboardingUseCase.readOnboardingUseCase(),
                onboardingUseCase.readNameStateUseCase()
            ) { onboardingCompleted, nameSaved ->
                Pair(onboardingCompleted, nameSaved)
            }.collect { (onboardingCompleted, nameSaved) ->
                _state.value = _state.value.copy(
                    isOnboardingCompleted = onboardingCompleted,
                    isNameSaved = nameSaved,
                    isLoading = false
                )
            }
        }

        onboardingUseCase.readOnboardingUseCase().onEach { completed ->
            _state.value = _state.value.copy(isOnboardingCompleted = completed, isLoading = false)
        }.launchIn(viewModelScope)
    }
}