package com.idapgroup.tnt.util

import java.io.ByteArrayOutputStream
import java.io.NotSerializableException
import java.io.ObjectOutputStream


internal fun assertSerializable(any: Any) {
    val stream = ObjectOutputStream(ByteArrayOutputStream())
    try {
        stream.writeObject(any)
    } catch (e: NotSerializableException) {
        throw RuntimeException("""
            |Seems that you are trying to use local non Serializable objects inside callback.
            |Please, use only primitives or Serializable classes, for example:
            |
            |class User(var image: Uri? = null)
            |
            |class MyFragment : Fragment() {
            |    
            |    val user = User()
            |    
            |    fun takePhotoForUser() {
            |        val localUser = User()
            |        takeImageFromGallery { 
            |            user.image = it // OK!
            |            localUser.image = it // Wrong!! Runtime exception
            |        }
            |    }
            |    
            |}""".trimMargin()
        )
    } finally {
        stream.flush()
        stream.close()
    }
}