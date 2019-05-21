package com.idapgroup.tnt.take

class PermissionConfig {

    internal var onDenied: (() -> Unit)? = null
    internal var onPermanentlyDenied:  (() -> Unit)? = null

    fun onDenied(block: () -> Unit) {
        this.onDenied = block
    }

    fun onPermanentlyDenied(block: () -> Unit) {
        this.onPermanentlyDenied = block
    }
}