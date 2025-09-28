package com.fyndr.fileshare.domain.naming.use_case

import com.fyndr.fileshare.domain.datamanager.LocalUserManager

class SaveNameStateUseCase(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(nameSaved: Boolean) {
        localUserManager.saveNameState(nameSaved)
    }
}