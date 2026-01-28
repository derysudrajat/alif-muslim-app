package id.derysudrajat.alif.services

import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionsController

class MokoPermissionControl(
    override val controller: PermissionsController
) : AppPermissionControl {

    override suspend fun checkAndRequestLocation() {
        controller.providePermission(Permission.LOCATION)
    }

    override fun openSettings() {
        controller.openAppSettings()
    }
}