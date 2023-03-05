package com.dave_devs.media3videoplayer.media3_player

import android.net.Uri
import androidx.media3.common.MediaItem

data class VideoItem(
    val contentUri: Uri, //This house the path for the content to play.
    val name: String, //The name of each video which is read with MetadataReader from Uri.
    val mediaItem: MediaItem //Representation of a media item.
)