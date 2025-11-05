package com.fyndr.fileshare.domain.send_or_receive.use_cases

import com.fyndr.fileshare.domain.send_or_receive.repository.NearByRepository
import javax.inject.Inject

class StartDiscoveryUseCase @Inject constructor(
    private val nearbyRepository: NearByRepository
) {
    suspend operator fun invoke() {
        nearbyRepository.startDiscovery("com.fyndr.fileshare.SERVICE_ID")
    }
}