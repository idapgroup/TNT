package com.idapgroup.tnt.take

import java.io.Serializable

sealed class MimeType(val value: String) : Serializable {

    sealed class Image(value: String): MimeType(value){
        object Any : Image("image/*")
        class Custom(format: String) : Image("image/$format")
    }

    sealed class Video(value: String): MimeType(value){
        object Any : Video("video/*")
        class Custom(format: String) : Video("video/$format")
    }

    class Custom(value: String) : MimeType(value)

}