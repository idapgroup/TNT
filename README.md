Take And Transform
============
Library created for simple taking images/photos from gallery or camera source and then apply transoformations such as: crop, fit, rotate etc. Based on reflection under the hood.

Download
--------

[ ![Download](https://api.bintray.com/packages/idapgroup/kotlin/TNT/images/download.svg?version=1.0.0) ](https://bintray.com/idapgroup/kotlin/TNT/1.0.0/link)

Add repository to your root `build.gradle`

```groovy
repositories {
    jcenter()
}
```

```groovy
dependencies {
  implementation 'com.idapgroup:tnt:latest-version'
}
```

TAKE usage sample
-------------

Activity and Fragment have next extension functions:
__pickImageFromGallery(onTaken: KFunction1<Uri, Unit>)__ - opens native android image picker and returns selected image Uri.
* `onTaken` - member function of this Activity/Fragment that takes Uri as an argument.

__takePhotoFromCamera(onTaken: KFunction1<File, Unit>)__ - opens native android camera and returns taken photo File.
* `onTaken` - member function of tthis Activity/Fragment that takes File as an argument.

```kotlin
class ExampleActivity : Activity {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galleryButton.setOnClickListener {
            pickImageFromGallery(::imagePicked)
        }
        galleryButton.setOnClickListener {
            takePhotoFromCamera(::photoTaken)
        }
    }
    
    fun imagePicked(imageUri: Uri) {
        // original uri of image
    }
    
    fun photoTaken(file: File) {
        // Original file in internal storage from Android camera. 
        // do smth with file (send to server, remove etc.)
    }

}
```

__Remember: Use only member functions of Activity/Fragment.__

TRANSFORM usage sample
-------------

Tramsormation functions:
* __resize(width, height)__ - change origin size of bitmap.Scale type `cropCenter` selected by default 
* __square(size: Int?)__ - if size null - takes origin bitmap smaller side
* __rotate(degrees: Float)__ - rotates bitmap by selected degrees
* __background(color: Int)__ - set bitmap background for `fitStart`, `fitCenter`, `fitEnd` modes
* __cropStart()__ - crops bitmap from start position
* __cropEnd()__ - crops bitmap from end position
* __cropCenter()__ - scale the image uniformly (maintain the image's aspect ratio) so
that both dimensions (width and height) of the image will be equal
to or larger than the corresponding dimension of the view
(minus padding). The image is then centered in the view.
* __fitXY()__ - scale in X and Y independently, so that src matches dst exactly. This may change the
aspect ratio of the src.
* __fitStart()__ - compute a scale that will maintain the original src aspect ratio, but will also ensure
that src fits entirely inside dst. At least one axis (X or Y) will fit exactly. START
aligns the result to the left and top edges of dst.
* __fitCenter()__ - compute a scale that will maintain the original src aspect ratio, but will also ensure
that src fits entirely inside dst. At least one axis (X or Y) will fit exactly. The
result is centered inside dst.
* __fitEnd()__ - compute a scale that will maintain the original src aspect ratio, but will also ensure
that src fits entirely inside dst. At least one axis (X or Y) will fit exactly. END
aligns the result to the right and bottom edges of dst.
* 
```kotlin
val bitmap = someBitmap.transform {
    resize(100, 200)
    cropEnd()
    rotate(43f)
    background(Color.RED)
}
```


