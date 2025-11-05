package com.fyndr.fileshare.domain.send_or_receive.use_cases

import com.fyndr.fileshare.domain.send_or_receive.models.FileTransferInfo
import javax.inject.Inject

data class NearbyUseCases @Inject constructor(
    val startAdvertisingUseCase: StartAdvertisingUseCase,
    val stopAdvertisingUseCase: StopAdvertisingUseCase,
    val startDiscoveryUseCase: StartDiscoveryUseCase,
    val stopDiscoveryUseCase: StopDiscoveryUseCase,
    val connectToDeviceUseCase: ConnectToDeviceUseCase,
    val disconnectUseCase: DisconnectUseCase,
    val sendFileUseCase: SendFileUseCase,
    val requestPermissionsUseCase: RequestPermissionsUseCase
) {
    suspend fun startAdvertising(deviceName: String) = startAdvertisingUseCase(deviceName)
    suspend fun stopAdvertising() = stopAdvertisingUseCase()
    suspend fun startDiscovery() = startDiscoveryUseCase()
    suspend fun stopDiscovery() = stopDiscoveryUseCase()
    suspend fun connectToDevice(endpointId: String) = connectToDeviceUseCase(endpointId)
    suspend fun disconnect() = disconnectUseCase()
    suspend fun sendFile(fileTransferInfo: FileTransferInfo, endpointId: String) = sendFileUseCase(fileTransferInfo, endpointId)
    suspend fun requestPermissions(): Boolean = requestPermissionsUseCase()
}
