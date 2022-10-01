package id.derysudrajat.alif.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import id.derysudrajat.alif.R
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.ui.main.MainActivity
import id.derysudrajat.alif.utils.LocationUtils

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlifTheme {
                Surface(
                    color = MaterialTheme.colors.primary,
                    modifier = Modifier.fillMaxSize(),
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_alif),
                        contentDescription = "App Icon",
                        modifier = Modifier.wrapContentSize()
                    )
                }
            }
        }
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

@Composable
@Preview
fun previewSplash() {
    AlifTheme {
        Surface(
            color = MaterialTheme.colors.primary,
            modifier = Modifier.fillMaxSize(),
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_alif),
                contentDescription = "App Icon",
                modifier = Modifier.wrapContentSize()
            )
        }
    }
}