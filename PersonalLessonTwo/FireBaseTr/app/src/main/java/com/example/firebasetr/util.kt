package com.example.firebasetr

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.startActivityForResult
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.TedPermission

fun Context.toast(string: String) {
    Toast.makeText(this, string, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.setImagePermission(AfterLogic: () -> Unit) {
    val permission = object : PermissionListener {
        override fun onPermissionGranted() {
            AfterLogic()
        }

        override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
            toast("[프로필 이미지 업로드]\n권한을 허용하셔야 로그인이 가능합니다.")
        }
    }

    TedPermission.with(this)
        .setPermissionListener(permission)
        .setRationaleMessage("갤러리 권한을 허용해주세요")
        .setDeniedMessage("권한이 거부되었습니다. [앱 설정] -> [권한] 항목에서 이용해주세요")
        .setPermissions(
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        .check()
}
