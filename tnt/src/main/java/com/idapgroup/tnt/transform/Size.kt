package com.idapgroup.tnt.transform

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF

data class Size(val width: Int, val height: Int)
data class SizeF(val width: Float, val height: Float)

fun Size.map(matrix: Matrix): Size {
    val rect = RectF()
    matrix.mapRect(rect, toRectF())

    return Size(
        Math.round(rect.width()),
        Math.round(rect.height())
    )
}
fun Size.toRect() = Rect(0, 0, width, height)
fun Size.toRectF() = RectF(0f, 0f, width.toFloat(), height.toFloat())
fun Size.toSizeF(): SizeF = SizeF(width.toFloat(), height.toFloat())

fun Size.half() = Size(width / 2, height / 2)

val Size.minSide get() = Math.min(width, height)
val Size.maxSide get() = Math.max(width, height)

val Bitmap.size: Size get() = Size(width, height)
