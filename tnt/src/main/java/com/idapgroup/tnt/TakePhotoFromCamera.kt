package com.idapgroup.tnt

import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.idapgroup.tnt.take.*
import com.idapgroup.tnt.take.Target
import java.io.File
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

/**
 * Calls native device camera app for taking photo.
 * @param onTaken - member function of calling FragmentActivity that returns taken photo File.
 * @param configPermissions - Builder for permission denied callbacks
 */
fun FragmentActivity.takePhotoFromCamera(
    onTaken: KFunction1<File, Unit>,
    configPermissions: (PermissionConfig.() -> Unit) = {}
) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = ImageSource.CAMERA,
        callback = onTaken.asCallback(),
        configPermissions = configPermissions
    )

/**
 * Calls native device camera app for taking photo.
 * @param onTaken - member function of calling FragmentActivity that returns taken photo File.
 * @param param - will be preserved and return as second param in [onTaken] callback. Must be [Serializable] or [Parcelable]
 * @param configPermissions - Builder for permission denied callbacks
 */
inline fun <reified T> FragmentActivity.takePhotoFromCamera(
    onTaken: KFunction2<File, T, Unit>,
    param: T,
    noinline configPermissions: (PermissionConfig.() -> Unit) = {}
) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = ImageSource.CAMERA,
        callback = onTaken.asCallback(param),
        configPermissions = configPermissions
    )

/**
 * Calls native device camera app for taking photo.
 * @param onTaken - member function of calling Fragment that returns taken photo File.
 * @param configPermissions - Builder for permission denied callbacks
 */
fun Fragment.takePhotoFromCamera(
    onTaken: KFunction1<File, Unit>,
    configPermissions: (PermissionConfig.() -> Unit) = {}
) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.CAMERA,
        callback = onTaken.asCallback(),
        configPermissions = configPermissions
    )

/**
 * Calls native device camera app for taking photo.
 * @param onTaken - member function of calling Fragment that returns taken photo File.
 * @param param - will be preserved and return as second param in [onTaken] callback. Must be [Serializable] or [Parcelable]
 * @param configPermissions - Builder for permission denied callbacks
 */
inline fun <reified T> Fragment.takePhotoFromCamera(
    onTaken: KFunction2<File, T, Unit>,
    param: T,
    noinline configPermissions: (PermissionConfig.() -> Unit) = {}
) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.CAMERA,
        callback = onTaken.asCallback(param),
        configPermissions = configPermissions
    )
