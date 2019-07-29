package com.example.ogan.listofdevelopersinlagosgithub.database.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.ogan.listofdevelopersinlagosgithub.model.users.UserApi;

import io.reactivex.Single;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    public void insertUser(UserApi user);

    @Query("SELECT * FROM users WHERE login = :username")
    public Single<UserApi> getUser(String username);
}
