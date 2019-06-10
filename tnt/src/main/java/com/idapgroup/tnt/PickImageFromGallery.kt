package com.idapgroup.tnt

import android.net.Uri
import android.os.Parcelable
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.idapgroup.tnt.take.*
import com.idapgroup.tnt.take.Target
import com.idapgroup.tnt.take.addResultFragment
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

/**
 * Calls android's native image picker
 * @param onPicked - member function of calling [FragmentActivity] that returns picked image Uri
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
        callback = onPicked.asCallback(),
        configPermissions = configPermissions
    )

/**
 * Calls android's native image picker
 * @param onPicked - member function of calling [FragmentActivity] that returns picked image Uri
 * @param param - will be preserved and return as second param in [onPicked] callback. Must be [Serializable] or [Parcelable]
 * @param configPermissions - Builder for permission denied callbacks, if null - no permission request called.
 */
inline fun <reified T> FragmentActivity.pickImageFromGallery(
    onPicked: KFunction2<Uri, T, Unit>,
    param: T,
    noinline configPermissions: ((PermissionConfig.() -> Unit))? = {}
) =
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.ACTIVITY,
        source = ImageSource.GALLERY,
        callback = onPicked.asCallback(param),
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
        callback = onPicked.asCallback(),
        configPermissions = configPermissions
    )

/**
 * Calls native android image picker
 * @param onPicked - member function of calling Fragment that returns picked image Uri
 * @param param - will be preserved and return as second param in [onPicked] callback. Must be [Serializable] or [Parcelable]
 * @param configPermissions - Builder for permission denied callbacks, if null - no permission request called.
 */
inline fun <reified T> Fragment.pickImageFromGallery(
    onPicked: KFunction2<Uri, T, Unit>,
    param: T,
    noinline configPermissions: ((PermissionConfig.() -> Unit))? = { }
) =
    addResultFragment(
        fragmentManager = childFragmentManager,
        target = Target.FRAGMENT,
        source = ImageSource.GALLERY,
        callback = onPicked.asCallback(param),
        configPermissions = configPermissions
    )
