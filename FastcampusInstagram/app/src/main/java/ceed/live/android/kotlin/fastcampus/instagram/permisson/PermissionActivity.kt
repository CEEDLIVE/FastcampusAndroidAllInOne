package ceed.live.android.kotlin.fastcampus.instagram.permisson

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ceed.live.android.kotlin.fastcampus.instagram.BaseActivity
import ceed.live.android.kotlin.fastcampus.instagram.R
import ceed.live.android.kotlin.fastcampus.instagram.logger.Log4k

class PermissionActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission)

        val cameraPermissionCheck = ContextCompat.checkSelfPermission(
            this@PermissionActivity, android.Manifest.permission.CAMERA
        )

        if (cameraPermissionCheck != PackageManager.PERMISSION_GRANTED) {
            // 권한이 없는 경우
            Log4k.e("권한이 없는 경우")
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                1000)
        } else {
            // 권한이 있는 경우
            Log4k.e("권한이 있는 경우")
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 1000) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log4k.d("승낙")
            } else {
                Log4k.d("거절")
            }
        }
    }
}