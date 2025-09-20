package com.fyndr.fileshare.presentation.name_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyndr.fileshare.domain.datamanager.LocalUserManager
import com.fyndr.fileshare.domain.naming.use_case.SaveUserNameUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val TAG = "NameScreenViewModel"

@HiltViewModel
class NameScreenViewModel @Inject constructor(
    private val saveUserNameUseCase: SaveUserNameUseCase,
    private val localUserManager: LocalUserManager

): ViewModel() {

    private val _state: MutableStateFlow<NameScreenStates> = MutableStateFlow(NameScreenStates())
    val state = _state.asStateFlow()

    fun onEvent(event: NameScreenEvents) {
        when(event) {
            is NameScreenEvents.OnNameChange -> {
                _state.value = _state.value.copy(name = event.name)
            }
            NameScreenEvents.OnSubmitClick -> {
                if (_state.value.name.length > 2) {
                    _state.value = _state.value.copy(isLoading = true)
                    viewModelScope.launch {
                        saveUserNameUseCase(_state.value.name)
                        localUserManager.saveNameState(true)
                        _state.value = _state.value.copy(isLoading = false, shouldNavigate = true)
                    }
                }
            }

            NameScreenEvents.OnNavigationDone -> {
                _state.value = _state.value.copy(shouldNavigate = false)
            }

            NameScreenEvents.OnAnimationComplete -> {
                _state.value = _state.value.copy(
                    isLoading = false,
                    shouldNavigate = true
                )
            }
        }
    }
}