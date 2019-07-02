package com.idapgroup.jet

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.idapgroup.tnt.pickFromGallery
import com.idapgroup.tnt.take.CaptureType
import com.idapgroup.tnt.take.MimeType
import com.idapgroup.tnt.takeFromCamera
import com.idapgroup.tnt.transform.asDataSource
import com.idapgroup.tnt.transform.resize
import com.idapgroup.tnt.transform.transformAsBitmap
import kotlinx.android.synthetic.main.screen_sample.*
import java.util.*

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
            onTakePhoto()
        }
        takeVideo.setOnClickListener {
            onTakeVideo()
        }
    }

    private fun onPickVideo() {
        pickFromGallery(MimeType.Video.Any) {
            callback(func = ::onVideoTaken, param = Date())
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

    private fun onPickImage() {
        pickFromGallery(MimeType.Image.Any) {
            callback(func = ::onTaken, param = Date())
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

    private fun onTakePhoto() {
        takeFromCamera(CaptureType.Image) {
            callback(func = ::onTaken, param = Date())
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

    private fun onTakeVideo() {
        takeFromCamera(CaptureType.Video) {
            callback(func = ::onVideoTaken, param = Date())
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

    private fun onTaken(uri: Uri, date: Date) {
        Toast.makeText(context, "${date.hours}:${date.minutes}:${date.seconds}", Toast.LENGTH_LONG).show()

        val bitmap = uri.asDataSource(context!!)
            .transformAsBitmap {
                resize(max = 640)
            }
        imageView.setImageBitmap(bitmap)
    }

    private fun onVideoTaken(uri: Uri, date: Date) {
        Toast.makeText(context, "$uri", Toast.LENGTH_LONG).show()
    }

    private fun showToast(msg: String) {
        Toast.makeText(context!!, msg, Toast.LENGTH_LONG).show()
    }
}