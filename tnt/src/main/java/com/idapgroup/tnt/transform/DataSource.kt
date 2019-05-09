package com.idapgroup.tnt.transform

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.annotation.DrawableRes
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStream
import java.net.URL

typealias DataSource = () -> InputStream

fun URL.asDataSource(): DataSource = ::openStream

fun Uri.asDataSource(ctx: Context): DataSource = {
    ctx.contentResolver.openInputStream(this)
        ?: throw IOException("Can't open '$this' as stream")
}

fun File.asDataSource(): DataSource = { FileInputStream(this) }

fun @receiver:DrawableRes Int.asDataSource(ctx: Context): DataSource = {
    ctx.resources.openRawResource(this)
}


fun File.transformAsBitmap(
    config: BitmapTransformer.() -> Unit
): Bitmap =
    asDataSource().transformAsBitmap(config)

fun Uri.transformAsBitmap(
    ctx: Context,
    config: BitmapTransformer.() -> Unit
): Bitmap =
    asDataSource(ctx).transformAsBitmap(config)

fun URL.transformAsBitmap(
    config: BitmapTransformer.() -> Unit
): Bitmap =
    asDataSource().transformAsBitmap(config)

fun @receiver:DrawableRes Int.transformAsBitmap(
    ctx: Context,
    config: BitmapTransformer.() -> Unit
): Bitmap =
    asDataSource(ctx).transformAsBitmap(config)

fun DataSource.transformAsBitmap(
    config: BitmapTransformer.() -> Unit
): Bitmap {
    val bitmap = toBitmap()
    val matrix = Matrix().apply {
        preRotate(
            getExifRotationDegrees(),
            bitmap.width / 2f,
            bitmap.height / 2f
        )
    }
    return bitmap.transform(matrix, config)
}

private fun DataSource.getExifRotationDegrees(): Float =
    invoke().use(::ExifInterface).rotationDegrees.toFloat()
