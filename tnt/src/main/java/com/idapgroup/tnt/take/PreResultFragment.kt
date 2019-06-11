package com.idapgroup.tnt.take

import androidx.fragment.app.FragmentManager

@PublishedApi
internal fun Any.addResultFragment(
    fragmentManager: FragmentManager,
    target: Target,
    source: ImageSource,
    block: RequestParams.() -> Unit
) {
    val params = RequestParams().apply(block)

    val callback = params.callback ?: throw RuntimeException("Callback in the request params must be assigned")
    callback.assertIsOwner(this)

    val fragment = ResultFragment.newInstance(
        target, source, callback, params.permissionParams)

    fragmentManager.beginTransaction()
        .add(fragment, null)
        .commit()
}