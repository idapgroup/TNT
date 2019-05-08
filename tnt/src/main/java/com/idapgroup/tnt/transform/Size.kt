package com.idapgroup.tnt.transform

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Rect
import android.graphics.RectF

data class Size(val width: Int, val height: Int) {

    constructor(side: Int): this(side, side)
}

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

val Bitmap.size: Size get() = Size(width, height)
