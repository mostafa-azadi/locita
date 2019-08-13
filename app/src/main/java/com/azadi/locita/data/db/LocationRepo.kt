package com.azadi.locita.data.db

import androidx.annotation.WorkerThread

class LocationRepo(val locationDao: LocationDao){

    @WorkerThread
    suspend fun insert(location: LocationModel) {
        //locationDao.insert(LocationModel)
    }
}