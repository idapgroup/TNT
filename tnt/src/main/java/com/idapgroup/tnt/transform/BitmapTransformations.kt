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
    @IntRange(from = 0, to = 255) alpha: Int,
    @IntRange(from = 0, to = 255) red: Int,
    @IntRange(from = 0, to = 255) green: Int,
    @IntRange(from = 0, to = 255) blue: Int
) =
    colorFilter(Color.argb(alpha, red, green, blue))

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
