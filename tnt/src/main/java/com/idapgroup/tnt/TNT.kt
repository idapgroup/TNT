package com.idapgroup.tnt

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.idapgroup.tnt.take.ImageSource
import com.idapgroup.tnt.take.ResultFragment
import com.idapgroup.tnt.take.Target
import com.idapgroup.tnt.take.assertHasMethod
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1

fun FragmentActivity.takePhotoFromCamera(onTaken: KFunction1<File, Unit>) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = ImageSource.CAMERA,
        callback = onTaken,
        resultClass = File::class
    )

@JvmName("takePhotoFromCameraByFile")
fun Fragment.takePhotoFromCamera(onTaken: KFunction1<File, Unit>) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.CAMERA,
        callback = onTaken,
        resultClass = File::class
    )

@JvmName("takePhotoFromCameraByUri")
fun Fragment.takePhotoFromCamera(onTaken: KFunction1<Uri, Unit>) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.CAMERA,
        callback = onTaken,
        resultClass = Uri::class
    )

fun FragmentActivity.pickImageFromGallery(onPicked: KFunction1<Uri, Unit>) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = ImageSource.GALLERY,
        callback = onPicked,
        resultClass = Uri::class
    )

fun Fragment.pickImageFromGallery(onPicked: KFunction1<Uri, Unit>) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.GALLERY,
        callback = onPicked,
        resultClass = Uri::class
    )

@PublishedApi
internal inline fun <reified R: Any> Any.addResultFragment(
    fragmentManager: FragmentManager,
    target: Target,
    source: ImageSource,
    callback: KFunction1<R, Unit>,
    resultClass: KClass<R>
) {
    assertHasMethod(callback)

    fragmentManager.beginTransaction()
        .add(ResultFragment.newInstance(target, source, callback.name, resultClass), null)
        .commit()
}