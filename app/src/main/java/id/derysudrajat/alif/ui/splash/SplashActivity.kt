package id.derysudrajat.alif.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import id.derysudrajat.alif.compose.page.SplashPage
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.ui.main.MainActivity
import id.derysudrajat.alif.utils.LocationUtils

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { AlifTheme { SplashPage() } }
        requestLocationPermission()
    }

    @SuppressLint("NewApi")
    fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            LocationUtils.handlePermission(permissions)
            Handler(mainLooper).postDelayed({
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }, 1000L)
        }
        LocationUtils.launchPermission(locationPermissionRequest)
    }
}