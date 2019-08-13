package com.azadi.locita.base.Location;

/**
 * Created by PC on 10/10/18.
 */

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.view.ContextThemeWrapper;
import androidx.core.app.ActivityCompat;
import com.azadi.locita.R;

/**
 *         Gps ic_location tracker class
 *         to get users ic_location and other information related to ic_location
 */
public class LocationTracker extends Service implements LocationListener {

    /**
     * context of calling class
     */
    private Context mContext;

    /**
     * flag for gps status
     */
    private boolean isGpsEnabled = false;

    /**
     * flag for network status
     */
    private boolean isNetworkEnabled = false;

    /**
     * flag for gps
     */
    private boolean canGetLocation = false;

    /**
     * ic_location
     */
    private Location mLocation;

    /**
     * latitude
     */
    private double mLatitude;

    /**
     * longitude
     */
    private double mLongitude;

    /**
     * min distance change to get ic_location ic_update
     */
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATE = 10;

    /**
     * min time for ic_location ic_update
     * 60000 = 1min
     */
    private static final long MIN_TIME_FOR_UPDATE = 60000;

    /**
     * ic_location manager
     */
    private LocationManager mLocationManager;


    /**
     * @param mContext constructor of the class
     */
    public LocationTracker(Context mContext) {

        this.mContext = mContext;
        getLocation();
    }


    /**
     * @return ic_location
     */
    public Location getLocation() {

        try {

            mLocationManager = (LocationManager) mContext.getSystemService(LOCATION_SERVICE);

            /*getting status of the gps*/
            isGpsEnabled = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

            /*getting status of network provider*/
            isNetworkEnabled = mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGpsEnabled && !isNetworkEnabled) {
                MessageLocationUtility.GpsOnDialog(this,
                        "سیستم موقعیت یاب",
                        "لطفا موقعیت مکانی خود را فعال کنید .");
            } else {

                this.canGetLocation = true;

                /*getting ic_location from network provider*/
                if (isNetworkEnabled) {

                    if (ActivityCompat.checkSelfPermission(mContext,
                            Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                            && ActivityCompat.checkSelfPermission(mContext, Manifest.permission
                            .ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions((Activity) mContext,
                                new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                                        Manifest.permission.ACCESS_COARSE_LOCATION}, 123);
                        return mLocation;
                    }
                    mLocationManager.requestLocationUpdates
                            (LocationManager.NETWORK_PROVIDER, MIN_TIME_FOR_UPDATE,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATE, this);

                    if (mLocationManager != null) {

                        mLocation = mLocationManager.getLastKnownLocation(LocationManager
                                .NETWORK_PROVIDER);

                        if (mLocation != null) {

                            mLatitude = mLocation.getLatitude();

                            mLongitude = mLocation.getLongitude();
                        }
                    }
                    /*if gps is enabled then get ic_location using gps*/
                    if (isGpsEnabled) {

                        if (mLocation == null) {

                            mLocationManager.requestLocationUpdates(LocationManager.
                                    GPS_PROVIDER, MIN_TIME_FOR_UPDATE, MIN_DISTANCE_CHANGE_FOR_UPDATE, this);

                            if (mLocationManager != null) {

                                mLocation = mLocationManager.
                                        getLastKnownLocation(LocationManager.GPS_PROVIDER);

                                if (mLocation != null) {

                                    mLatitude = mLocation.getLatitude();

                                    mLongitude = mLocation.getLongitude();
                                }

                            }
                        }

                    }
                }
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return mLocation;
    }

    /**
     * call this function to stop using gps in your application
     */
    public void stopUsingGps() {

        if (mLocationManager != null) {

            mLocationManager.removeUpdates(LocationTracker.this);

        }
    }

    /**
     * @return latitude
     *         <p/>
     *         function to get latitude
     */
    public double getLatitude() {

        if (mLocation != null) {

            mLatitude = mLocation.getLatitude();
        }
        return mLatitude;
    }

    /**
     * @return longitude
     *         function to get longitude
     */
    public double getLongitude() {

        if (mLocation != null) {

            mLongitude = mLocation.getLongitude();

        }

        return mLongitude;
    }

    /**
     * @return to check gps or wifi is enabled or not
     */
    public boolean canGetLocation() {

        return this.canGetLocation;
    }

    /**
     * function to prompt user to open
     * settings to enable gps
     */
    public void showSettingsAlert1() {

        AlertDialog.Builder mAlertDialog = new AlertDialog.Builder(
                new ContextThemeWrapper(mContext, R.style.AppTheme));

        mAlertDialog.setTitle("سیستم موقعیت مکانی");

        mAlertDialog.setMessage("لطفا موقعیت مکانی خود را فعال کنید");

        mAlertDialog.setPositiveButton("فعالسازی", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                Intent mIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                mContext.startActivity(mIntent);
            }
        });

        mAlertDialog.setNegativeButton("بستن", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                dialog.cancel();

            }
        });

        final AlertDialog mcreateDialog = mAlertDialog.create();
        mcreateDialog.show();
    }

    @Override
    public IBinder onBind(Intent arg0) {

        return null;
    }

    public void onLocationChanged(Location location) {

    }

    public void onProviderDisabled(String provider) {
    }

    public void onProviderEnabled(String provider) {
    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}
