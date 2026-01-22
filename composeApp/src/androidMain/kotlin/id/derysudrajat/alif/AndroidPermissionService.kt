package id.derysudrajat.alif

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import id.derysudrajat.alif.services.PermissionService
import id.derysudrajat.alif.services.PermissionStatus

class AndroidPermissionService(
    private val context: Context,
) : PermissionService {

    override suspend fun checkLocationPermission(): PermissionStatus {
        val hasFine =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
        val hasCoarse =
            ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)

        return if (hasFine == PackageManager.PERMISSION_GRANTED || hasCoarse == PackageManager.PERMISSION_GRANTED) {
            PermissionStatus.GRANTED
        } else {
            PermissionStatus.DENIED
        }
    }

    override suspend fun requestLocationPermission() {

    }

    override fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}