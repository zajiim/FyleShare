package com.fyndr.fileshare.presentation.main

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainActivityViewModel: ViewModel() {

    private var _state = MutableStateFlow(MainActivityStates())
    val state: StateFlow<MainActivityStates> = _state.asStateFlow()



}