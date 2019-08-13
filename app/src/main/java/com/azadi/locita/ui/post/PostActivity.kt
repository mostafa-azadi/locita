package com.azadi.locita.ui.post

import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.InflateException
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.azadi.locita.R
import com.azadi.locita.base.BaseActivity
import com.azadi.locita.data.db.LocationModel
import com.azadi.locita.utility.LocationUtility.MessageOwnLocation
import com.azadi.locita.utility.LocationUtility.OwnLocation
import com.azadi.locita.utility.LocationUtility.OwnLocationListener
import com.azadi.locita.utility.PermissionUtility
import com.azadi.locita.utility.Utility
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_post.*


class PostActivity : BaseActivity(), OwnLocationListener, OnMapReadyCallback, LocationListener,
    View.OnClickListener {



    internal var map: GoogleMap? = null
    internal var latlng: LatLng? = null
    private var locationRequest: LocationRequest? = null
    internal var zoom: Int = 0

    var ownLocation: OwnLocation? = null
    var lati: Double? = null
    var longi: Double? = null
    internal var mapFragment: SupportMapFragment? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        title = "Add Location"
        ownLocation = OwnLocation(this)
        ownLocation?.setListener(this)


        imgMapMarker.setOnClickListener(this)
    }


    override fun onClick(v: View?) {

        if(Utility.isEmpty(edtPostTitle.text.toString())){
            Toast.makeText(this,"Please Enter a Title",Toast.LENGTH_LONG).show()
        }
        else{
            latlng = map!!.cameraPosition.target
            addLocationToDB(latlng!!)
            finish()
        }
    }

    private fun addLocationToDB(currentlatLng : LatLng) {
        if (database != null) {
            database!!.locationDao().add(LocationModel(
                edtPostTitle.text.toString(),
                currentlatLng.latitude.toString() ,
                currentlatLng.longitude.toString() )
            )
        }
    }

    override fun onLocationChanged(location: Location?) {

        try{
            if (lati != null && longi != null) {
                latlng = LatLng(lati!!, longi!!)
            }
            else{
                latlng = LatLng(getCurrentLocation().latitude, getCurrentLocation().longitude)
            }
            val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, zoom.toFloat())
            if (map != null) {
                map!!.animateCamera(cameraUpdate)
                map!!.addMarker(MarkerOptions().position(latlng!!))
            }
        }catch (e : Exception){
            Toast.makeText(this,"map not loaded",Toast.LENGTH_LONG).show()
        }
    }

    override fun onMapReady(gmap: GoogleMap?) {
        PermissionUtility.permissionCoarseLocation(this)
        PermissionUtility.permissionFineLocation(this)
        try {
            if (gmap != null) {
                map = gmap
                map!!.mapType = GoogleMap.MAP_TYPE_NORMAL
                map!!.isTrafficEnabled = true
                map!!.isIndoorEnabled = true
                map!!.isBuildingsEnabled = true
                map!!.uiSettings.isZoomControlsEnabled = true

                MapsInitializer.initialize(this)


                if (lati != null && longi != null) {
                    latlng = LatLng(lati!!, longi!!)
                }
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latlng, zoom.toFloat())
                map!!.animateCamera(cameraUpdate)
            }
        } catch (e: InflateException) {
            Toast.makeText(
                this, "Problems inflating the view !",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: NullPointerException) {
            Toast.makeText(
                this, "Google Play Services missing !",
                Toast.LENGTH_LONG
            ).show()
        }

    }


    override fun locationOn() {
        ownLocation?.beginUpdates()
        lati = ownLocation?.latitude
        longi = ownLocation?.longitude


        zoom = 15

        locationRequest = LocationRequest.create()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY


        try {
            if (mapFragment == null) {
                mapFragment = supportFragmentManager.findFragmentById(R.id.frgMap) as SupportMapFragment
                mapFragment!!.getMapAsync(this)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onPositionChanged() {
        if (ownLocation != null) {


//            Toast.makeText(
//                this,
//                ownLocation!!.getLongitude().toString() + "," +
//                        ownLocation!!.getLatitude().toString(),
//                Toast.LENGTH_SHORT
//            ).show()



        } else {
            Toast.makeText(this, "location is null", Toast.LENGTH_LONG).show()
        }
    }

    override fun locationCancelled() {
        MessageOwnLocation.GpsOnDialog(
            this,
            "GPS",
            "Please Enable GPS In Your Device"
        )
    }



}
