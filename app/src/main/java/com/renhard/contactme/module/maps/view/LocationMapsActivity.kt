package com.renhard.contactme.module.maps.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.renhard.contactme.R
import com.renhard.contactme.databinding.ActivityLocationMapsBinding
import com.renhard.contactme.module.main.model.AddressModel
import com.renhard.contactme.utils.extension.getSerializable

class LocationMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityLocationMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLocationMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = getString(R.string.address_location)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        val address = intent.getSerializable("address", AddressModel::class.java)
        address?.let { address ->
            mMap = googleMap

            val addressGeo = LatLng(address.geo.lat.toDouble(), address.geo.lng.toDouble())
            val fullAdress = "${address.street} St, ${address.suite}, ${address.city}, ${address.zipcode}"
            mMap.addMarker(MarkerOptions().position(addressGeo).title(fullAdress))?.showInfoWindow()

            mMap.moveCamera(CameraUpdateFactory.newLatLng(addressGeo))
            mMap.animateCamera(CameraUpdateFactory.zoomTo(2F))
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return super.onSupportNavigateUp()
    }
}