package id.derysudrajat.alif.services

import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController

class MokoPermissionControl(
    private val controller: PermissionsController
) : AppPermissionControl {

    override suspend fun checkAndRequestLocation() {
        // Delegates to Moko
        controller.providePermission(Permission.LOCATION)
    }

    override fun openSettings() {
        controller.openAppSettings()
    }
}