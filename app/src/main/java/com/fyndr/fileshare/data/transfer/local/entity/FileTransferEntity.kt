package com.fyndr.fileshare.data.transfer.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.fyndr.fileshare.domain.send_or_receive.models.TransferState

@Entity(tableName = "file_transfers")
data class FileTransferEntity(
    @PrimaryKey
    val transferId: String,
    val fileName: String,
    val fileSize: Long,
    val filePath: String,
    val mimeType: String,
    val deviceName: String,
    val deviceId: String,
    val transferState: TransferState,
    val isIncoming: Boolean,
    val timeStamp: Long,
    val bytesTransferred: Long = 0,
    val errorMessage: String? = null
)
