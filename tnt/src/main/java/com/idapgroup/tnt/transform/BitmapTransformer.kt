package com.idapgroup.tnt.transform

import android.graphics.*
import com.idapgroup.tnt.transform.BitmapTransformer.*
import androidx.annotation.IntRange as AIntRange

internal typealias Transform = Attrs.() -> Attrs

class BitmapTransformer {

    data class Attrs(
        val bitmap: Bitmap,
        val size: Size = bitmap.size,
        val colorFilter: Int? = null,
        val background: Int? = null,
        val matrix: Matrix = Matrix()
    )

    private val preTransforms = mutableListOf<Transform>()
    private val postTransforms = mutableListOf<Transform>()

    internal fun preTransform(transform: Transform): BitmapTransformer {
        preTransforms += transform
        return this
    }

    internal fun postTransform(transform: Transform): BitmapTransformer {
        postTransforms += transform
        return this
    }

    internal fun transform(attrs: Attrs, block: Attrs.() -> Attrs): Attrs {
        val preAttrs = preTransforms.fold(attrs)
        return postTransforms.fold(block(preAttrs))
    }

    private fun List<Transform>.fold(initial: Attrs): Attrs =
        fold(initial) { acc, op -> op(acc) }
}

fun Bitmap.transform(matrix: Matrix = Matrix(), config: BitmapTransformer.() -> Unit): Bitmap {
    val transformer = BitmapTransformer()
    transformer.config()
    val attrs = Attrs(this, matrix = matrix)
    return transformer.transform(attrs) {
        val paint = Paint()
        colorFilter?.let {
            paint.colorFilter = PorterDuffColorFilter(it, PorterDuff.Mode.SRC_ATOP)
        }
        this.copy(
            bitmap = bitmap.transform(size, matrix, paint) { canvas ->
                background?.let(canvas::drawColor)
            }
        )
    }.bitmap
}