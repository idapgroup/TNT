package com.idapgroup.tnt.transform

import android.graphics.Matrix

/**
 * Options for scaling the content of an source to the destination target.
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
     * Coming soon...
     */
    CropStart,
    /**
     * Scale the image uniformly (maintain the image's aspect ratio) so
     * that both dimensions (width and height) of the image will be equal
     * to or larger than the corresponding dimension of the view
     * (minus padding). The image is then centered in the view.
     */
    CropCenter,
    /**
     * Coming soon...
     */
    CropEnd,
}