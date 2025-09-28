package com.fyndr.fileshare.data.send_or_receive.repository

import com.fyndr.fileshare.data.naming.local.dao.UserNameDAO
import com.fyndr.fileshare.domain.send_or_receive.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userNameDAO: UserNameDAO
): UserRepository {
    override suspend fun getUserDetails(): String? {
        return userNameDAO.getName()?.name
    }
}