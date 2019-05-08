package com.idapgroup.tnt.transform

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.exifinterface.media.ExifInterface
import java.io.IOException


fun Uri.asBitmap(ctx: Context): Bitmap {
    val inputStream = ctx.contentResolver.openInputStream(this)
        ?: throw IOException("$this is bad image")
    return inputStream.use(BitmapFactory::decodeStream)
}

fun Uri.asMediaUri(ctx: Context): Uri {
    val projection = arrayOf(MediaStore.Images.Media.DATA)
    val cursor = ctx.contentResolver.query(this, projection, null, null, null)
    cursor ?: return this

    return cursor.use { c ->
        val index = c!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        c.moveToFirst()
        Uri.parse(c.getString(index))
    }
}

internal fun Uri.getExifRotationDegrees(ctx: Context): Float
        = ctx.contentResolver.openInputStream(this)
    ?.use(::ExifInterface)
    ?.let { it.rotationDegrees.toFloat() }
    ?: 0f
