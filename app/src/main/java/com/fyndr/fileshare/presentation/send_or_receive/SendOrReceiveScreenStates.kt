package com.fyndr.fileshare.presentation.send_or_receive

import com.fyndr.fileshare.domain.send_or_receive.models.ConnectionState
import com.fyndr.fileshare.domain.send_or_receive.models.DiscoveredDevice
import com.fyndr.fileshare.domain.send_or_receive.models.FileTransferInfo
import com.fyndr.fileshare.domain.send_or_receive.models.TransferProgress

data class SendOrReceiveScreenStates(
    val currentUserName: String = "",
    val isLoading: Boolean = false,
    val connectionState: ConnectionState = ConnectionState.DISCONNECTED,
    val discoveredDevices: List<DiscoveredDevice> = emptyList(),
    val selectedFiles: List<FileTransferInfo> = emptyList(),
    val currentTransfer: TransferProgress? = null,
    val isAdvertising: Boolean = false,
    val isDiscovering: Boolean = false,
    val errorMessage: String? = null,
    val showFilePicker: Boolean = false,
    val showDeviceList: Boolean = false,
    val permissionsGranted: Boolean = false
)
