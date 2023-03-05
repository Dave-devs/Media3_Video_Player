package com.dave_devs.media3videoplayer.media3_player

import android.app.Application
import android.net.Uri
import android.provider.MediaStore

data class MetaData(
    val fileName: String
)

interface MetaDataReader {
    fun getMetaDataFromUri(contentUri: Uri): MetaData?
}

//Video fileName reader from the Uri
class MetaDataReaderImpl(
    private val app: Application
): MetaDataReader {

    override fun getMetaDataFromUri(contentUri: Uri): MetaData? {
        //If contentUri Link/Path is null
        if(contentUri.scheme != "content") {
            return null
        }
        val fileName = app.contentResolver
                //Query the given URI, returning a Cursor over the result set.
            .query(
                contentUri,
                arrayOf(MediaStore.Video.VideoColumns.DISPLAY_NAME),
                null,
                null,
                null,
            )
            ?.use { cursor ->
                /* Cursor -> This interface provides random read-write
                access to the result set returned by a database query.
                With cursor we can fetch single filed that are currently
                not contained in the result of this query.
                */
                val index = cursor.getColumnIndex(MediaStore.Video.VideoColumns.DISPLAY_NAME)
                cursor.moveToFirst() //To move to the first entry of the query result.
                cursor.getString(index)
            }
        return fileName?.let { fullFileName ->
            MetaData(
                fileName = Uri.parse(fullFileName).lastPathSegment ?: return null
            )
        }
    }
}