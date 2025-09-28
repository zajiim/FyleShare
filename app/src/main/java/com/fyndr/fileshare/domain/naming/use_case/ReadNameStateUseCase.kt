package com.fyndr.fileshare.domain.naming.use_case

import com.fyndr.fileshare.domain.datamanager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadNameStateUseCase(
    private val localUserManager: LocalUserManager
) {
    operator fun invoke(): Flow<Boolean> {
        return localUserManager.readNameState()
    }
}