package com.fyndr.fileshare.domain.transfer.use_cases

import com.fyndr.fileshare.data.transfer.local.entity.FileTransferEntity
import com.fyndr.fileshare.domain.transfer.repository.FileTransferRepository
import kotlinx.coroutines.flow.Flow

class GetFileTransferHistoryUseCase(
    private val fileTransferRepository: FileTransferRepository
) {
    operator fun invoke(): Flow<List<FileTransferEntity>> {
        return fileTransferRepository.getAllTransfers()
    }
}