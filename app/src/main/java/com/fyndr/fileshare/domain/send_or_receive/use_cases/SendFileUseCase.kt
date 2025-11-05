package com.fyndr.fileshare.domain.send_or_receive.use_cases

import com.fyndr.fileshare.domain.send_or_receive.models.FileTransferInfo
import com.fyndr.fileshare.domain.send_or_receive.repository.NearByRepository
import javax.inject.Inject

class SendFileUseCase @Inject constructor(
    private val nearbyRepository: NearByRepository
) {
    suspend operator fun invoke(fileTransferInfo: FileTransferInfo, endpointId: String) {
        nearbyRepository.sendFile(fileTransferInfo, endpointId)
    }
}
