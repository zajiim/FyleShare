package com.fyndr.fileshare.presentation.name_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

private const val TAG = "NameScreenViewModel"

@HiltViewModel
class NameScreenViewModel @Inject constructor(

): ViewModel() {

    private val _state: MutableStateFlow<NameScreenStates> = MutableStateFlow(NameScreenStates())
    val state = _state.asStateFlow()

    fun onEvent(event: NameScreenEvents) {
        when(event) {
            is NameScreenEvents.OnNameChange -> {
                _state.value = state.value.copy(name = event.name)
            }
            NameScreenEvents.OnSubmitClick -> {
                Log.e(TAG, "submit click invoked: ", )
            }
        }
    }
}