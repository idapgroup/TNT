package com.idapgroup.tnt.take

import java.io.Serializable

sealed class Source : Serializable {
    class Camera(val type: MediaType) : Source()
    class Gallery(val type: MimeType) : Source()
}