package com.ianpedraza.geolocationpermissions

interface CheckPermissionsManager {
    fun onAlreadyGranted()
    fun onRequestPermissionRationale()
    fun onRequest(permission: String)
}