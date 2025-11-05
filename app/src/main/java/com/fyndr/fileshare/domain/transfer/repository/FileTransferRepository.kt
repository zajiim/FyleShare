package com.fyndr.fileshare.domain.transfer.repository

import com.fyndr.fileshare.data.transfer.local.entity.FileTransferEntity
import com.fyndr.fileshare.domain.send_or_receive.models.TransferState
import kotlinx.coroutines.flow.Flow

interface FileTransferRepository {
    fun getAllTransfers(): Flow<List<FileTransferEntity>>
    suspend fun getTransferById(transferId: String): FileTransferEntity?
    fun getTransfersByDirection(isIncoming: Boolean): Flow<List<FileTransferEntity>>
    fun getTransfersByState(state: TransferState): Flow<List<FileTransferEntity>>
    suspend fun insertTransfer(transfer: FileTransferEntity)
    suspend fun updateTransfer(transfer: FileTransferEntity)
    suspend fun deleteTransfer(transfer: FileTransferEntity)
    suspend fun deleteTransferById(transferId: String)
    suspend fun deleteAllTransfers()
    suspend fun getTransferCountByState(state: TransferState): Int
}
