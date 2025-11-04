package com.fyndr.fileshare.data.send_or_receive.repository

import android.content.Context
import android.util.Log
import com.fyndr.fileshare.domain.send_or_receive.models.ConnectionState
import com.fyndr.fileshare.domain.send_or_receive.models.DiscoveredDevice
import com.fyndr.fileshare.domain.send_or_receive.models.FileTransferInfo
import com.fyndr.fileshare.domain.send_or_receive.models.TransferProgress
import com.fyndr.fileshare.domain.send_or_receive.repository.NearByRepository
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.ConnectionInfo
import com.google.android.gms.nearby.connection.ConnectionLifecycleCallback
import com.google.android.gms.nearby.connection.ConnectionResolution
import com.google.android.gms.nearby.connection.ConnectionsClient
import com.google.android.gms.nearby.connection.ConnectionsStatusCodes
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject


private const val TAG = "NearByRepositoryImpl"
class NearByRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    override val discoveredDevices: Flow<List<DiscoveredDevice>>,
    override val connectionState: Flow<ConnectionState>,
    override val transferProgress: Flow<TransferProgress>
): NearByRepository {

    private val connectionsClient: ConnectionsClient = Nearby.getConnectionsClient(context)
    private val serviceId = "com.fyndr.fileshare.SERVICE_ID"

    private val _discoveredDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())
    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    private val _transferProgress = MutableStateFlow<Map<String, TransferProgress>>(emptyMap())
    private val _currentTransfer = MutableStateFlow<TransferProgress?>(null)

    private val connectionLifecycleCallback = object: ConnectionLifecycleCallback() {
        override fun onConnectionInitiated(
            endpointId: String, connectionInfo: ConnectionInfo
        ) {
            Log.d(TAG, "Connection initiated with ${connectionInfo.endpointName}")
            _connectionState.value = ConnectionState.CONNECTING
        }

        override fun onConnectionResult(endpointId: String, result: ConnectionResolution) {
            when (result.status.statusCode) {
                ConnectionsStatusCodes.STATUS_OK -> {
                    Log.d(TAG, "Connected to $endpointId")
                    _connectionState.value = ConnectionState.CONNECTED
                    updateDeviceConnectionStatus(endpointId = endpointId, isConnected = true)
                }
                else -> {
                    Log.e(TAG, "Connection failed: ${result.status.statusMessage}")
                    _connectionState.value = ConnectionState.ERROR
                    updateDeviceConnectionStatus(endpointId = endpointId, isConnected = false)
                }
            }
        }

        private fun updateDeviceConnectionStatus(endpointId: String, isConnected: Boolean) {
            TODO("Not yet implemented")
        }

        override fun onDisconnected(endpointId: String) {
            Log.d(TAG, "Disconnected from $endpointId")
            _connectionState.value = ConnectionState.DISCONNECTED
            updateDeviceConnectionStatus(endpointId, false)
        }

    }

    override suspend fun startAdvertising(serviceId: String, deviceName: String) {
        TODO("Not yet implemented")
    }

    override suspend fun stopAdvertising() {
        TODO("Not yet implemented")
    }

    override suspend fun startDiscovery(serviceId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun stopDiscovery() {
        TODO("Not yet implemented")
    }

    override suspend fun connectToDevice(endpointId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun disconnect() {
        TODO("Not yet implemented")
    }

    override suspend fun sendFile(
        fileTransferInfo: FileTransferInfo,
        endpointId: String
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun acceptConnection(endpointId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun rejectConnection(endpointId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun requestPermissions(): Boolean {
        TODO("Not yet implemented")
    }
}