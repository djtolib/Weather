package com.tolib.weather
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.tolib.weather.ui.LocationUtils
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LocationUtilsTest {

    @Mock
    private lateinit var mockContext: Context

    @Mock
    private lateinit var mockLocationManager: LocationManager

    private lateinit var locationUtils: LocationUtils

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        locationUtils = LocationUtils
        `when`(mockContext.getSystemService(Context.LOCATION_SERVICE)).thenReturn(mockLocationManager)
    }

    @Test
    fun `getCurrentLocation should return null when permission is not granted`() {
        `when`(mockContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(PackageManager.PERMISSION_DENIED)

        val result = locationUtils.getCurrentLocation(mockContext)

        assertEquals(null, result)
    }

    @Test
    fun `getCurrentLocation should return null when location manager returns null`() {
        `when`(mockContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(PackageManager.PERMISSION_GRANTED)
        `when`(mockLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)).thenReturn(null)

        val result = locationUtils.getCurrentLocation(mockContext)

        assertEquals(null, result)
    }

    @Test
    fun `getCurrentLocation should return location when permission is granted and location manager returns a valid location`() {
        `when`(mockContext.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)).thenReturn(PackageManager.PERMISSION_GRANTED)
        val expectedLocation = Location("test")
        `when`(mockLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)).thenReturn(expectedLocation)

        val result = locationUtils.getCurrentLocation(mockContext)

        assertEquals(expectedLocation, result)
    }
}
