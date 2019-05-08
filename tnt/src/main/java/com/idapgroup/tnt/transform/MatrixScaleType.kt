package com.idapgroup.tnt.transform

import android.graphics.Matrix

fun ScaleType.toMatrix(src: Size, dst: Size): Matrix {
    val matrix = Matrix()

    when (this) {
        ScaleType.Center -> // Center bitmap in view, no scaling.
            matrix.setTranslate(
                (dst.width - src.width) * 0.5f,
                (dst.height - src.height) * 0.5f
            )
        ScaleType.CropStart -> {
            matrix.setCrop(src, dst, 0f)
        }
        ScaleType.CropCenter -> {
            matrix.setCrop(src, dst, 0.5f)
        }
        ScaleType.CropEnd -> {
            matrix.setCrop(src, dst, 1f)
        }
        else -> matrix.setRectToRect(src.toRectF(), dst.toRectF(), this.toScaleToFit())
    }
    return matrix
}

private fun Matrix.setCrop(src: Size, dst: Size, ratio: Float) {
    val scale: Float
    var dx = 0f
    var dy = 0f

    if (src.width * dst.height > dst.width * src.height) {
        scale = dst.height.toFloat() / src.height.toFloat()
        dx = (dst.width - src.width * scale) * ratio
    } else {
        scale = dst.width.toFloat() / src.width.toFloat()
        dy = (dst.height - src.height * scale) * ratio
    }

    setScale(scale, scale)
    postTranslate(dx, dy)
}

fun ScaleType.toScaleToFit(): Matrix.ScaleToFit = when (this) {
    ScaleType.FitStart -> Matrix.ScaleToFit.START
    ScaleType.FitCenter -> Matrix.ScaleToFit.CENTER
    ScaleType.FitEnd -> Matrix.ScaleToFit.END
    ScaleType.FitXY -> Matrix.ScaleToFit.FILL
    else -> throw IllegalArgumentException("Unsupported scale type: $this")
}