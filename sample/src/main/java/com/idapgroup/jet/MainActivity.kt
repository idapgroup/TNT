package com.idapgroup.jet

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.idapgroup.tnt.pickImageFromGallery
import com.idapgroup.tnt.transform.*
import kotlinx.android.synthetic.main.screen_sample.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragmentContainer, SampleFragment(), "tag_sample")
                .commit()
        }
    }
}

class SampleFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.screen_sample, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        imageView.setOnClickListener {
                        pickImageFromGallery(::onTaken)
//            takePhotoFromCamera(::take)
        }
    }

    fun onTaken(uri: Uri) {
        Glide.with(imageView).load(uri).into(imageView)
    }

    private fun take(file: File) {
        val bitmap = file.transformAsBitmap {
            square(600, cropCenter)
                colorFilter(100, 33, 100, 98)
                blur()
        }
        imageView.setImageBitmap(bitmap)
    }
}