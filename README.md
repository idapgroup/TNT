Take And Transform
============
Library created for simple taking images/photos/videos from gallery or camera source and then apply transformations such as crop, fit, rotate, etc.

Download
--------

[ ![Download](https://api.bintray.com/packages/idapgroup/kotlin/TNT/images/download.svg?version=3.0.1) ](https://bintray.com/idapgroup/kotlin/TNT/3.0.1/link)

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

Activity and Fragment have the next extension functions for most common cases:  
* __takeImageFromGallery__, __takeVideoFromGallery__ -  opens android picker and returns taken photo/video Uri.  

* __takeImageFromCamera__, __takeVideoFromCamera__  -  opens android camera and returns captured photo/video Uri.  

```kotlin
class ExampleActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galleryButton.setOnClickListener {
            takeImageFromGallery {
                proccessImageUri(it)
            }
        }
        cameraButton.setOnClickListener {
            takeVideoFromCamera {
                proccessVideoUri(it)
            }
        }
    }

}
```

### Advanced usage

"As a developer, I don't want to write boilerplate code for permissions or saving related data to instance state."

Functions that were described above are just an extension functions for `take`.  
__take(source: Source<*>,  permissions: PermissionParams.() -> Unit = {},  callback: F.(Uri) -> Unit)__

Where:

*  __source__ -  one of Gallery(MimeType) or Camera(MediaType);  
* __permission__ - dispatches permission denied events ; 
*  __callback__  - receiver for selected data Uri.

```kotlin
class ExampleActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galleryButton.setOnClickListener {
            take(
                source = Source.Gallery(MimeType.Video.Any),
                permissions = {
                    onDenied { showToast("Permission denied") }
                    onPermanentlyDenied { showToast("Permission permanently denied") }
                }) {
                proccessVideoUri(it)
            }
        }
        cameraButton.setOnClickListener {
            take(
                source = Source.Camera(MediaType.Image),
                permissions = {
                    onDenied { showToast("Permission denied") }
                    onPermanentlyDenied { showToast("Permission permanently denied") }
                }) {
                proccessImageUri(it)
            }
        }
    }

}
```

#### Keep in mind: when you are trying to use local variables inside callback function it must be primitives or implements Serializable interface. Parcelable isn't supported yet.

```kotlin
open class User(var image: Uri? = null)

class SerializableUser : User(), Serializable

class MyFragment : Fragment() {
    
    val user = User()
    
    fun takePhotoForUser() {
        val localUser = User()
		val localSerializableUser = SerializableUser()
        takeImageFromGallery { 
            user.image = it // OK!
			localSerializableUser.image = it // OK!
            localUser.image = it // Wrong!! Runtime exception
        }
    }
    
}
```

TRANSFORM usage sample
-------------

Tramsormation functions:
* __resize(width, height, scaleType)__ - change origin size of bitmap.
* __square(size: Int?, scaleType)__ - if size null - takes origin bitmap smaller side
*  Supported scale types: `cropStart`, `cropCenter`, `cropEnd`, `fitStart`, `fitCenter`, `fitEnd`, `fitXY`

* __rotate(degrees: Float)__ - rotates bitmap by selected degrees
* __background(color: Int)__ - set bitmap background for `fitStart`, `fitCenter`, `fitEnd` modes
* __blur(radius, sampling)__
* __colorFilter(color)__

```kotlin
val bitmap = file.transformAsBitmap {
    resize(100, 200, cropEnd)
    rotate(43f)
    colorFilter(a = 89, r = 34, g = 120, b = 34)
    background(Color.RED)
    blur()
}
```


