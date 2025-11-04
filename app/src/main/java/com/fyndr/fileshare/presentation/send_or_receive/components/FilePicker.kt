package com.fyndr.fileshare.presentation.send_or_receive.components

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fyndr.fileshare.domain.send_or_receive.models.FileTransferInfo

@Composable
fun FilePicker(
    onFileSelected: (FileTransferInfo) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedFileUri by remember { mutableStateOf<Uri?>(null) }
    var selectedFileName by remember { mutableStateOf<String?>(null) }
    var selectedFileSize by remember { mutableStateOf<Long?>(null) }

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            selectedFileUri = it
            // Extract file name from URI
            selectedFileName = getFileNameFromUri(it)
            // Get file size (you might need to implement this)
            selectedFileSize = getFileSizeFromUri(it)
        }
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF2A2A2A)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Select File to Share",
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(16.dp))

            // File selection button
            OutlinedButton(
                onClick = { filePickerLauncher.launch("*/*") },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color(0xFF2196F3)
                ),
                border = ButtonDefaults.outlinedButtonBorder.copy(
                    brush = androidx.compose.ui.graphics.Brush.horizontalGradient(
                        colors = listOf(Color(0xFF2196F3), Color(0xFF21CBF3))
                    )
                )
            ) {
                Text("Choose File")
            }

            // Selected file info
            selectedFileUri?.let { uri ->
                Spacer(modifier = Modifier.height(16.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1A1A1A)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(12.dp)
                    ) {
                        Text(
                            text = "Selected File:",
                            color = Color(0xFF9E9E9E),
                            fontSize = 12.sp
                        )

                        Text(
                            text = selectedFileName ?: "Unknown",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )

                        selectedFileSize?.let { size ->
                            Text(
                                text = formatFileSize(size),
                                color = Color(0xFF9E9E9E),
                                fontSize = 12.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Share button
                Button(
                    onClick = {
                        if (selectedFileUri != null && selectedFileName != null && selectedFileSize != null) {
                            val fileTransferInfo = FileTransferInfo(
                                fileName = selectedFileName!!,
                                fileSize = selectedFileSize!!,
                                fileUri = selectedFileUri!!,
                                mimeType = getMimeTypeFromUri(selectedFileUri!!)
                            )
                            onFileSelected(fileTransferInfo)
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text("Share File")
                }
            }
        }
    }
}

private fun getFileNameFromUri(uri: Uri): String {
    return uri.lastPathSegment ?: "Unknown File"
}

private fun getFileSizeFromUri(uri: Uri): Long {
    // This is a simplified implementation
    // In a real app, you'd need to get the actual file size
    return 1024L // Placeholder
}

private fun getMimeTypeFromUri(uri: Uri): String {
    // This is a simplified implementation
    // In a real app, you'd need to determine the actual MIME type
    return "application/octet-stream" // Placeholder
}
fun formatFileSize(bytes: Long): String {
    val kb = bytes / 1024.0
    val mb = kb / 1024.0
    val gb = mb / 1024.0

    return when {
        gb >= 1 -> "%.2f GB".format(gb)
        mb >= 1 -> "%.2f MB".format(mb)
        kb >= 1 -> "%.2f KB".format(kb)
        else -> "$bytes bytes"
    }
}
