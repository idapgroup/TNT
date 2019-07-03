package com.idapgroup.tnt

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.idapgroup.tnt.take.*
import com.idapgroup.tnt.take.Target
import com.idapgroup.tnt.take.addResultFragment

fun <F: Fragment> F.take(
    source: Source<*>,
    permissions: PermissionParams.() -> Unit = {},
    callback: F.(Uri) -> Unit
) {
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
    addResultFragment(
        fragmentManager = supportFragmentManager,
        target = Target.Fragment,
        source = source,
        permissions = permissions,
        callback = callback
    )
}