package com.fyndr.fileshare.domain.naming.repository


interface UserRepository {
    suspend fun saveUserName(name: String)
    suspend fun getUserName(): String?
}