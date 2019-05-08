package com.idapgroup.tnt.take

import android.content.Context
import java.io.File

internal fun createTempImageFile(context: Context): File {
    val dstDirectory = getTempImageDirectory(context)
    dstDirectory.mkdirs()
    return File.createTempFile("img", ".jpg", dstDirectory)
}

internal fun getTempImageDirectory(context: Context): File {
    val externalDir = context.getExternalFilesDir(null)
        ?: throw IllegalStateException("Media storage isn't available")
    return File(externalDir, "tmp")
}




