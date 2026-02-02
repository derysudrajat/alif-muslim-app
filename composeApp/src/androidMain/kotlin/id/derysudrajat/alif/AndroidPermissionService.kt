package id.derysudrajat.alif

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController
import id.derysudrajat.alif.services.PermissionService
import id.derysudrajat.alif.services.PermissionStatus

class AndroidPermissionService(
    private val context: Context,
    private val controller: PermissionsController
) : PermissionService {

    override suspend fun checkLocationPermission(): PermissionStatus {
        val hasFine = controller.isPermissionGranted(Permission.LOCATION)
        val hasCoarse = controller.isPermissionGranted(Permission.COARSE_LOCATION)
        return if (hasFine || hasCoarse) {
            PermissionStatus.GRANTED
        } else {
            PermissionStatus.DENIED
        }
    }

    override suspend fun requestLocationPermission() {
        try {
            controller.providePermission(Permission.LOCATION)
        } catch (e: Exception) {
            println("Permission denied: ${e.message}")
        }
    }

    override fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    }
}