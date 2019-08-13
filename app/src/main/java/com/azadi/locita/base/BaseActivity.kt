package com.azadi.locita.base

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.room.Room
import com.azadi.locita.base.Location.BaseLocationActivity
import com.azadi.locita.data.db.DataBaseBuilder

abstract class BaseActivity : BaseLocationActivity() {

    companion object{
        var database: DataBaseBuilder? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        database = Room.databaseBuilder(this, DataBaseBuilder::class.java, "locita_database")
            .allowMainThreadQueries().build()

    }
}