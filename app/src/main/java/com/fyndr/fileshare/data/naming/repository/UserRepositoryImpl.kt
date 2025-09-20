package com.fyndr.fileshare.data.naming.repository

import com.fyndr.fileshare.data.naming.local.dao.UserNameDAO
import com.fyndr.fileshare.data.naming.local.entity.UserNameEntity
import com.fyndr.fileshare.domain.naming.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val dao: UserNameDAO
): UserRepository {
    override suspend fun saveUserName(name: String) {
        dao.insertName(UserNameEntity(name = name))
    }

    override suspend fun getUserName(): String? {
        return dao.getName()?.name
    }
}