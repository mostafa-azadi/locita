package com.azadi.locita.base.Location;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderApi;
import com.google.android.gms.location.LocationServices;

/**
 * Created by PC on 06/14/18.
 */

public class BaseLocationActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    public static Location currentLocation;

    public static Location getCurrentLocation() {
        if (currentLocation == null) {
            currentLocation = new Location("");
        }
        return currentLocation;
    }

    GoogleApiClient googleApiClient;
    Location location;
    static final int REQUEST_CODE = 1000;
    public static final String TAG = "TAG_BASE_LOCATION";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        GpsUtility gpsUtility = new GpsUtility(this);
        if (!gpsUtility.isGpsStatsus()) {

            MessageLocationUtility.GpsOnDialog(this,
                    "سیستم موقعیت یاب",
                    "لطفا موقعیت مکانی خود را فعال کنید .");

        } else {
            gpsUtility.isBugGPS();
            gpsUtility.gpsON();
        }

        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        if (googleApiClient != null) {
            googleApiClient.connect();
        }
    }

    //------------------------------------------------User Location Methods----------------------
    //------Google Api Client  Impelements Methods
    @Override
    public void onConnected(Bundle bundle) {
        Log.d(TAG, "We Are Connected");
        currentLocation = getUserCurrentLocation();
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.d(TAG, "The Connection Is Suspanded");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        Log.d(TAG, "The Connection Failed");
        int result = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this, REQUEST_CODE);
            } catch (Exception e) {
                Log.d(TAG, e.getStackTrace().toString());
            }
        } else {

            switch (result) {
                case ConnectionResult.SERVICE_VERSION_UPDATE_REQUIRED:
                    Log.d(TAG, "SERVICE_VERSION_UPDATE_REQUIRED");
                    Toast.makeText(this, "GooglePlayService Is UPDATE REQUIRED!", Toast.LENGTH_LONG).show();
                    MessageLocationUtility.GooglePlayUpdateDialog(this,
                            "Update Google Play Services",
                            "This app won't run unless you ic_update Google Play Services");
                    break;
                case ConnectionResult.SUCCESS:
                    Log.d(TAG, "Play service available success but some other thing is wrong");
                    //mostafa.azadi:  Play service is available but some other thing is wrong
                    Toast.makeText(this, "GooglePlayService Is Not Working!", Toast.LENGTH_LONG).show();
                    //this.finish();
                    break;
                default:
                    Log.d(TAG, "unknown services result: " + connectionResult);
                    MessageLocationUtility.GooglePlayUpdateDialog(this,
                            "Update Google Play Services",
                            "This app won't run unless you ic_update Google Play Services");
                    //Toast.makeText(this,"GooglePlayService Is Not Working!", Toast.LENGTH_LONG).show();
                    //this.finish();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
            googleApiClient.connect();
        }

    }


    //------Custom Method for User Location
    public Location getUserCurrentLocation() {

        int permissionCheckLocation = ContextCompat.checkSelfPermission(
                this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (permissionCheckLocation != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(
                    (Activity) this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, 133);
        } else {
            FusedLocationProviderApi fusedLocationProviderApi = LocationServices.FusedLocationApi;
            location = fusedLocationProviderApi.getLastLocation(googleApiClient);
            if (location != null) {
                return location;
            } else {
                LocationTracker mLocationTracker = new LocationTracker(this);

                if (mLocationTracker.canGetLocation()) {
                    location = mLocationTracker.getLocation();
                    if (location == null) {
                        location = new Location("s");
                    }
                    return location;
                } else {
                    MessageLocationUtility.GpsOnDialog(this,
                            "سیستم موقعیت یاب",
                            "لطفا موقعیت مکانی خود را فعال کنید .");
                }
            }
        }
        return null;
    }

//
//    public List<AddressModel> getAddress(Location location){
//
//        List<AddressModel> addressList = new ArrayList<>();
//
//        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
//        List<Address> addresses = null;
//        try {
//            addresses = geocoder.getFromLocation(
//                    location.getLatitude(),
//                    location.getLatitude(),
//                    1);
//
//
//        String cityName = addresses.get(0).getAddressLine(0);
//        String stateName = addresses.get(0).getAddressLine(1);
//        String countryName = addresses.get(0).getAddressLine(2);
//
//        addressList.add(new AddressModel(countryName,stateName,cityName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        return addressList;
//    }

}
