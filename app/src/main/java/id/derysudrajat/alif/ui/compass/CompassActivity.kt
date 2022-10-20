package id.derysudrajat.alif.ui.compass

import android.annotation.SuppressLint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.location.Location
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import id.derysudrajat.alif.compose.page.CompassPage
import id.derysudrajat.alif.compose.ui.theme.AlifTheme
import id.derysudrajat.alif.data.model.RotationTarget
import id.derysudrajat.alif.utils.LocationUtils
import id.derysudrajat.alif.utils.LocationUtils.checkLocationPermission


class CompassActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var currentLocation: Location
    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    private var currentDegree = 0f
    private var currentDegreeNeedle = 0f

    private val model: CompassViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlifTheme {
                CompassPage(model.isFacingQibla,
                    model.qilbaRotation,
                    model.compassRotation,
                    model.locationAddress,
                    goToBack = { finish() },
                    refreshLocation = {
                        if (this::sensorManager.isInitialized) sensorManager.unregisterListener(this)
                        getLocation()
                    })
            }
        }
        getLocation()
    }

    private fun getLocation() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        checkLocationPermission { requestLocationPermission() }
        fusedLocationClient.lastLocation.addOnSuccessListener {
            it?.let { location ->
                currentLocation = location
                model.getLocationAddress(this, currentLocation)
                sensorManager = getSystemService(SENSOR_SERVICE) as SensorManager
                sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION)
                sensorManager.registerListener(
                    this, sensor, SensorManager.SENSOR_DELAY_GAME
                )
            }
        }
    }

    @SuppressLint("NewApi")
    fun requestLocationPermission() {
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions -> LocationUtils.handlePermission(permissions) }
        LocationUtils.launchPermission(locationPermissionRequest)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        val degree = event?.values?.get(0) ?: 0f
        val destinationLoc = Location("service Provider").apply {
            latitude = 21.422487
            longitude = 39.826206
        }

        var bearTo: Float = currentLocation.bearingTo(destinationLoc)
        if (bearTo < 0) bearTo += 360
        var direction: Float = bearTo - degree
        if (direction < 0) direction += 360

        val isFacingQibla = direction in 359.0..360.0 || direction in 0.0..1.0

        val qiblaRoation = RotationTarget(currentDegreeNeedle, direction)
        currentDegreeNeedle = direction
        val compassRotation = RotationTarget(currentDegree, -degree)
        currentDegree = -degree
        model.updateCompass(qiblaRoation, compassRotation, isFacingQibla)
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onDestroy() {
        super.onDestroy()
        sensorManager.unregisterListener(this)
    }
}