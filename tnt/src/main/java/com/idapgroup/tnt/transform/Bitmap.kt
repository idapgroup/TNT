package com.idapgroup.tnt.transform

import android.graphics.*
import android.graphics.Bitmap.CompressFormat
import java.io.File
import java.io.FileOutputStream

/**
 * Decode [DataSource] as bitmap
 */
fun DataSource.toBitmap(): Bitmap = invoke().use(BitmapFactory::decodeStream)

/**
 * Compress this [Bitmap] in [file]
 */
fun Bitmap.compressTo(
    format: CompressFormat = CompressFormat.JPEG,
    quality: Int = 90,
    file: File
) {
    FileOutputStream(file).use { stream ->
        compress(format, quality, stream)
    }
}

internal fun Bitmap.transform(
    size: Size,
    matrix: Matrix,
    paint: Paint = Paint(),
    preDraw: (Canvas) -> Unit = {}
): Bitmap {
    val bitmap = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    with(paint) {
        isFilterBitmap = true
        isAntiAlias = true
    }
    preDraw(canvas)
    canvas.drawBitmap(this, matrix, paint)
    recycle()
    return bitmap
}