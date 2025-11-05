package com.fyndr.fileshare.domain.transfer.use_cases

import com.fyndr.fileshare.data.transfer.local.entity.FileTransferEntity
import com.fyndr.fileshare.domain.transfer.repository.FileTransferRepository
import javax.inject.Inject


class UpdateFileTransferUseCase @Inject constructor(
    private val fileTransferRepository: FileTransferRepository
) {
    suspend operator fun invoke(transfer: FileTransferEntity) {
        fileTransferRepository.updateTransfer(transfer)
    }
}


