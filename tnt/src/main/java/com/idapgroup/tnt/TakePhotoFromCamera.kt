package com.idapgroup.tnt

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.idapgroup.tnt.take.*
import com.idapgroup.tnt.take.Target
import com.idapgroup.tnt.take.addResultFragment
import java.io.File
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
        callback = onTaken.asCallback(),
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
        callback = onTaken.asCallback(),
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
        callback = onTaken.asCallback(),
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
        callback = onTaken.asCallback(),
        configPermissions = configPermissions
    )