package com.azadi.locita.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.azadi.locita.R
import com.azadi.locita.base.BaseActivity
import com.azadi.locita.base.GlobalData
import com.azadi.locita.data.db.LocationModel
import com.azadi.locita.ui.about_me.AboutMeActivity
import com.azadi.locita.ui.post.PostActivity
import com.azadi.locita.utility.LocationUtility.OwnLocation
import com.azadi.locita.utility.LocationUtility.OwnLocationListener
import com.azadi.locita.utility.PermissionUtility
import com.azadi.locita.utility.Utility

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import java.util.ArrayList


class MainActivity : BaseActivity(), OwnLocationListener {

    var ownLocation: OwnLocation? = null
    var lati: Double? = null
    var longi: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        getPermissions()

        if (checkPermissions()){
            ownLocation = OwnLocation(this)
            ownLocation?.setListener(this)

            GlobalData.location = currentLocation
        }else{
            getPermissions()
        }

        fab.setOnClickListener { view ->
            var intent = Intent(this, PostActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onStart() {
        super.onStart()
        if (database != null) {
            var allList: List<LocationModel> = ArrayList()
            allList = database!!.locationDao().getAll()

            var adapter = LocationListAdapter(this)
            adapter.setLocationList(allList)
            recyclerlist.adapter = adapter
            recyclerlist.layoutManager = LinearLayoutManager(this)

        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_about -> {
                var intent = Intent(this, AboutMeActivity::class.java)
                startActivity(intent)
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun getPermissions() {
        val flag = PermissionUtility.CheckingPermissionIsEnabledOrNot(this)
        if (!flag) {
            PermissionUtility.RequestMultiplePermission(this)
        }
    }

    fun checkPermissions() : Boolean{
        return PermissionUtility.CheckingPermissionIsEnabledOrNot(this)
    }

    fun requestPermissions(){
        PermissionUtility.RequestMultiplePermission(this)
    }


    override fun locationOn() {
        if(ownLocation != null) {
            ownLocation?.beginUpdates()
            lati = ownLocation?.latitude
            longi = ownLocation?.longitude
            GlobalData.location = Utility.getLocation(lati!!, longi!!)
        }
        else if (currentLocation != null) {
            GlobalData.location = currentLocation
        }
    }

    override fun onPositionChanged() {
    }

    override fun locationCancelled() {

    }

}
