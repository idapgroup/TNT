package com.idapgroup.jet

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.idapgroup.tnt.pickImageFromGallery
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
            pickImageFromGallery(::onTaken, Date())
//            takePhotoFromCamera(onTaken = ::onTaken, configPermissions = {
//                onDenied {
//                    Toast.makeText(context!!, "denied", Toast.LENGTH_SHORT).show()
//                }
//                onPermanentlyDenied {
//                    Toast.makeText(context!!, "permanently denied", Toast.LENGTH_SHORT).show()
//                }
//            })
        }
    }

    fun onTaken(uri: Uri, date: Date) {
        Toast.makeText(context, "Date: $date", Toast.LENGTH_LONG).show()

        val bitmap = uri.asDataSource(context!!)
            .transformAsBitmap {
                resize(max = 640)
            }
        imageView.setImageBitmap(bitmap)
    }
}