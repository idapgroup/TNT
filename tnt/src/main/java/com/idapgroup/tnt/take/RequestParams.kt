package com.idapgroup.tnt.take

import android.net.Uri
import java.io.File
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

internal typealias Action = () -> Unit

internal class PermissionParams(
    val onDenied: Action?,
    val onPermanentlyDenied: Action?
)

class RequestParams {

    @PublishedApi
    internal var callback: Callback? = null
    internal var permissionParams: PermissionParams? = null

    /**
     * @param func - result receiver, member method of target component.
     */
    @JvmName("callbackWithFile")
    fun callback(func: KFunction1<File, Unit>) {
        callback = func.asCallback()
    }

    /**
     * @param func - result receiver, member method of target component.
     */
    @JvmName("callbackWithUri")
    fun callback(func: KFunction1<Uri, Unit>) {
        this.callback = func.asCallback()
    }

    /**
     * @param func - result receiver, member method of target component.
     */
    @JvmName("callbackWithFileAndParam")
    inline fun <reified T> callback(func: KFunction2<File, T, Unit>, param: T) {
        this.callback = func.asCallback(param)
    }

    /**
     * @param func - result receiver, member method of target component.
     */
    @JvmName("callbackWithUriAndParam")
    inline fun <reified T> callback(func: KFunction2<Uri, T, Unit>, param: T) {
        this.callback = func.asCallback(param)
    }

    /**
     * @param onDenied - will be called when permissions request denied
     * @param onPermanentlyDenied - will be called when permissions request permanently denied
     */
    fun permissions(onDenied: Action? = null, onPermanentlyDenied: Action? = null) {
        this.permissionParams = PermissionParams(
            onDenied = onDenied,
            onPermanentlyDenied = onPermanentlyDenied
        )
    }
}