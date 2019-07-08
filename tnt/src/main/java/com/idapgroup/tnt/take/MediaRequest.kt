package com.idapgroup.tnt.take

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File

internal fun capture(
    context: Context,
    type: MediaType,
    requestCode: Int,
    startActivity: (Intent, requestCode: Int) -> Unit,
    outFile: File? = null
): File {
    val file = outFile ?: createTempFile(context, type)
    val action = when (type) {
        MediaType.Image -> MediaStore.ACTION_IMAGE_CAPTURE
        MediaType.Video -> MediaStore.ACTION_VIDEO_CAPTURE
    }
    val intent = Intent(action)

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        intent.putExtra(
            MediaStore.EXTRA_OUTPUT,
            file.providerUri(context)
        )
    } else {
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file))
    }
    startActivity(intent.withChooser(), requestCode)
    return file
}

internal fun pick(
    mimeType: MimeType,
    requestCode: Int,
    startActivity: (Intent, requestCode: Int) -> Unit
) {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = mimeType.value
    startActivity(intent.withChooser(), requestCode)
}

private fun File.providerUri(context: Context): Uri {
    val packageInfo = context.packageManager
        .getPackageInfo(context.packageName, PackageManager.GET_PROVIDERS)
    val provider = packageInfo.providers
        .first { it.authority.endsWith(".tnt.provider") }
    return FileProvider.getUriForFile(context, provider.authority, this)
}

private fun Intent.withChooser() = Intent.createChooser(this, "")