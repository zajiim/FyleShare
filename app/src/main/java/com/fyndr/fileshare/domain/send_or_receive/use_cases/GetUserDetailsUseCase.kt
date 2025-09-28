package com.fyndr.fileshare.domain.send_or_receive.use_cases

import com.fyndr.fileshare.domain.send_or_receive.repository.UserRepository


class GetUserDetailsUseCase(
    private val repository: UserRepository
) {
    suspend operator fun invoke(): String? {
        return repository.getUserDetails()
    }
}