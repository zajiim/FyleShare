package com.fyndr.fileshare.data.datamanager

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fyndr.fileshare.data.naming.local.dao.UserNameDAO
import com.fyndr.fileshare.data.naming.local.entity.UserNameEntity

@Database(entities = [UserNameEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userNameDao(): UserNameDAO
}