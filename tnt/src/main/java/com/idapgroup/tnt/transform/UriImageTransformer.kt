package com.idapgroup.tnt.transform

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri

fun Uri.transformAsBitmap(ctx: Context, config: ImageTransformer.() -> Unit): Bitmap {
    val imageUri = asMediaUri(ctx)
    val bitmap = imageUri.asBitmap(ctx)
    val transformer = BitmapTransformer(bitmap)
    transformer.rotate(imageUri.getExifRotationDegrees(ctx))
    transformer.apply(config)
    return transformer.transform()
}
