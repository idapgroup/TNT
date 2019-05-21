package com.idapgroup.tnt

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import com.idapgroup.tnt.take.*
import com.idapgroup.tnt.take.Target
import java.io.File
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1

/**
 * Calls native device camera app for taking photo.
 * @param onTaken - member function of calling FragmentActivity that returns taken photo File.
 * @param configPermissions - Builder for permission denied callbacks
 */
@JvmName("takePhotoFromCameraByFile")
fun FragmentActivity.takePhotoFromCamera(
    onTaken: KFunction1<File, Unit>,
    configPermissions: (PermissionConfig.() -> Unit) = {}
) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = ImageSource.CAMERA,
        callback = onTaken,
        resultClass = File::class,
        configPermissions = configPermissions
    )

/**
 * Calls native device camera app for taking photo.
 * @param onTaken - member function of calling FragmentActivity that returns taken photo File.
 * @param configPermissions - Builder for permission denied callbacks
 */
@JvmName("takePhotoFromCameraByUri")
fun FragmentActivity.takePhotoFromCamera(
    onTaken: KFunction1<Uri, Unit>,
    configPermissions: (PermissionConfig.() -> Unit) = {}
) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = ImageSource.CAMERA,
        callback = onTaken,
        resultClass = Uri::class,
        configPermissions = configPermissions
    )

/**
 * Calls native device camera app for taking photo.
 * @param onTaken - member function of calling Fragment that returns taken photo File.
 * @param configPermissions - Builder for permission denied callbacks
 */
@JvmName("takePhotoFromCameraByFile")
fun Fragment.takePhotoFromCamera(
    onTaken: KFunction1<File, Unit>,
    configPermissions: (PermissionConfig.() -> Unit) = {}
) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.CAMERA,
        callback = onTaken,
        resultClass = File::class,
        configPermissions = configPermissions
    )

/**
 * Calls native device camera app for taking photo.
 * @param onTaken - member function of calling Fragment that returns taken photo File.
 * @param configPermissions - Builder for permission denied callbacks
 */
@JvmName("takePhotoFromCameraByUri")
fun Fragment.takePhotoFromCamera(
    onTaken: KFunction1<Uri, Unit>,
    configPermissions: PermissionConfig.() -> Unit
) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.CAMERA,
        callback = onTaken,
        resultClass = Uri::class,
        configPermissions = configPermissions
    )

/**
 * Calls native android image picker
 * @param onPicked - member function of calling FragmentActivity that returns picked image Uri
 * @param configPermissions - Builder for permission denied callbacks, if null - no permission request called.
 */
fun FragmentActivity.pickImageFromGallery(
    onPicked: KFunction1<Uri, Unit>,
    configPermissions: ((PermissionConfig.() -> Unit))? = {}
) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = ImageSource.GALLERY,
        callback = onPicked,
        resultClass = Uri::class,
        configPermissions = configPermissions
    )

/**
 * Calls native android image picker
 * @param onPicked - member function of calling Fragment that returns picked image Uri
 * @param configPermissions - Builder for permission denied callbacks, if null - no permission request called.
 */
fun Fragment.pickImageFromGallery(
    onPicked: KFunction1<Uri, Unit>,
    configPermissions: ((PermissionConfig.() -> Unit))? = {}
) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.GALLERY,
        callback = onPicked,
        resultClass = Uri::class,
        configPermissions = configPermissions
    )

@PublishedApi
internal inline fun <reified R : Any> Any.addResultFragment(
    fragmentManager: FragmentManager,
    target: Target,
    source: ImageSource,
    callback: KFunction1<R, Unit>,
    resultClass: KClass<R>,
    noinline configPermissions: (PermissionConfig.() -> Unit)?
) {
    assertHasMethod(callback)
    val permissionCallbacks = configPermissions?.let { PermissionConfig().apply(it) }

    fragmentManager.beginTransaction()
        .add(
            ResultFragment.newInstance(
                target,
                source,
                callback.name,
                resultClass,
                permissionCallbacks
            ),
            null
        )
        .commit()
}