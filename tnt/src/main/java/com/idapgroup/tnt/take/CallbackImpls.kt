package com.idapgroup.tnt.take

import android.net.Uri
import java.io.File
import java.lang.reflect.Method

@PublishedApi
internal open class CallbackImpl(
    protected val name: String,
    protected val resultClass: Class<*>
): Callback {

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
}

@PublishedApi
internal class CallbackWithParamImpl(
    name: String,
    resultClass: Class<*>,
    private val paramClass: Class<*>,
    private val param: Any?
): CallbackImpl(name, resultClass) {

    override fun call(target: Any, result: Any) {
        getJavaMethod(target).invoke(target, mapResult(result), param)
    }

    override fun getJavaMethod(target: Any): Method {
        val method = target::class.java.getDeclaredMethod(name, resultClass, paramClass)
        method.isAccessible = true
        return method
    }
}