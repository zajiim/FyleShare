package com.fyndr.fileshare.domain.naming.use_case

import com.fyndr.fileshare.domain.naming.repository.UserRepository

class SaveUserNameUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(name: String) {
        repository.saveUserName(name)
    }
}