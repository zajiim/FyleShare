package com.fyndr.fileshare.presentation.name_screen

sealed class NameScreenEvents {
    data class OnNameChange(val name: String) : NameScreenEvents()
    object OnSubmitClick : NameScreenEvents()
    object OnAnimationComplete: NameScreenEvents()
    object OnNavigationDone : NameScreenEvents()
}
