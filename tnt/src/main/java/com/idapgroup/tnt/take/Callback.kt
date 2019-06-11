package com.idapgroup.tnt.take

import android.os.Bundle
import kotlin.reflect.KClass
import kotlin.reflect.KFunction1
import kotlin.reflect.KFunction2

interface Callback {

    fun assertIsOwner(target: Any)

    fun call(target: Any, result: Any)

    fun toBundle(): Bundle
}

@PublishedApi
@JvmName("asCallback1")
internal inline fun <reified R> KFunction1<R, Unit>.asCallback() =
    CallbackImpl(name, javaClass<R>())

@PublishedApi
@JvmName("asCallback2")
internal inline fun <reified R, reified T> KFunction2<R, T, Unit>.asCallback(param: T): Callback =
    CallbackWithParamImpl(name, javaClass<R>(), javaClass<T>(), param)

@PublishedApi
internal inline fun <reified T> javaClass(): Class<*> =
    (T::class as KClass<*>).javaPrimitiveType ?: T::class.java