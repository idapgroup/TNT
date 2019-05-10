@file:Suppress("unused")

package com.idapgroup.tnt.transform

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.annotation.IntRange

/**
 * Add blur effect to [BitmapTransformer]
 */
fun BitmapTransformer.blur(
    radius: Int? = DEFAULT_RADIUS,
    sampling: Float? = DEFAULT_SAMPLING,
    context: Context? = null
) = postTransform {
    it.copy(bitmap = it.bitmap.blur(radius, sampling, context))
}

/**
 * Add colorFilter to [BitmapTransformer] by color components(alpha, red, green, blue)
 */
fun BitmapTransformer.colorFilter(
    @IntRange(from = 0, to = 255) a: Int,
    @IntRange(from = 0, to = 255) r: Int,
    @IntRange(from = 0, to = 255) g: Int,
    @IntRange(from = 0, to = 255) b: Int
) =
    colorFilter(Color.argb(a, r, g, b))

/**
 * Add colorFilter to [BitmapTransformer]
 */
fun BitmapTransformer.colorFilter(color: Int) =
    preTransform {
        it.copy(colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP))
    }

/**
 * Add colorFilter to [BitmapTransformer]
 */
fun BitmapTransformer.colorFilter(filter: PorterDuffColorFilter) =
    preTransform { it.copy(colorFilter = filter) }


fun BitmapTransformer.background(color: Int) =
    preTransform { it.copy(background = color) }
