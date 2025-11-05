package com.fyndr.fileshare.domain.transfer.use_cases

import com.fyndr.fileshare.data.transfer.local.entity.FileTransferEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

data class FileTransferUseCases @Inject constructor(
    val getFileTransferHistory: GetFileTransferHistoryUseCase,
    val saveFileTransfer: SaveFileTransferHistoryUseCase,
    val updateFileTransfer: UpdateFileTransferUseCase
) {
    fun getTransferHistory(): Flow<List<FileTransferEntity>> = getFileTransferHistory()
    suspend fun saveTransfer(transfer: FileTransferEntity) = saveFileTransfer(transfer)
    suspend fun updateTransfer(transfer: FileTransferEntity) = updateFileTransfer(transfer)
}
