package com.fyndr.fileshare.presentation.send_or_receive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyndr.fileshare.domain.send_or_receive.use_cases.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendOrReceiveViewModel @Inject constructor(
    private val userDetailsUseCase: GetUserDetailsUseCase
): ViewModel() {
    private val _state = MutableStateFlow(SendOrReceiveScreenStates())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            userDetailsUseCase().let {
                _state.value = _state.value.copy(currentUserName = it ?: "User")
            }
        }
    }

    fun onEvent(events: SendOrReceiveEvents) {
        when(events) {
            SendOrReceiveEvents.OnReceiverClick -> {
                print("Receiver Clicked")
            }
        }

    }


}