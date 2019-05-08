package com.idapgroup.tnt.transform

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint

fun Bitmap.transform(config: ImageTransformer.() -> Unit): Bitmap {
    val transformer = ImageTransformer()
    transformer.apply(config)
    return transformer.transform(this)
}

open class ImageTransformer internal constructor() {

    private val squareSize = Size(-1, -1)

    private var size: Size? = null
    private var rotateDegrees: Float? = null
    private var scaleType = ScaleType.CropCenter
    private var background: Int? = null

    fun scaleType(scaleType: ScaleType): ImageTransformer {
        this.scaleType = scaleType
        return this
    }

    fun resize(width: Int, height: Int): ImageTransformer {
        this.size = Size(width, height)
        return this
    }

    fun square(size: Int? = null): ImageTransformer {
        this.size = size?.let { Size(it, it) } ?: squareSize
        return this
    }

    fun rotate(degrees: Float): ImageTransformer {
        this.rotateDegrees = degrees
        return this
    }

    fun background(color: Int): ImageTransformer {
        this.background = color
        return this
    }

    internal fun transform(bitmap: Bitmap): Bitmap {
        val matrix = Matrix()
        val dstSize = when(size) {
            null -> bitmap.size
            squareSize -> Math.min(bitmap.width, bitmap.height).let(::Size)
            else -> size!!
        }
        size?.takeUnless { it == squareSize }
            ?.let {
                matrix.preConcat(scaleType.toMatrix(bitmap.size, it))
            }
        rotateDegrees?.let {
            matrix.postRotate(it, dstSize.width / 2f, dstSize.height / 2f)
        }
        return bitmap.transform(dstSize, matrix) { canvas ->
            background?.let(canvas::drawColor)
        }
    }
}

fun ImageTransformer.cropStart() = scaleType(ScaleType.CropStart)
fun ImageTransformer.cropCenter() = scaleType(ScaleType.CropCenter)
fun ImageTransformer.cropEnd() = scaleType(ScaleType.CropEnd)

fun ImageTransformer.fitXY() = scaleType(ScaleType.FitXY)
fun ImageTransformer.fitStart() = scaleType(ScaleType.FitStart)
fun ImageTransformer.fitCenter() = scaleType(ScaleType.FitCenter)
fun ImageTransformer.fitEnd() = scaleType(ScaleType.FitEnd)

private fun Bitmap.transform(
    size: Size,
    matrix: Matrix,
    preDraw: (Canvas) -> Unit = {}
): Bitmap {
    val bitmap = Bitmap.createBitmap(size.width, size.height, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(bitmap)
    val paint = Paint().apply {
        isFilterBitmap = true
        isAntiAlias = true
    }
    preDraw(canvas)
    canvas.drawBitmap(this, matrix, paint)
    return bitmap
}