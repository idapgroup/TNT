package com.idapgroup.tnt.take

typealias Action = () -> Unit

class PermissionParams {
    internal var onDenied: Action? = null
    internal var onPermanentlyDenied: Action? = null

    /** Will be called when permissions request denied */
    fun onDenied(action: Action) {
        this.onDenied = action
    }

    /** Will be called when permissions request permanently denied */
    fun onPermanentlyDenied(action: Action) {
        this.onPermanentlyDenied = action
    }
}