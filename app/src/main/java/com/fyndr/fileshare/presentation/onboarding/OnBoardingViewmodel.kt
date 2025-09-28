package com.fyndr.fileshare.presentation.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyndr.fileshare.domain.on_boarding.use_cases.onboarding.OnBoardingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class OnBoardingViewmodel @Inject constructor(
    private val onBoardingUseCases: OnBoardingUseCases
): ViewModel() {

    private val _state = MutableStateFlow(OnBoardingStates())
    val state = _state.asStateFlow()

    init {
        onBoardingUseCases.readOnboardingUseCase().onEach { completed ->
            _state.value = _state.value.copy(
                isOnboardingCompleted = completed
            )
        }.launchIn(viewModelScope)
    }

    fun onEvent(events: OnBoardingEvents) {
        when(events) {
            is OnBoardingEvents.SaveOnBoardingEvent -> saveOnBoarding(true)
        }
    }


    private fun saveOnBoarding(value: Boolean) {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)
            onBoardingUseCases.saveOnboardingUseCase(value)
            _state.value = _state.value.copy(isLoading = false, isOnboardingCompleted = value)
        }
    }


}