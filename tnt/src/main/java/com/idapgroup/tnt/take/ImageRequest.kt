package com.idapgroup.tnt.take

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.core.content.FileProvider
import java.io.File

internal fun takePhoto(
    context: Context,
    requestCode: Int,
    startActivity: (Intent, requestCode: Int) -> Unit,
    outFile: File? = null
): File {
    val file = outFile ?: createTempImageFile(context)
    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
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

internal fun pickImage(
    requestCode: Int,
    startActivity: (Intent, requestCode: Int) -> Unit
) {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
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