package com.fyndr.fileshare.domain.send_or_receive.models

import android.net.Uri

data class DiscoveredDevice(
    val endpointId: String,
    val deviceName: String,
    val serviceId: String,
    val isConnected: Boolean = false
)

data class FileTransferInfo(
    val fileName: String,
    val fileSize: Long,
    val fileUri: Uri,
    val mimeType: String,
    val transferId: String = System.currentTimeMillis().toString()
)

data class TransferProgress(
    val transferId: String,
    val fileName: String,
    val bytesTransferred: Long,
    val totalBytes: Long,
    val isCompleted: Boolean = false,
    val isFailed: Boolean = false,
    val error: String? = null
) {
    val progressPercentage: Float
        get() = if (totalBytes > 0) (bytesTransferred.toFloat() / totalBytes.toFloat()) * 100f else 0f
}

enum class ConnectionState {
    DISCONNECTED,
    ADVERTISING,
    DISCOVERING,
    CONNECTING,
    CONNECTED,
    ERROR
}

enum class TransferState {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    FAILED,
    CANCELLED
}