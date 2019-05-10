package com.idapgroup.tnt

import android.net.Uri
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.idapgroup.tnt.take.Destination
import com.idapgroup.tnt.take.ImageSource
import com.idapgroup.tnt.take.ResultFragment
import com.idapgroup.tnt.take.assertMember
import java.io.File
import kotlin.reflect.KFunction1


fun FragmentActivity.takePhotoFromCamera(onTaken: KFunction1<File, Unit>) {
    assertMember(onTaken)

    supportFragmentManager.beginTransaction()
        .add(
            ResultFragment.newInstance(Destination.ACTIVITY, ImageSource.CAMERA, onTaken.name),
            null
        ).commit()

}

fun FragmentActivity.pickImageFromGallery(onTaken: KFunction1<Uri, Unit>) {
    assertMember(onTaken)

    supportFragmentManager.beginTransaction()
        .add(
            ResultFragment.newInstance(Destination.ACTIVITY, ImageSource.GALLERY, onTaken.name),
            null
        ).commit()

}

fun Fragment.takePhotoFromCamera(onTaken: KFunction1<File, Unit>) {
    assertMember(onTaken)

    childFragmentManager.beginTransaction()
        .add(
            ResultFragment.newInstance(Destination.FRAGMENT, ImageSource.CAMERA, onTaken.name),
            null
        ).commit()

}

fun Fragment.pickImageFromGallery(onTaken: KFunction1<Uri, Unit>) {
    assertMember(onTaken)

    childFragmentManager.beginTransaction()
        .add(
            ResultFragment.newInstance(Destination.FRAGMENT, ImageSource.GALLERY, onTaken.name),
            null
        ).commit()
}