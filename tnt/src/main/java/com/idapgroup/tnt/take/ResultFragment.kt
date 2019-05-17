package com.idapgroup.tnt.take

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.idapgroup.argumentdelegate.argumentDelegate
import java.io.File
import kotlin.reflect.KClass

private const val GALLERY_REQUEST = 101
private const val CAMERA_REQUEST = 102
private const val CAMERA_PERMISSION_REQUEST = 201

internal class ResultFragment : Fragment() {

    companion object {
        fun newInstance(
            destination: Destination,
            action: ImageSource,
            funName: String
        ) = ResultFragment().apply {
            arguments = bundleOf(
                "destination" to destination,
                "action" to action,
                "funName" to funName
            )
        }
    }
    
    private class Result<out T: Any>(
        val value: T,
        val clazz: KClass<out T>
    )

    private val destination: Destination by argumentDelegate()
    private val action: ImageSource by argumentDelegate()
    private val funName: String by argumentDelegate()

    private var started = false
    private var pendingResult: Result<Any>? = null
    private var photoFile: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        started = savedInstanceState?.getBoolean("started") ?: false
        photoFile = savedInstanceState?.get("photoFile") as File?
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("started", started)
        outState.putSerializable("photoFile", photoFile)
    }

    override fun onStart() {
        super.onStart()
        if(started.not()) {
            started = true
            when(action) {
                ImageSource.CAMERA -> takePhoto()
                ImageSource.GALLERY -> pickImage(
                    GALLERY_REQUEST,
                    ::startActivityForResult
                )
            }
        } else {
            pendingResult?.let(::handleResultAndFinish)
        }
    }

    private fun takePhoto() {
        if(context!!.isPermissionGranted(Manifest.permission.CAMERA)) {
            photoFile = takePhoto(
                context!!,
                CAMERA_REQUEST,
                ::startActivityForResult
            )
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == CAMERA_PERMISSION_REQUEST && grantResults.isRequestGranted()) {
            takePhoto()
        } else {
            fragmentManager?.popBackStack()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode != Activity.RESULT_OK) return

        val result = when(requestCode) {
            GALLERY_REQUEST -> Result(data!!.data!!, Uri::class)
            CAMERA_REQUEST -> Result(photoFile!!, File::class)
            else -> throw  RuntimeException("Are you crazy?")
        }
        tryHandleResultAndFinish(result)
    }

    private fun tryHandleResultAndFinish(result: Result<Any>) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            handleResultAndFinish(result)
        } else {
            pendingResult = result
        }
    }

    private fun handleResultAndFinish(result: Result<Any>) {
        val target: Any = when(destination) {
            Destination.ACTIVITY -> activity!!
            Destination.FRAGMENT -> parentFragment!!
        }
        val method = target::class.java.getDeclaredMethod(funName, result.clazz.java)
        method.isAccessible = true
        method.invoke(target, result.value)
        
        fragmentManager?.popBackStack()
    }
}

private fun IntArray.isRequestGranted() = this.getOrNull(0) == PackageManager.PERMISSION_GRANTED

private fun Context.isPermissionGranted(permission: String) =
    checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED