package com.ianpedraza.geolocationpermissions

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

object CheckPermissionsUtil {

    fun check(
        activity: AppCompatActivity,
        permission: String,
        permissionsManager: CheckPermissionsManager,
    ) {
        when {
            ContextCompat.checkSelfPermission(
                activity,
                permission
            ) == PackageManager.PERMISSION_GRANTED -> {
                permissionsManager.onAlreadyGranted()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(activity, permission) -> {
                permissionsManager.onRequestPermissionRationale()
            }
            else -> {
                permissionsManager.onRequest(permission)
            }
        }
    }

}