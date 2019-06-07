package com.idapgroup.tnt.take

import androidx.fragment.app.FragmentManager

@PublishedApi
internal fun Any.addResultFragment(
    fragmentManager: FragmentManager,
    target: Target,
    source: ImageSource,
    callback: Callback,
    configPermissions: (PermissionConfig.() -> Unit)?
) {
    callback.assertIsOwner(this)

    val permissionCallbacks = configPermissions?.let { PermissionConfig().apply(it) }

    val fragment = ResultFragment.newInstance(
        target, source, callback, permissionCallbacks)

    fragmentManager.beginTransaction()
        .add(fragment, null)
        .commit()
}