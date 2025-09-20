package com.fyndr.fileshare.data.naming.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fyndr.fileshare.data.naming.local.entity.UserNameEntity

@Dao
interface UserNameDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertName(name: UserNameEntity)

    @Query("SELECT * FROM user_name_table LIMIT 1")
    suspend fun getName(): UserNameEntity?
}