package com.azadi.locita.utility

import android.Manifest
import android.Manifest.permission.*
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Created By Mostafa Azadi
 */

class PermissionUtility(_context: Context) : AppCompatActivity() {


    internal var context: Context
    val RequestPermissionCode = 120


    init {
        context = _context
    }

    // Calling override method.
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray)
    {
        when (requestCode) {

            RequestPermissionCode ->

                if (grantResults.size > 0) {
                    val InternetPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
                    val CoarseLocationPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED
                    val FineLocationPermission = grantResults[2] == PackageManager.PERMISSION_GRANTED
                    if (InternetPermission && CoarseLocationPermission && FineLocationPermission
                    ) {
                        Toast.makeText(context, "Permission Granted", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Permission Denied", Toast.LENGTH_LONG).show()

                    }
                }
        }
    }

    companion object {

        val RequestPermissionCode = 120

        fun permissionChecker(context: Context, permissionID: String): Boolean {

            var flag = false
            var GeneralPermission: String? = null
            val CoarseLocation = Manifest.permission.ACCESS_COARSE_LOCATION
            val Internet = Manifest.permission.INTERNET
            val FineLocation = Manifest.permission.ACCESS_FINE_LOCATION


            when (permissionID) {
                "INTERNET" -> GeneralPermission = Internet
                "ACCESS_COARSE_LOCATION" -> GeneralPermission = CoarseLocation
                "ACCESS_FINE_LOCATION" -> GeneralPermission = FineLocation
            }

            flag = ContextCompat.checkSelfPermission(context, GeneralPermission!!) == PackageManager.PERMISSION_GRANTED

            return flag
        }


        fun RequestMultiplePermission(context: Context) {
            // Creating String Array with Permissions.
            ActivityCompat.requestPermissions(
                context as Activity,
                arrayOf(
                    INTERNET,
                    ACCESS_COARSE_LOCATION,
                    ACCESS_FINE_LOCATION
                ),
                RequestPermissionCode
            )

        }

        fun CheckingPermissionIsEnabledOrNot(context: Context): Boolean {

            var result = false

            val FirstPermissionResult = ContextCompat.checkSelfPermission(context, Manifest.permission.INTERNET)

            val SecondPermissionResult = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
            val ThirdPermissionResult = ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            )


            if (FirstPermissionResult == PackageManager.PERMISSION_GRANTED &&
                SecondPermissionResult == PackageManager.PERMISSION_GRANTED &&
                ThirdPermissionResult == PackageManager.PERMISSION_GRANTED
            ) {
                result = true
            }
            return result
        }


        //----------------------------Single permissions

        fun permissionCoarseLocation(context: Context) {
            val permissionCheckReadStorage = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_COARSE_LOCATION
            )
            if (permissionCheckReadStorage != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    context as Activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
                    RequestPermissionCode
                )
            }
        }

        fun permissionFineLocation(context: Context) {
            val permissionCheckReadStorage = ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            )
            if (permissionCheckReadStorage != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    RequestPermissionCode
                )
            }
        }

        fun permissionInternet(context: Context) {
            val permissionCheckReadStorage = ContextCompat.checkSelfPermission(
                context, Manifest.permission.INTERNET
            )
            if (permissionCheckReadStorage != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    context as Activity, arrayOf(Manifest.permission.INTERNET),
                    RequestPermissionCode
                )
            }
        }


    }




}