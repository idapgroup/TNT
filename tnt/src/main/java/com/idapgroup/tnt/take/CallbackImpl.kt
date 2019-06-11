package com.idapgroup.tnt.take

import android.net.Uri
import android.os.Bundle
import androidx.core.os.bundleOf
import java.io.File
import java.lang.reflect.Method

@PublishedApi
internal open class CallbackImpl: Callback {

    protected val name: String
    protected val resultClass: Class<*>

    constructor(name: String, resultClass: Class<*>) {
        this.name = name
        this.resultClass = resultClass
    }

    constructor(bundle: Bundle) {
        this.name = bundle["name"] as String
        this.resultClass = bundle["resultClass"] as Class<*>
    }

    override fun assertIsOwner(target: Any) {
        try {
            getJavaMethod(target)
        } catch (e: NoSuchMethodException) {
            val targetClass = target::class.java.simpleName
            throw IllegalArgumentException(
                "'$name' is not a member method of $targetClass. User this@$targetClass::$name")
        }
    }

    override fun call(target: Any, result: Any) {
        getJavaMethod(target).invoke(target, mapResult(result))
    }

    @Throws(NoSuchMethodException::class)
    protected open fun getJavaMethod(target: Any): Method {
        val method = target::class.java.getDeclaredMethod(name, resultClass)
        method.isAccessible = true
        return method
    }

    protected fun mapResult(result: Any): Any =
        if (result is File && resultClass === Uri::class.java) {
            Uri.fromFile(result)
        } else {
            result
        }

    override fun toBundle(): Bundle =
            bundleOf(
                "name" to name,
                "resultClass" to resultClass
            )
}

@PublishedApi
internal class CallbackWithParamImpl : CallbackImpl {

    private val paramClass: Class<*>
    private val paramValue: Any?

    constructor(
        name: String,
        resultClass: Class<*>,
        paramClass: Class<*>,
        paramValue: Any?
    ) : super(name = name, resultClass = resultClass) {
        this.paramClass = paramClass
        this.paramValue = paramValue
    }

    constructor(bundle: Bundle) : super(bundle) {
        this.paramClass = bundle["paramClass"] as Class<*>
        this.paramValue = bundle["paramValue"]
    }

    override fun call(target: Any, result: Any) {
        getJavaMethod(target).invoke(target, mapResult(result), paramValue)
    }

    override fun getJavaMethod(target: Any): Method {
        val method = target::class.java.getDeclaredMethod(name, resultClass, paramClass)
        method.isAccessible = true
        return method
    }

    override fun toBundle(): Bundle =
        super.toBundle().apply {
            putAll(bundleOf(
                "paramClass" to paramClass,
                "paramValue" to paramValue
            ))
        }
}

private fun isCallbackWithParam(bundle: Bundle): Boolean = bundle["paramClass"] != null

internal fun toCallback(bundle: Bundle): Callback =
        if (isCallbackWithParam(bundle)) {
            CallbackWithParamImpl(bundle)
        } else {
            CallbackImpl(bundle)
        }