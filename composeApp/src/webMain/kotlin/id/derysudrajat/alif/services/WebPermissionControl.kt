package id.derysudrajat.alif.services

import dev.icerock.moko.permissions.PermissionsController
import kotlinx.browser.window

class WebPermissionControl : AppPermissionControl {

    override val controller: PermissionsController
        get() = PermissionsController()

    override suspend fun checkAndRequestLocation() {

    }

    override fun openSettings() {
        window.alert("Please check your browser settings.")
    }
}