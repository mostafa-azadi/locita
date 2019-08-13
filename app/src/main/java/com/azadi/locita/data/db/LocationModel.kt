package com.azadi.locita.data.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "tbl_location")
class LocationModel(
    @field:ColumnInfo(name = "title") var title: String,
    @field:ColumnInfo(name = "lat") var lat: String,
    @field:ColumnInfo(name = "lng") var lng: String
) {


    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}