package com.idapgroup.jet

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.idapgroup.tnt.take
import com.idapgroup.tnt.take.MediaType
import com.idapgroup.tnt.take.MimeType
import com.idapgroup.tnt.take.Source
import com.idapgroup.tnt.takeImageFromGallery
import com.idapgroup.tnt.transform.asDataSource
import com.idapgroup.tnt.transform.resize
import com.idapgroup.tnt.transform.transformAsBitmap
import kotlinx.android.synthetic.main.screen_sample.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, SampleFragment(), null)
                .commit()
        }
    }
}

class SampleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.screen_sample, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        pickImage.setOnClickListener {
            onPickImage()
        }
        pickVideo.setOnClickListener {
            onPickVideo()
        }
        takePhoto.setOnClickListener {
            onTakePhoto(3)
        }
        takeVideo.setOnClickListener {
            onTakeVideo()
        }
    }

    private fun onPickImage() {
        takeImageFromGallery {
            onImageTaken(it, 5)
        }
    }

    private fun onPickVideo() {
        take(
            source = Source.Gallery(MimeType.Video.Any),
            permissions = {
                onDenied {  showToast( "Permission denied") }
                onPermanentlyDenied { showToast( "Permission permanently denied") }
            },
            callback = SampleFragment::onVideoTaken
        )
    }

    private fun onTakePhoto(position: Int) {
        take(
            source = Source.Camera(MediaType.Image),
            permissions = {
                onDenied {  showToast( "Permission denied") }
                onPermanentlyDenied { showToast( "Permission permanently denied") }
            }
        ) { uri: Uri ->
                onImageTaken(uri, position)
            }

    }

    private fun onTakeVideo() {
        take(
            source = Source.Camera(MediaType.Video),
            permissions = {
                onDenied {  showToast( "Permission denied") }
                onPermanentlyDenied { showToast( "Permission permanently denied") }
            }
        ) {
            onVideoTaken(it)
        }
    }

    private fun onImageTaken(uri: Uri, position: Int) {
        Toast.makeText(context, "Position: $position", Toast.LENGTH_LONG).show()

        val bitmap = uri.asDataSource(context!!)
            .transformAsBitmap {
                resize(max = 640)
            }
        imageView.setImageBitmap(bitmap)
    }

    private fun onVideoTaken(uri: Uri) {
        Toast.makeText(context, "Video: $uri", Toast.LENGTH_LONG).show()
    }

    private fun showToast(msg: String) {
        Toast.makeText(context!!, msg, Toast.LENGTH_LONG).show()
    }
}