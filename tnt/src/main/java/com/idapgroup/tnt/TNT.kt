package com.idapgroup.tnt

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.idapgroup.tnt.take.*
import com.idapgroup.tnt.take.Target
import com.idapgroup.tnt.take.assertSerializable

/**
 * Calls native android picker/camera and returns Uri into callback
 * @param source - Source for taking data
 * @param permissions - dispatches permission denied events
 * @param callback - receiver for selected data Uri
 */
fun <F: Fragment> F.take(
    source: Source,
    permissions: PermissionParams.() -> Unit = {},
    callback: F.(Uri) -> Unit
) {
    assertSerializable(context!!, callback)
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.Fragment,
        source = source,
        permissions = permissions,
        callback = callback
    )
}

/**
 * Calls native android picker/camera and returns Uri into callback
 * @param source - Source for taking data
 * @param permissions - dispatches permission denied events
 * @param callback - receiver for selected data Uri
 */
fun <F: FragmentActivity> F.take(
    source: Source,
    permissions: PermissionParams.() -> Unit = {},
    callback: F.(Uri) -> Unit
) {
    assertSerializable(this, callback)
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.Fragment,
        source = source,
        permissions = permissions,
        callback = callback
    )
}

/**
 * Takes image from android gallery and invokes callback with selected Uri
 */
fun <F: Fragment> F.takeImageFromGallery(callback: F.(Uri) -> Unit) {
    take(Source.Gallery(MimeType.Image.Any), {}, callback)
}

/**
 * Takes video from android gallery and invokes callback with selected Uri
 */
fun <F: Fragment> F.takeVideoFromGallery(callback: F.(Uri) -> Unit) {
    take(Source.Gallery(MimeType.Video.Any), {}, callback)
}

/**
 * Takes image from android native camera and invokes callback with captured Uri
 */
fun <F: Fragment> F.takeImageFromCamera(callback: F.(Uri) -> Unit) {
    take(Source.Camera(MediaType.Image), {}, callback)
}

/**
 * Takes video from android native camera and invokes callback with captured Uri
 */
fun <F: Fragment> F.takeVideoFromCamera(callback: F.(Uri) -> Unit) {
    take(Source.Camera(MediaType.Video), {}, callback)
}

/**
 * Takes image from android gallery and invokes callback with selected Uri
 */
fun <F: FragmentActivity> F.takeImageFromGallery(callback: F.(Uri) -> Unit) {
    take(Source.Gallery(MimeType.Image.Any), {}, callback)
}

/**
 * Takes video from android gallery and invokes callback with selected Uri
 */
fun <F: FragmentActivity> F.takeVideoFromGallery(callback: F.(Uri) -> Unit) {
    take(Source.Gallery(MimeType.Video.Any), {}, callback)
}

/**
 * Takes image from android native camera and invokes callback with captured Uri
 */
fun <F: FragmentActivity> F.takeImageFromCamera(callback: F.(Uri) -> Unit) {
    take(Source.Camera(MediaType.Image), {}, callback)
}

/**
 * Takes video from android native camera and invokes callback with captured Uri
 */
fun <F: FragmentActivity> F.takeVideoFromCamera(callback: F.(Uri) -> Unit) {
    take(Source.Camera(MediaType.Video), {}, callback)
}