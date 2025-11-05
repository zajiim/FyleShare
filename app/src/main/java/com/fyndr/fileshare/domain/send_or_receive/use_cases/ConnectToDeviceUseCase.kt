package com.fyndr.fileshare.domain.send_or_receive.use_cases

import com.fyndr.fileshare.domain.send_or_receive.repository.NearByRepository
import javax.inject.Inject

class ConnectToDeviceUseCase @Inject constructor(
    private val nearByRepository: NearByRepository
) {
    suspend operator fun invoke(endpointId: String) {
        nearByRepository.connectToDevice(endpointId = endpointId)
    }
}