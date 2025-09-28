package com.fyndr.fileshare.domain.send_or_receive.repository

interface UserRepository {
    suspend fun getUserDetails(): String?
}