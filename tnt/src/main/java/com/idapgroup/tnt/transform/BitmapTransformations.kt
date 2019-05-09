@file:Suppress("unused")

package com.idapgroup.tnt.transform

import android.content.Context
import android.graphics.Color
import androidx.annotation.IntRange

val BitmapTransformer.cropStart get() = ScaleType.CropStart
val BitmapTransformer.cropCenter get() = ScaleType.CropCenter
val BitmapTransformer.cropEnd get() = ScaleType.CropEnd

val BitmapTransformer.fitXY get() = ScaleType.FitXY
val BitmapTransformer.fitStart get() = ScaleType.FitStart
val BitmapTransformer.fitCenter get() = ScaleType.FitCenter
val BitmapTransformer.fitEnd get() = ScaleType.FitEnd

fun BitmapTransformer.square(scaleType: ScaleType): BitmapTransformer =
    square(side = null, scaleType = scaleType)

fun BitmapTransformer.square(side: Int? = null, scaleType: ScaleType = cropCenter): BitmapTransformer =
    resize(
        { size: Size ->
            (side ?: size.minSide).let { Size(it, it) }
        },
        scaleType
    )

fun BitmapTransformer.resize(width: Int, height: Int, scaleType: ScaleType = cropCenter): BitmapTransformer =
    resize({ Size(width, height) }, scaleType)

private fun BitmapTransformer.resize(
    transformSize: (Size) -> Size,
    scaleType: ScaleType
): BitmapTransformer =
    preTransform {
        val newSize = transformSize(size)
        matrix.postConcat(scaleType.toMatrix(size, newSize))
        this.copy(size = newSize)
    }

fun BitmapTransformer.rotate(degrees: Float): BitmapTransformer =
    preTransform {
        matrix.postRotate(degrees, size.width / 2f, size.height / 2f)
        this
    }

fun BitmapTransformer.blur(
    radius: Int? = DEFAULT_RADIUS,
    sampling: Float? = DEFAULT_SAMPLING,
    context: Context? = null
): BitmapTransformer =
    postTransform {
        this.copy(bitmap = bitmap.blur(radius, sampling, context))
    }

fun BitmapTransformer.colorFilter(
    @IntRange(from = 0, to = 255) alpha: Int,
    @IntRange(from = 0, to = 255) red: Int,
    @IntRange(from = 0, to = 255) green: Int,
    @IntRange(from = 0, to = 255) blue: Int
): BitmapTransformer =
    colorFilter(Color.argb(alpha, red, green, blue))

fun BitmapTransformer.colorFilter(color: Int): BitmapTransformer =
    preTransform { this.copy(colorFilter = color) }


fun BitmapTransformer.background(color: Int): BitmapTransformer =
    preTransform { this.copy(background = color) }
