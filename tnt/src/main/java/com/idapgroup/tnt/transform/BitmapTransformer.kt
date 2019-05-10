package com.idapgroup.tnt.transform

import android.graphics.*
import com.idapgroup.tnt.transform.MatrixTransformer.*

class BitmapTransformer : MatrixTransformer<BitmapTransformerAttrs>()

data class BitmapTransformerAttrs(
    internal val bitmap: Bitmap,
    val colorFilter: PorterDuffColorFilter? = null,
    val background: Int? = null,
    override val matrix: Matrix = Matrix(),
    override val size: Size = bitmap.size
) : Attrs<BitmapTransformerAttrs> {

    override fun modify(matrix: Matrix, size: Size): BitmapTransformerAttrs =
        copy(matrix = matrix, size = size)
}

internal fun Bitmap.transform(
    matrix: Matrix = Matrix(),
    config: BitmapTransformer.() -> Unit
): Bitmap {
    val transformer = BitmapTransformer()
    transformer.config()
    val initial = BitmapTransformerAttrs(bitmap = this, matrix = matrix)

    val newAttrs = transformer.transform(initial) { attrs ->
        val paint = Paint()
        paint.colorFilter = attrs.colorFilter
        val bitmap = attrs.bitmap.transform(attrs.size, matrix, paint) { canvas ->
            attrs.background?.let(canvas::drawColor)
        }
        attrs.copy(bitmap = bitmap)
    }
    return newAttrs.bitmap
}