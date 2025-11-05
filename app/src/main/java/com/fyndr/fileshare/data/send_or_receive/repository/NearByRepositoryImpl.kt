package com.fyndr.fileshare.data.send_or_receive.repository

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import com.fyndr.fileshare.domain.send_or_receive.models.ConnectionState
import com.fyndr.fileshare.domain.send_or_receive.models.DiscoveredDevice
import com.fyndr.fileshare.domain.send_or_receive.models.FileTransferInfo
import com.fyndr.fileshare.domain.send_or_receive.models.TransferProgress
import com.fyndr.fileshare.domain.send_or_receive.repository.NearByRepository
import com.google.android.gms.nearby.Nearby
import com.google.android.gms.nearby.connection.*
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.suspendCancellableCoroutine
import java.io.File
import javax.inject.Inject
import kotlin.coroutines.resume


private const val TAG = "NearByRepositoryImpl"
class NearByRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): NearByRepository {

    private val connectionsClient: ConnectionsClient = Nearby.getConnectionsClient(context)
    private val serviceId = "com.fyndr.fileshare.SERVICE_ID"



    private val _discoveredDevices = MutableStateFlow<List<DiscoveredDevice>>(emptyList())
    private val _connectionState = MutableStateFlow(ConnectionState.DISCONNECTED)
    private val _transferProgress = MutableStateFlow<Map<String, TransferProgress>>(emptyMap())
    private val _currentTransfer = MutableStateFlow<TransferProgress?>(null)

    override val discoveredDevices: Flow<List<DiscoveredDevice>> = _discoveredDevices.asStateFlow()
    override val connectionState: Flow<ConnectionState> = _connectionState.asStateFlow()
    override val transferProgress: Flow<TransferProgress?> = _currentTransfer.asStateFlow()

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

        override fun onDisconnected(endpointId: String) {
            Log.d(TAG, "Disconnected from $endpointId")
            _connectionState.value = ConnectionState.DISCONNECTED
            updateDeviceConnectionStatus(endpointId, false)
        }

    }

    private val endpointDiscoveryCallback = object: EndpointDiscoveryCallback() {
        override fun onEndpointFound(
            endpointId: String,
            discoveredEndpointInfo: DiscoveredEndpointInfo
        ) {
            Log.d(TAG, "Found endpoint: ${discoveredEndpointInfo.endpointName}")
            val device = DiscoveredDevice(
                endpointId = endpointId,
                deviceName = discoveredEndpointInfo.endpointName,
                serviceId = discoveredEndpointInfo.serviceId
            )
            val currentDevices = _discoveredDevices.value.toMutableList()
            if (!currentDevices.any{ it.endpointId == endpointId }) {
                currentDevices.add(device)
                _discoveredDevices.value = currentDevices
            }
        }

        override fun onEndpointLost(endpointId: String) {
            Log.d(TAG, "Lost endpoint: $endpointId")
            val currentDevices = _discoveredDevices.value.toMutableList()
            currentDevices.removeAll { it.endpointId == endpointId }
            _discoveredDevices.value = currentDevices
        }

    }

    override suspend fun startAdvertising(serviceId: String, deviceName: String) {
        if (!hasRequiredPermissions()) {
            throw SecurityException("Required permissions not granted")
        }

        val advertisingOptions = AdvertisingOptions.Builder()
            .setStrategy(Strategy.P2P_STAR)
            .build()

        suspendCancellableCoroutine { continuation ->
            connectionsClient.startAdvertising(
                deviceName,
                serviceId,
                connectionLifecycleCallback,
                advertisingOptions
            ).addOnSuccessListener {
                Log.d(TAG, "Started advertising")
                _connectionState.value = ConnectionState.ADVERTISING
                continuation.resume(Unit)
            }.addOnFailureListener { e ->
                Log.e(TAG, "Failed to start advertising", e)
                _connectionState.value = ConnectionState.ERROR
                continuation.resume(Unit)
            }
        }
    }

    override suspend fun stopAdvertising() {
        connectionsClient.stopAdvertising()
        _connectionState.value = ConnectionState.DISCONNECTED
        Log.d(TAG, "Stopped advertising")
    }

    override suspend fun startDiscovery(serviceId: String) {
        if (!hasRequiredPermissions()) {
            throw SecurityException("Required permissions not granted")
        }
        val discoveryOptions = DiscoveryOptions.Builder()
            .setStrategy(Strategy.P2P_STAR)
            .build()

        suspendCancellableCoroutine { continuation ->
            connectionsClient.startDiscovery(
                serviceId,
                endpointDiscoveryCallback,
                discoveryOptions
            ).addOnSuccessListener {
                Log.d(TAG, "Started discovery")
                _connectionState.value = ConnectionState.DISCOVERING
                continuation.resume(Unit)
            }.addOnFailureListener { e ->
                Log.e(TAG, "Failed to start discovery", e)
                _connectionState.value = ConnectionState.ERROR
                continuation.resume(Unit)
            }
        }
    }

    override suspend fun stopDiscovery() {
        connectionsClient.stopDiscovery()
        _connectionState.value = ConnectionState.DISCONNECTED
        Log.d(TAG, "Stopped discovery")
    }

    override suspend fun connectToDevice(endpointId: String) {
        suspendCancellableCoroutine { continuation ->
            connectionsClient.requestConnection(
                "FileShare User",
                endpointId,
                connectionLifecycleCallback
            ).addOnSuccessListener {
                Log.d(TAG, "Connection request sent to $endpointId")
                continuation.resume(Unit)
            }.addOnFailureListener { e ->
                Log.e(TAG, "Failed to request connection to $endpointId", e)
                continuation.resume(Unit)
            }
        }
    }


