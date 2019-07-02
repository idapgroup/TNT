package com.idapgroup.tnt.take

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import com.idapgroup.argumentdelegate.argumentDelegate
import java.io.File

private const val GALLERY_REQUEST = 101
private const val CAMERA_REQUEST = 102
private const val CAMERA_PERMISSION_REQUEST = 201
private const val READ_STORAGE_PERMISSION_REQUEST = 202

@PublishedApi
internal class ResultFragment : Fragment() {

    companion object {
        fun newInstance(
            target: Target,
            action: Source<*>,
            callback: Callback,
            permissionParams: PermissionParams?
        ) = ResultFragment().apply {
            arguments = bundleOf(
                "target" to target,
                "action" to action,
                "callbackBundle" to callback.toBundle()
            )
            this.permissionParams = permissionParams
        }
    }

    private val target: Target by argumentDelegate()
    private val action: Source<*> by argumentDelegate()
    private val callbackBundle: Bundle by argumentDelegate()

    private val callback: Callback by lazy { toCallback(callbackBundle) }

    private var permissionParams: PermissionParams? = null

    private var started = false
    private var pendingResult: Any? = null
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
        if (started.not()) {
            started = true
            when (val a = action) {
                is Source.Camera -> take(a.type)
                is Source.Gallery -> pick(a.type)
            }
        } else {
            pendingResult?.let(::handleResultAndFinish)
        }
    }

    private fun pick(type: MimeType) {
        if (permissionParams == null || context!!.isPermissionGranted(Manifest.permission.READ_EXTERNAL_STORAGE)) {
            pick(
                type,
                GALLERY_REQUEST,
                ::startActivityForResult
            )
        } else {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_STORAGE_PERMISSION_REQUEST)
        }
    }

    private fun take(type: CaptureType) {
        if (context!!.isPermissionGranted(Manifest.permission.CAMERA)) {
            photoFile = take(
                context!!,
                type,
                CAMERA_REQUEST,
                ::startActivityForResult
            )
        } else {
            requestPermissions(arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(grantResults.isRequestGranted()) {
            when(requestCode) {
                CAMERA_PERMISSION_REQUEST -> take(action.type as CaptureType)
                READ_STORAGE_PERMISSION_REQUEST -> pick(action.type as MimeType)
            }
        } else {
            if(shouldShowRequestPermissionRationale(permissions[0])) {
                permissionParams?.onDenied?.invoke()
            } else {
                permissionParams?.onPermanentlyDenied?.invoke()
            }
            finish()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return

        val result = when (requestCode) {
            GALLERY_REQUEST -> data!!.data!!
            CAMERA_REQUEST -> photoFile!!
            else -> throw  RuntimeException("Are you crazy?")
        }
        tryHandleResultAndFinish(result)
    }

    private fun tryHandleResultAndFinish(result: Any) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            handleResultAndFinish(result)
        } else {
            pendingResult = result
        }
    }

    private fun handleResultAndFinish(result: Any) {
        val target: Any = when (target) {
            Target.ACTIVITY -> activity!!
            Target.FRAGMENT -> parentFragment!!
        }
        callback.call(target, result)
        finish()
    }

    private fun finish() {
        fragmentManager!!.beginTransaction()
            .remove(this)
            .commit()
    }
}

private fun IntArray.isRequestGranted() = this.getOrNull(0) == PackageManager.PERMISSION_GRANTED

private fun Context.isPermissionGranted(permission: String) =
    checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED