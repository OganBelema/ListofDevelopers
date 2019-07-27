package com.example.ogan.listofdevelopersinlagosgithub.database.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ogan.listofdevelopersinlagosgithub.model.items.ApiResult;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface ResultDao {

    @Insert(onConflict = REPLACE)
    public void insertResult(ApiResult result);

    @Query("SELECT * FROM result")
    public Flowable<ApiResult> getResult();

}
