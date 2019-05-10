package com.idapgroup.tnt.transform

import android.graphics.Matrix

/**
 * Options for scaling the content of an source to the target.
 */
enum class ScaleType {
    /**
     * Scale the image using [Matrix.ScaleToFit.FILL].
     */
    FitXY,
    /**
     * Scale the image using [Matrix.ScaleToFit.START].
     */
    FitStart,
    /**
     * Scale the image using [Matrix.ScaleToFit.CENTER].
     */
    FitCenter,
    /**
     * Scale the image using [Matrix.ScaleToFit.END].
     */
    FitEnd,
    /**
     * Center the image in the view, but perform no scaling.
     */
    Center,
    /**
     * Scale the image uniformly (maintain the image's aspect ratio) so
     * that both dimensions (width and height) of the image will be equal
     * to or larger than the corresponding dimension of the target .
     * The image is then position on the start of target.
     */
    CropStart,
    /**
     * Scale the image uniformly (maintain the image's aspect ratio) so
     * that both dimensions (width and height) of the image will be equal
     * to or larger than the corresponding dimension of the target.
     * The image is then centered in the target.
     */
    CropCenter,
    /**
     * Scale the image uniformly (maintain the image's aspect ratio) so
     * that both dimensions (width and height) of the image will be equal
     * to or larger than the corresponding dimension of the target.
     * The image is then position on the end of target.
     */
    CropEnd,
}