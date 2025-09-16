package com.fyndr.fileshare.domain.home.models

import com.fyndr.fileshare.R

data class MediaItem(
    val mediaTypeName: String,
    val mediaTypeIcon: Int,
)


val itemList = listOf(
    MediaItem(
        mediaTypeName = "Music",
        mediaTypeIcon = R.drawable.ic_audio
    ),
    MediaItem(
        mediaTypeName = "Photos",
        mediaTypeIcon = R.drawable.ic_images
    ),
    MediaItem(
        mediaTypeName = "Videos",
        mediaTypeIcon = R.drawable.ic_video
    ),
    MediaItem(
        mediaTypeName = "Folder",
        mediaTypeIcon = R.drawable.ic_folder
    ),
    MediaItem(
        mediaTypeName = "Documents",
        mediaTypeIcon = R.drawable.ic_document
    ),
    MediaItem(
        mediaTypeName = "Apps",
        mediaTypeIcon = R.drawable.ic_apps
    )
)