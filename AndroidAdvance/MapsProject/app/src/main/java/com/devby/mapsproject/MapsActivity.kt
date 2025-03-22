package com.devby.mapsproject

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.devby.mapsproject.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdate
import com.google.android.material.snackbar.Snackbar
import java.util.Locale
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMapLongClickListener {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationManager: LocationManager
    private lateinit var locationListener: LocationListener
    private lateinit var permissionLauncher: ActivityResultLauncher<String>

    var following: Boolean? = null
    private lateinit var sharedPreferences: SharedPreferences


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        registerLauncher()
        sharedPreferences = getSharedPreferences("com.devby.mapsproject", MODE_PRIVATE)
        following = false
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnMapLongClickListener(this)
        /*
            39.91026292812899, 32.750490897453915
            val btkAkademi = LatLng(39.91026292812899, 32.750490897453915)
            mMap.addMarker(MarkerOptions().position(btkAkademi).title("BTK Akademi"))
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(btkAkademi,10f))
         */
        locationManager = this.getSystemService(LOCATION_SERVICE) as LocationManager
        locationListener = object : LocationListener {
            override fun onLocationChanged(location: Location) {
                following = sharedPreferences.getBoolean("following", false)
                if (!following!!) {
                    mMap.clear()
                    val userLocation = LatLng(location.latitude, location.longitude)
                    mMap.addMarker(MarkerOptions().position(userLocation).title("Konumunuz!"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 14f))
                    sharedPreferences.edit().putBoolean("following", true).apply()
                }


            }
        }
        if (ContextCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
                Snackbar.make(
                    binding.root,
                    "Konumunu Almak için izin gerekli",
                    Snackbar.LENGTH_INDEFINITE
                ).setAction(
                    "İzin Ver!"
                ) {
                    permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                }.show()
            } else {
                permissionLauncher.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)

            }
        } else {
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                0,
                0f,
                locationListener
            )
            val lastKnownLocation =
                locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            if (lastKnownLocation != null) {
                val lastKnownLatLng =
                    LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastKnownLatLng, 14f))
            }

        }
    }

    private fun registerLauncher() {
        permissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { result ->
                if (result) {
                    if (ContextCompat.checkSelfPermission(
                            this@MapsActivity,
                            android.Manifest.permission.ACCESS_FINE_LOCATION
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            0,
                            0f,
                            locationListener
                        )
                        val lastKnownLocation =
                            locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                        if (lastKnownLocation != null) {
                            val lastKnownLatLng =
                                LatLng(lastKnownLocation.latitude, lastKnownLocation.longitude)
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastKnownLatLng, 14f))
                        }
                    }
                } else {
                    Toast.makeText(this@MapsActivity, "İzne İhtiyacımız var!", Toast.LENGTH_SHORT)
                        .show()
                }

            }
    }

    @SuppressLint("NewApi")
    override fun onMapLongClick(location: LatLng) {
        mMap.clear()
        //println("Latitude: " + location.latitude + "Longititude: " + location.longitude)
        //Geocoder
        val geocoder = Geocoder(this, Locale.getDefault())
        var address = ""
        try {
            geocoder.getFromLocation(
                location.latitude,
                location.longitude,
                1,
                Geocoder.GeocodeListener { adressList ->
                    val firstAddress = adressList.first()
                    val countryName = firstAddress.countryName
                    val streetName = firstAddress.thoroughfare
                    //...
                    address += countryName
                    address + streetName
                    println(address)
                })


        } catch (e: Exception) {
            e.printStackTrace()
        }
        mMap.addMarker(MarkerOptions().position(location))
    }
}