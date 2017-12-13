package com.zoomx.zoomx.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.zoomx.zoomx.model.RequestEntity;

import java.util.List;

/**
 * Created by Ahmed Fathallah on 12/6/2017.
 */

@Dao
public interface RequestDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertRequest(RequestEntity requestEntity);

    @Query("SELECT * FROM requests ORDER BY startDate DESC")
    LiveData<List<RequestEntity>> loadRequests();

    @Query("DELETE FROM requests")
    int clearRequestsData();
}
