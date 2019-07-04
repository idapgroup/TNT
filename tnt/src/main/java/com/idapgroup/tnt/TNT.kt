package com.idapgroup.tnt

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.idapgroup.tnt.take.*
import com.idapgroup.tnt.take.Target
import com.idapgroup.tnt.util.assertSerializable

fun <F: Fragment> F.take(
    source: Source<*>,
    permissions: PermissionParams.() -> Unit = {},
    callback: F.(Uri) -> Unit
) {
    assertSerializable(callback)
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.Fragment,
        source = source,
        permissions = permissions,
        callback = callback
    )
}

fun <F: FragmentActivity> F.take(
    source: Source<*>,
    permissions: PermissionParams.() -> Unit = {},
    callback: F.(Uri) -> Unit
) {
    assertSerializable(callback)
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.Fragment,
        source = source,
        permissions = permissions,
        callback = callback
    )
}

fun <F: Fragment> F.takeImageFromGallery(callback: F.(Uri) -> Unit) {
    take(Source.Gallery(MimeType.Image.Any), {}, callback)
}

fun <F: Fragment> F.takeVideoFromGallery(callback: F.(Uri) -> Unit) {
    take(Source.Gallery(MimeType.Video.Any), {}, callback)
}

fun <F: Fragment> F.takeImageFromCamera(callback: F.(Uri) -> Unit) {
    take(Source.Camera(MediaType.Image), {}, callback)
}

fun <F: Fragment> F.takeVideoFromCamera(callback: F.(Uri) -> Unit) {
    take(Source.Camera(MediaType.Video), {}, callback)
}

fun <F: FragmentActivity> F.takeImageFromGallery(callback: F.(Uri) -> Unit) {
    take(Source.Gallery(MimeType.Image.Any), {}, callback)
}

fun <F: FragmentActivity> F.takeVideoFromGallery(callback: F.(Uri) -> Unit) {
    take(Source.Gallery(MimeType.Video.Any), {}, callback)
}

fun <F: FragmentActivity> F.takeImageFromCamera(callback: F.(Uri) -> Unit) {
    take(Source.Camera(MediaType.Image), {}, callback)
}

fun <F: FragmentActivity> F.takeVideoFromCamera(callback: F.(Uri) -> Unit) {
    take(Source.Camera(MediaType.Video), {}, callback)
}