//    override suspend fun connectToDevice(endpointId: String) {
//        val connectionOptions = ConnectionOptions.Builder()
//            .setPayloadCallback(payloadCallback)
//            .build()
//
//        connectionsClient.requestConnection(
//            "FileShare User",
//            endpointId,
//            connectionLifecycleCallback
//        ).addOnSuccessListener {
//            Log.d(TAG, "Connection request sent to $endpointId")
//        }.addOnFailureListener { e ->
//            Log.e(TAG, "Failed to request connection to $endpointId", e)
//        }
//    }

    override suspend fun disconnect() {
        connectionsClient.stopAllEndpoints()
        Log.d(TAG, "Disconnected from all endpoints")
    }
    override suspend fun sendFile(
        fileTransferInfo: FileTransferInfo,
        endpointId: String
    ) {
        val file = File(fileTransferInfo.fileUri.path!!)
//        val payload = Payload.fromFile(fileTransferInfo.fileUri)
        val payload = Payload.fromFile(file)
        connectionsClient.sendPayload(endpointId, payload)

        val progress = TransferProgress(
            transferId = fileTransferInfo.transferId,
            fileName = fileTransferInfo.fileName,
            bytesTransferred = 0,
            totalBytes = fileTransferInfo.fileSize
        )
        val currentTransfers = _transferProgress.value.toMutableMap()
        currentTransfers[fileTransferInfo.transferId] = progress
        _transferProgress.value = currentTransfers
    }

    override suspend fun acceptConnection(endpointId: String) {
        connectionsClient.acceptConnection(endpointId, payloadCallback)
    }

    override suspend fun rejectConnection(endpointId: String) {
        connectionsClient.rejectConnection(endpointId)
    }

    override suspend fun requestPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val missingPermissions = permissions.filter {
            ActivityCompat.checkSelfPermission(context, it) != PackageManager.PERMISSION_GRANTED
        }

        return missingPermissions.isEmpty()
    }


    private fun hasRequiredPermissions(): Boolean {
        val permissions = arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_WIFI_STATE,
            Manifest.permission.CHANGE_WIFI_STATE,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        return permissions.all {
            ActivityCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }



    private val payloadCallback = object: PayloadCallback() {
        override fun onPayloadReceived(
            endpointId: String,
            payload: Payload
        ) {
            when(payload.type) {
                Payload.Type.BYTES -> {
                    Log.d(TAG, "Received bytes from $endpointId")
                }

                Payload.Type.FILE -> {
                    Log.d(TAG, "Received files from $endpointId")
                }

                Payload.Type.STREAM -> {
                    Log.d(TAG, "Received stream from $endpointId")
                }
            }
        }

        override fun onPayloadTransferUpdate(
            endpointId: String,
            update: PayloadTransferUpdate
        ) {
            when(update.status) {
                PayloadTransferUpdate.Status.IN_PROGRESS -> {
                    val progress = TransferProgress(
                        transferId = update.payloadId.toString(),
                        fileName = "Unknown",
                        bytesTransferred = update.bytesTransferred,
                        totalBytes = update.totalBytes
                    )
                    val currentTransfers = _transferProgress.value.toMutableMap()
                    currentTransfers[update.payloadId.toString()] = progress
                    _transferProgress.value = currentTransfers
                    _currentTransfer.value = progress
                }
                PayloadTransferUpdate.Status.SUCCESS -> {
                    val currentTransfers = _transferProgress.value.toMutableMap()
                    val progress = currentTransfers[update.payloadId.toString()]?.copy(
                        isCompleted = true
                    )
                    if (progress != null) {
                        currentTransfers[update.payloadId.toString()] = progress
                        _transferProgress.value = currentTransfers
                        _currentTransfer.value = progress
                    }
                }
                PayloadTransferUpdate.Status.FAILURE -> {
                    val currentTransfers = _transferProgress.value.toMutableMap()
                    val progress = currentTransfers[update.payloadId.toString()]?.copy(
                        isFailed = true,
                        error = "Transfer failed"
                    )
                    if (progress != null) {
                        currentTransfers[update.payloadId.toString()] = progress
                        _transferProgress.value = currentTransfers
                        _currentTransfer.value = progress
                    }
                }
            }
        }

    }

    private fun updateDeviceConnectionStatus(endpointId: String, isConnected: Boolean) {
        val currentDevices = _discoveredDevices.value.toMutableList()
        val index = currentDevices.indexOfFirst { it.endpointId == endpointId }
        if (index != -1) {
            currentDevices[index] = currentDevices[index].copy(isConnected = isConnected)
            _discoveredDevices.value = currentDevices
        }
    }
}