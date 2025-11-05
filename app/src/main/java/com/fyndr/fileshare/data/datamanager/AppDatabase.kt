package com.fyndr.fileshare.data.datamanager

import androidx.room.Database
import androidx.room.RoomDatabase
import com.fyndr.fileshare.data.naming.local.dao.UserNameDAO
import com.fyndr.fileshare.data.naming.local.entity.UserNameEntity
import com.fyndr.fileshare.data.transfer.local.dao.FileTransferDAO
import com.fyndr.fileshare.data.transfer.local.entity.FileTransferEntity

@Database(entities = [UserNameEntity::class, FileTransferEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun userNameDao(): UserNameDAO
    abstract fun fileTransferDao(): FileTransferDAO

}