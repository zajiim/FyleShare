package com.fyndr.fileshare.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyndr.fileshare.domain.datamanager.LocalUserManager
import com.fyndr.fileshare.domain.use_cases.onboarding.ReadOnboardingUseCase
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
    readOnboardingUseCase: ReadOnboardingUseCase,
    private val localUserManager: LocalUserManager
) : ViewModel() {

    private val _state = MutableStateFlow(MainActivityStates())
    val state: StateFlow<MainActivityStates> = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(isLoading = true)

        viewModelScope.launch {
            combine(
                readOnboardingUseCase(),
                localUserManager.readNameState()
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

        readOnboardingUseCase().onEach { completed ->
            _state.value = _state.value.copy(isOnboardingCompleted = completed, isLoading = false)
        }.launchIn(viewModelScope)
    }
}