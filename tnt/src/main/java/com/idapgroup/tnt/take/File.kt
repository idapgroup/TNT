package com.idapgroup.tnt.take

import android.content.Context
import java.io.File

internal fun createTempFile(context: Context, type: CaptureType): File {
    val dstDirectory = getTempImageDirectory(context)
    dstDirectory.mkdirs()
    val prefix = when (type) {
        CaptureType.Image -> "img"
        CaptureType.Video -> "vid"
    }
    return File.createTempFile(prefix, ".tmp", dstDirectory)
}

internal fun getTempImageDirectory(context: Context): File {
    val externalDir = context.getExternalFilesDir(null)
        ?: throw IllegalStateException("Media storage isn't available")
    return File(externalDir, "tmp")
}




