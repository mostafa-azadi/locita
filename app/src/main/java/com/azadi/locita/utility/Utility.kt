package com.azadi.locita.utility

import android.location.Location

class Utility {

    companion object{
        fun isEmpty(s: String?): Boolean {
            return s == null || s.trim { it <= ' ' }.isEmpty() || s === "" ||
                    s == "" || s == null || s == "null" || s === "null" ||
                    s == "NULL" || s === "NULL"
        }

        fun getLocation(lat:Double , lng:Double) : Location{
            val targetLocation = Location("")//provider name is unnecessary
            targetLocation.latitude = lat
            targetLocation.longitude = lng
            return targetLocation
        }


    }
}