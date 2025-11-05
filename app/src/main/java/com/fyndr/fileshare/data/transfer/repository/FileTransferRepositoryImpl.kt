package com.fyndr.fileshare.data.transfer.repository

import androidx.compose.foundation.text.input.rememberTextFieldState
import com.fyndr.fileshare.data.transfer.local.dao.FileTransferDAO
import com.fyndr.fileshare.data.transfer.local.entity.FileTransferEntity
import com.fyndr.fileshare.domain.send_or_receive.models.TransferState
import com.fyndr.fileshare.domain.transfer.repository.FileTransferRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FileTransferRepositoryImpl @Inject constructor(
    private val fileTransferDao: FileTransferDAO
): FileTransferRepository {
    override fun getAllTransfers(): Flow<List<FileTransferEntity>> {
        return fileTransferDao.getAllTransfers()
    }

    override suspend fun getTransferById(transferId: String): FileTransferEntity? {
        return fileTransferDao.getTransferById(transferId = transferId)
    }

    override fun getTransfersByDirection(isIncoming: Boolean): Flow<List<FileTransferEntity>> {
        return fileTransferDao.getTransfersByDirection(isIncoming = isIncoming)
    }

    override fun getTransfersByState(state: TransferState): Flow<List<FileTransferEntity>> {
        return fileTransferDao.getTransfersByState(state = state)
    }

    override suspend fun insertTransfer(transfer: FileTransferEntity) {
        fileTransferDao.insertTransfer(transfer = transfer)
    }

    override suspend fun updateTransfer(transfer: FileTransferEntity) {
        fileTransferDao.updateTransfer(transfer = transfer)
    }

    override suspend fun deleteTransfer(transfer: FileTransferEntity) {
        fileTransferDao.deleteTransfer(transfer = transfer)
    }

    override suspend fun deleteTransferById(transferId: String) {
        fileTransferDao.deleteTransferById(transferId = transferId)
    }

    override suspend fun deleteAllTransfers() {
        fileTransferDao.deleteAllTransfers()
    }

    override suspend fun getTransferCountByState(state: TransferState): Int {
        return fileTransferDao.getTransferCountByState(state = state)
    }
}