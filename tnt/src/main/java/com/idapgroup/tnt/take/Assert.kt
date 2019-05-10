package com.idapgroup.tnt.take

import kotlin.reflect.KFunction1

@PublishedApi
internal inline fun <reified T> Any.assertMember(function: KFunction1<T, Unit>) {
    try {
        this::class.java.getDeclaredMethod(function.name, T::class.java)
    } catch (e: NoSuchMethodException) {
        throw IllegalArgumentException("'${function.name}' is not a member function of ${this::class.simpleName}")
    }
}