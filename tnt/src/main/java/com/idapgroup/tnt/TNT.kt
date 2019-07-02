package com.idapgroup.tnt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.idapgroup.tnt.take.*
import com.idapgroup.tnt.take.Target

/**
 * Calls android's native picker
 * @param block - sets request parameters
 */
fun FragmentActivity.pickFromGallery(
    mimeType: MimeType,
    block: RequestParams.() -> Unit
) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = Source.Gallery(mimeType),
        block = block
    )

/**
 * Calls native android picker
 * @param block - sets request parameters
 */
fun Fragment.pickFromGallery(
    mimeType: MimeType,
    block: RequestParams.() -> Unit
) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = Source.Gallery(mimeType),
        block = block
    )

/**
 * Calls native device camera app for taking photo/video.
 * @param block - collects request parameters
 */
fun FragmentActivity.takeFromCamera(
    type: CaptureType,
    block: RequestParams.() -> Unit
) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = Source.Camera(type),
        block = block
    )

/**
 * Calls native device camera app for taking photo/video.
 * @param block - collects request parameters
 */
fun Fragment.takeFromCamera(
    type: CaptureType,
    block: RequestParams.() -> Unit
) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = Source.Camera(type),
        block = block
    )
