package com.example.ogan.listofdevelopersinlagosgithub.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ogan.listofdevelopersinlagosgithub.model.items.ApiResult;

import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface ResultDao {

    @Insert(onConflict = REPLACE)
    public void insertResult(ApiResult result);

    @Query("SELECT * FROM result")
    public Single<ApiResult> getResult();

}
