package com.azadi.locita.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query


@Dao
interface LocationDao {

    @Query("Select * from tbl_location")
    fun getAll(): List<LocationModel>

    @Insert
    fun add(location: LocationModel)

    @Query("DELETE FROM tbl_location  where id LIKE  :id")
    fun delete(id : Int)

    @Delete
    fun remove(location: LocationModel)

//    @Query("SELECT * from tbl_location ORDER BY id ASC")
//    fun getAllSorted(): List<LocationModel>
//
//    @Query("SELECT * FROM tbl_location")
//    fun getAll(): List<LocationModel>
//
//    @Query("SELECT * FROM tbl_location where id LIKE  :id")
//    fun getLocationById(id : Int): LocationModel
//
//    @Query("SELECT COUNT(*) from tbl_location")
//    fun countLocations(): Int
//
//    @Insert
//    fun insert(location : LocationModel)
//



}