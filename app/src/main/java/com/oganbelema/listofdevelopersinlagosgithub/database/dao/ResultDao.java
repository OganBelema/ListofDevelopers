package com.oganbelema.listofdevelopersinlagosgithub.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.oganbelema.listofdevelopersinlagosgithub.model.items.ApiResult;

import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;


@Dao
public interface ResultDao {

    @Insert(onConflict = REPLACE)
    void insertResult(ApiResult result);

    @Query("SELECT * FROM result")
    Single<ApiResult> getResult();

}
