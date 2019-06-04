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

/**
 * [InputStream] producer
 */
typealias DataSource = () -> InputStream

/**
 * Transform this [URL] to [DataSource]
 */
fun URL.asDataSource(): DataSource = ::openStream
/**
 * Transform this [Uri] to [DataSource]
 */
fun Uri.asDataSource(ctx: Context): DataSource = {
    ctx.contentResolver.openInputStream(this)
        ?: throw IOException("Can't open '$this' as stream")
}
/**
 * Transform this [File] to [DataSource]
 */
fun File.asDataSource(): DataSource = { FileInputStream(this) }
/**
 * Transform this drawable resource [Int] to [DataSource]
 */
fun @receiver:DrawableRes Int.asDataSource(ctx: Context): DataSource = {
    ctx.resources.openRawResource(this)
}

/**
 * Decode this [File] to bitmap with transformation by [BitmapTransformer]
 */
fun File.transformAsBitmap(
    config: BitmapTransformer.() -> Unit
): Bitmap =
    asDataSource().transformAsBitmap(config)

/**
 * Decode this [Uri] to bitmap with transformation by [BitmapTransformer]
 */
fun Uri.transformAsBitmap(
    ctx: Context,
    config: BitmapTransformer.() -> Unit
): Bitmap =
    asDataSource(ctx).transformAsBitmap(config)

/**
 * Decode this [URL] to bitmap with transformation by [BitmapTransformer]
 */
fun URL.transformAsBitmap(
    config: BitmapTransformer.() -> Unit
): Bitmap =
    asDataSource().transformAsBitmap(config)

fun @receiver:DrawableRes Int.transformAsBitmap(
    ctx: Context,
    config: BitmapTransformer.() -> Unit
): Bitmap =
    asDataSource(ctx).transformAsBitmap(config)

/**
 * Decode this [DataSource] to bitmap with transformation by [BitmapTransformer]
 */
fun DataSource.transformAsBitmap(
    config: BitmapTransformer.() -> Unit
): Bitmap {
    val bitmap = toBitmap()
    val rotation = getExifRotationDegrees()
    val matrix = Matrix().rotate(rotation, bitmap.size)

    return bitmap.transform(matrix) {
        resize(mapSize = {
            if (rotation == 90f || rotation == 270f) it.swapSides() else it
        })
        config()
    }
}

private fun DataSource.getExifRotationDegrees(): Float =
    invoke().use(::ExifInterface).rotationDegrees.toFloat()
