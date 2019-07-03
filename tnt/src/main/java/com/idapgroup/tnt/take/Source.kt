package com.idapgroup.tnt.take

import java.io.Serializable

sealed class Source<T>(val type: T) : Serializable {
    class Camera(type: MediaType) : Source<MediaType>(type)
    class Gallery(type: MimeType) : Source<MimeType>(type)
}