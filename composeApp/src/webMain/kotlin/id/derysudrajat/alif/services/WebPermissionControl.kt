package id.derysudrajat.alif.services

import kotlinx.browser.window

class WebPermissionControl : AppPermissionControl {

    override suspend fun checkAndRequestLocation() {

    }

    override fun openSettings() {
        window.alert("Please check your browser settings.")
    }
}