package com.fyndr.fileshare.domain.naming.use_case

import com.fyndr.fileshare.domain.naming.repository.UserRepository

class GetUserNameUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): String? {
        return repository.getUserName()
    }
}