package com.fyndr.fileshare.presentation.send_or_receive

import com.fyndr.fileshare.domain.send_or_receive.models.DiscoveredDevice
import com.fyndr.fileshare.domain.send_or_receive.models.FileTransferInfo

sealed class SendOrReceiveEvents {
    object OnReceiverClick : SendOrReceiveEvents()
    object OnSenderClick: SendOrReceiveEvents()
    object OnStartAdvertising : SendOrReceiveEvents()
    object OnStopAdvertising : SendOrReceiveEvents()
    object OnStartDiscovery : SendOrReceiveEvents()
    object OnStopDiscovery : SendOrReceiveEvents()
    object OnDisconnect : SendOrReceiveEvents()
    object OnRequestPermissions : SendOrReceiveEvents()
    object OnShowFilePicker : SendOrReceiveEvents()
    object OnHideFilePicker : SendOrReceiveEvents()
    object OnShowDeviceList : SendOrReceiveEvents()
    object OnHideDeviceList : SendOrReceiveEvents()
    object OnClearError : SendOrReceiveEvents()

    data class OnDeviceSelected(val device: DiscoveredDevice) : SendOrReceiveEvents()
    data class OnFilesSelected(val files: List<FileTransferInfo>) : SendOrReceiveEvents()
    data class OnSendFile(val device: DiscoveredDevice, val file: FileTransferInfo) : SendOrReceiveEvents()
    data class OnRemoveFile(val file: FileTransferInfo) : SendOrReceiveEvents()
}