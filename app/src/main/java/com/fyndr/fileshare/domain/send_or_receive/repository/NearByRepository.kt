package com.fyndr.fileshare.domain.send_or_receive.repository

import com.fyndr.fileshare.domain.send_or_receive.models.ConnectionState
import com.fyndr.fileshare.domain.send_or_receive.models.DiscoveredDevice
import com.fyndr.fileshare.domain.send_or_receive.models.FileTransferInfo
import com.fyndr.fileshare.domain.send_or_receive.models.TransferProgress
import kotlinx.coroutines.flow.Flow

interface NearByRepository {
    val discoveredDevices: Flow<List<DiscoveredDevice>>
    val connectionState: Flow<ConnectionState>
    val transferProgress: Flow<TransferProgress?>

    suspend fun startAdvertising(serviceId: String, deviceName: String)
    suspend fun stopAdvertising()

    suspend fun startDiscovery(serviceId: String)
    suspend fun stopDiscovery()

    suspend fun connectToDevice(endpointId: String)
    suspend fun disconnect()

    suspend fun sendFile(fileTransferInfo: FileTransferInfo, endpointId: String)
    suspend fun acceptConnection(endpointId: String)
    suspend fun rejectConnection(endpointId: String)

    suspend fun requestPermissions(): Boolean
}