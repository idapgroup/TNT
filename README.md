Take And Transform
============
Library created for simple taking images/photos/videos from gallery or camera source and then apply transoformations such as: crop, fit, rotate etc. Based on reflection under the hood.

Download
--------

[ ![Download](https://api.bintray.com/packages/idapgroup/kotlin/TNT/images/download.svg?version=2.0.1) ](https://bintray.com/idapgroup/kotlin/TNT/2.0.1/link)

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
__pickFromGallery(mimeType: MimeType, block: RequestParams.() -> Unit)__ - opens native android picker and returns selected Uri.
* `mimeType` - mime type of taking file;
* `block` -  [RequestParams](#request-params)  block.

__takeFromCamera(type: CaptureType,  block: RequestParams.() -> Unit)__ - opens native android camera and returns taken photo/video Uri.
* `type` - One of `Image` or `Video` capture type;
* `block` -  [RequestParams](#request-params) block.

## RequestParams

Builder function for additional picking/taking options.

**callback(func: KFunction1<Uri, Unit>)** - takes result receiver, member method of target component.

**permissions(onDenied: Action? = null, onPermanentlyDenied: Action? = null)** - permissions result handler.

```kotlin
class ExampleActivity : Activity {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        galleryButton.setOnClickListener {
			pickFromGallery(MimeType.Image.Any) {
				callback(func = ::onTaken)
				permissions(
					onDenied = {
						showToast( "Permission denied")
					},
					onPermanentlyDenied = {
						showToast( "Permission permanently denied")
					}
            	)
        	}
        }
        cameraButton.setOnClickListener {
			takeFromCamera(CaptureType.Video) {
				callback(func = ::onTaken)
				permissions(
					onDenied = {
						toast( "Permission denied")
					},
					onPermanentlyDenied = {
						toast( "Permission permanently denied")
					}
				)
			}
        }
    }
    
    fun onTaken(uri: Uri) {
        // original uri of file
    }

}
```

__Remember: Use only member functions of Activity/Fragment.__

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
(#some)
```kotlin
val bitmap = file.transformAsBitmap {
    resize(100, 200, cropEnd)
    rotate(43f)
    colorFilter(a = 89, r = 34, g = 120, b = 34)
    background(Color.RED)
    blur()
}
```


