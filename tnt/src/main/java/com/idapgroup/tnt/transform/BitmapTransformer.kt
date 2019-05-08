package com.idapgroup.tnt.transform

import android.graphics.Bitmap

fun Bitmap.transformer() = BitmapTransformer(this)

class BitmapTransformer(
    private val bitmap: Bitmap
) : ImageTransformer() {

    fun transform(): Bitmap = transform(bitmap)
}