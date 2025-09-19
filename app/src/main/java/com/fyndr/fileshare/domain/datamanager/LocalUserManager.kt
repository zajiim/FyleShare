package com.fyndr.fileshare.domain.datamanager

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {
    suspend fun saveOnBoardingState(value: Boolean)

    fun readOnBoardingState(): Flow<Boolean>



}