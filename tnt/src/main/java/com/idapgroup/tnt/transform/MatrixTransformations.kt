@file:Suppress("unused")

package com.idapgroup.tnt.transform

import com.idapgroup.tnt.transform.MatrixTransformer.Attrs

/** See [ScaleType.CropStart] */
val MatrixTransformer<*>.cropStart get() = ScaleType.CropStart
/** See [ScaleType.CropCenter] */
val MatrixTransformer<*>.cropCenter get() = ScaleType.CropCenter
/** See [ScaleType.CropEnd] */
val MatrixTransformer<*>.cropEnd get() = ScaleType.CropEnd
/** See [ScaleType.FitXY] */
val MatrixTransformer<*>.fitXY get() = ScaleType.FitXY
/** See [ScaleType.FitStart] */
val MatrixTransformer<*>.fitStart get() = ScaleType.FitStart
/** See [ScaleType.FitCenter] */
val MatrixTransformer<*>.fitCenter get() = ScaleType.FitCenter
/** See [ScaleType.FitEnd] */
val MatrixTransformer<*>.fitEnd get() = ScaleType.FitEnd

/**
 * Make content of [Attrs] squared with min origin content sides
 */
fun <A: Attrs<A>> MatrixTransformer<A>.square(scaleType: ScaleType) =
    square(side = null, scaleType = scaleType)

/**
 * Make content of [Attrs] squared with [side]s
 */
fun <A: Attrs<A>> MatrixTransformer<A>.square(
    side: Int? = null,
    scaleType: ScaleType = cropCenter
) = resize(
        { size: Size ->
            (side ?: size.minSide).let { Size(it, it) }
        },
        scaleType
    )
/**
 * Resize content of [Attrs]
 */
fun <A: Attrs<A>> MatrixTransformer<A>.resize(
    width: Int, height: Int,
    scaleType: ScaleType = cropCenter
) = resize({ Size(width, height) }, scaleType)

fun <A: Attrs<A>> MatrixTransformer<A>.resize(
    max: Int
) = resize(mapSize = {
    val scale = it.maxSide / max.toFloat()
    val newSize = (it / scale).toSize()
    newSize
})

fun <A: Attrs<A>> MatrixTransformer<A>.resize(
    mapSize: (Size) -> Size,
    scaleType: ScaleType = cropCenter
) = preTransform {
    val newSize = mapSize(it.size)
    it.matrix.postConcat(scaleType.toMatrix(it.size, newSize))
    it.modify(size = newSize)
}

/**
 * Rotate content of [Attrs]
 */
fun <A: Attrs<A>> MatrixTransformer<A>.rotate(degrees: Float) =
    preTransform {
        it.matrix.rotate(degrees, it.size)
        it
    }
