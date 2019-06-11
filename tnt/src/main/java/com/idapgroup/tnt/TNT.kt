package com.idapgroup.tnt

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.idapgroup.tnt.take.ImageSource
import com.idapgroup.tnt.take.RequestParams
import com.idapgroup.tnt.take.Target
import com.idapgroup.tnt.take.addResultFragment

/**
 * Calls android's native image picker
 * @param block - sets request parameters
 */
fun FragmentActivity.pickImageFromGallery(
    block: RequestParams.() -> Unit
) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = ImageSource.GALLERY,
        block = block
    )

/**
 * Calls native android image picker
 * @param block - sets request parameters
 */
fun Fragment.pickImageFromGallery(
    block: RequestParams.() -> Unit
) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.GALLERY,
        block = block
    )

/**
 * Calls native device camera app for taking photo.
 * @param block - collects request parameters
 */
fun FragmentActivity.takePhotoFromCamera(
    block: RequestParams.() -> Unit
) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = ImageSource.CAMERA,
        block = block
    )

/**
 * Calls native device camera app for taking photo.
 * @param block - collects request parameters
 */
fun Fragment.takePhotoFromCamera(
    block: RequestParams.() -> Unit
) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.CAMERA,
        block = block
    )
