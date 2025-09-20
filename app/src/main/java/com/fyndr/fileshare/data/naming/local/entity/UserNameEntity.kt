package com.fyndr.fileshare.data.naming.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_name_table")
data class UserNameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String
)
