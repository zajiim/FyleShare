package com.fyndr.fileshare.data.transfer.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.fyndr.fileshare.data.transfer.local.entity.FileTransferEntity
import com.fyndr.fileshare.domain.send_or_receive.models.TransferState
import kotlinx.coroutines.flow.Flow

@Dao
interface FileTransferDAO {

    @Query("SELECT * FROM file_transfers ORDER BY timestamp DESC")
    fun getAllTransfers(): Flow<List<FileTransferEntity>>

    @Query("SELECT * FROM file_transfers WHERE transferId = :transferId")
    suspend fun getTransferById(transferId: String): FileTransferEntity?

    @Query("SELECT * FROM file_transfers WHERE isIncoming = :isIncoming ORDER BY timestamp DESC")
    fun getTransfersByDirection(isIncoming: Boolean): Flow<List<FileTransferEntity>>

    @Query("SELECT * FROM file_transfers WHERE transferState = :state ORDER BY timestamp DESC")
    fun getTransfersByState(state: TransferState): Flow<List<FileTransferEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransfer(transfer: FileTransferEntity)

    @Update
    suspend fun updateTransfer(transfer: FileTransferEntity)

    @Delete
    suspend fun deleteTransfer(transfer: FileTransferEntity)

    @Query("DELETE FROM file_transfers WHERE transferId = :transferId")
    suspend fun deleteTransferById(transferId: String)

    @Query("DELETE FROM file_transfers")
    suspend fun deleteAllTransfers()

    @Query("SELECT COUNT(*) FROM file_transfers WHERE transferState = :state")
    suspend fun getTransferCountByState(state: TransferState): Int

}