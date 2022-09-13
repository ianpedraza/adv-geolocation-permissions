package com.ianpedraza.geolocationpermissions

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    /**
     * In order to use requestPermissionLauncher and let the system
     *   handle the request code it's needed
     *       - androidx.activity, version 1.2.0 or later.
     *       - androidx.fragment, version 1.3.0 or later.
     */
    private val requestPermissionLauncher by lazy {
        registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted: Boolean ->
            if (isGranted) {
                Timber.i("Granted")
            } else {
                Timber.i("Denied")
            }
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 189
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        CheckPermissionsUtil.check(this, ACCESS_FINE_LOCATION, permissionManager)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timber.i("Granted:onRequestPermissionsResult")
                } else {
                    Timber.i("Denied:onRequestPermissionsResult")
                }

                return
            }

            else -> {
                /* Ignore all other requests */
            }
        }
    }

    private val permissionManager = object : CheckPermissionsManager {
        override fun onAlreadyGranted() {
            Timber.i("Granted:Permission already granted")
        }

        override fun onRequestPermissionRationale() {
            Timber.i("Denied:shouldShowRequestPermissionRationale")
        }

        override fun onRequest(permission: String) {
            Timber.i("Denied:onRequest")
            requestPermissionLauncher.launch(permission)
            // requestPermissions(arrayOf(permission), MainActivity.PERMISSION_REQUEST_CODE)
        }
    }
}
