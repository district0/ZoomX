package com.zoomx.zoomx.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

import com.zoomx.zoomx.model.RequestEntity

/**
 * Created by Ahmed Fathallah on 12/6/2017.
 */

@Dao
interface RequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRequest(requestEntity: RequestEntity)

    @Query("SELECT * FROM requests ORDER BY startDate DESC")
    fun loadRequests(): LiveData<List<RequestEntity>>

    @Query("DELETE FROM requests")
    fun clearRequestsData(): Int

    @Query("SELECT * FROM requests WHERE id = :id")
    fun getRequestById(id: Int): LiveData<RequestEntity>

}
