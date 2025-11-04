package com.fyndr.fileshare.presentation.send_or_receive

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fyndr.fileshare.domain.send_or_receive.models.ConnectionState
import com.fyndr.fileshare.domain.send_or_receive.use_cases.GetUserDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SendOrReceiveViewModel @Inject constructor(
    private val userDetailsUseCase: GetUserDetailsUseCase,
    private val nearbyUseCases: NearbyUseCases,
    private val transferUseCases: TransferUseCases,
    private val nearbyRepository: NearbyRepository
): ViewModel() {
    private val _state = MutableStateFlow(SendOrReceiveScreenStates())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            userDetailsUseCase().let {
                _state.value = _state.value.copy(currentUserName = it ?: "User")
            }

            checkPermissions()

            observeNearbyRepository()

        }
    }

    private suspend fun checkPermissions() {
        val hasPermissions = nearbyUseCases.requestPermissions()
        _state.value = _state.value.copy(permissionsGranted = hasPermissions)
    }

    private fun observeNearbyRepository() {
        viewModelScope.launch {
            combine(
                nearbyRepository.discoveredDevices,
                nearbyRepository.connectionState,
                nearbyRepository.transferProgress
            ) { devices, connectionState, transferProgress ->
                _state.value = _state.value.copy(
                    discoveredDevices = devices,
                    connectionState = connectionState,
                    currentTransfer = transferProgress,
                    isAdvertising = connectionState == ConnectionState.ADVERTISING,
                    isDiscovering = connectionState == ConnectionState.DISCOVERING
                )
            }
        }
    }

    fun onEvent(events: SendOrReceiveEvents) {
        when(events) {
            SendOrReceiveEvents.OnReceiverClick -> {
                print("Receiver Clicked")
            }

            SendOrReceiveEvents.OnClearError -> TODO()
            is SendOrReceiveEvents.OnDeviceSelected -> TODO()
            SendOrReceiveEvents.OnDisconnect -> TODO()
            is SendOrReceiveEvents.OnFilesSelected -> TODO()
            SendOrReceiveEvents.OnHideDeviceList -> TODO()
            SendOrReceiveEvents.OnHideFilePicker -> TODO()
            is SendOrReceiveEvents.OnRemoveFile -> TODO()
            SendOrReceiveEvents.OnRequestPermissions -> TODO()
            is SendOrReceiveEvents.OnSendFile -> TODO()
            SendOrReceiveEvents.OnSenderClick -> TODO()
            SendOrReceiveEvents.OnShowDeviceList -> TODO()
            SendOrReceiveEvents.OnShowFilePicker -> TODO()
            SendOrReceiveEvents.OnStartAdvertising -> TODO()
            SendOrReceiveEvents.OnStartDiscovery -> TODO()
            SendOrReceiveEvents.OnStopAdvertising -> TODO()
            SendOrReceiveEvents.OnStopDiscovery -> TODO()
        }

    }


    private fun startAdvertising() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, errorMessage = null)
                nearbyUseCases.startAdvertising(_state.value.currentUserName)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to start advertising: ${e.message}"
                )
            }
        }
    }

    private fun stopAdvertising() {
        viewModelScope.launch {
            try {
                nearbyUseCases.stopAdvertising()
                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = "Failed to stop advertising: ${e.message}"
                )
            }
        }
    }

    private fun startDiscovery() {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, errorMessage = null)
                nearbyUseCases.startDiscovery()
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to start discovery: ${e.message}"
                )
            }
        }
    }

    private fun stopDiscovery() {
        viewModelScope.launch {
            try {
                nearbyUseCases.stopDiscovery()
                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = "Failed to stop discovery: ${e.message}"
                )
            }
        }
    }

    private fun connectToDevice(device: com.fyndr.fileshare.domain.nearby.models.DiscoveredDevice) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, errorMessage = null)
                nearbyUseCases.connectToDevice(device.endpointId)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to connect to device: ${e.message}"
                )
            }
        }
    }

    private fun disconnect() {
        viewModelScope.launch {
            try {
                nearbyUseCases.disconnect()
                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    errorMessage = "Failed to disconnect: ${e.message}"
                )
            }
        }
    }

    private fun sendFile(device: com.fyndr.fileshare.domain.nearby.models.DiscoveredDevice, file: com.fyndr.fileshare.domain.nearby.models.FileTransferInfo) {
        viewModelScope.launch {
            try {
                _state.value = _state.value.copy(isLoading = true, errorMessage = null)

                // Save transfer to database
                val transferEntity = FileTransferEntity(
                    transferId = file.transferId,
                    fileName = file.fileName,
                    fileSize = file.fileSize,
                    filePath = file.fileUri.toString(),
                    mimeType = file.mimeType,
                    deviceName = device.deviceName,
                    deviceId = device.endpointId,
                    transferState = TransferState.PENDING,
                    isIncoming = false,
                    timestamp = System.currentTimeMillis()
                )
                transferUseCases.saveTransfer(transferEntity)

                // Send file
                nearbyUseCases.sendFile(file, device.endpointId)

                _state.value = _state.value.copy(isLoading = false)
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    errorMessage = "Failed to send file: ${e.message}"
                )
            }
        }
    }

    private fun removeFile(file: com.fyndr.fileshare.domain.nearby.models.FileTransferInfo) {
        val currentFiles = _state.value.selectedFiles.toMutableList()
        currentFiles.remove(file)
        _state.value = _state.value.copy(selectedFiles = currentFiles)
    }

    private fun requestPermissions() {
        viewModelScope.launch {
            checkPermissions()
        }
    }


}