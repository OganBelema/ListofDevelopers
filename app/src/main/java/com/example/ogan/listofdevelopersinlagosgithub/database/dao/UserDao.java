package com.example.ogan.listofdevelopersinlagosgithub.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.ogan.listofdevelopersinlagosgithub.model.users.UserApi;

import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    public void insertUser(UserApi user);

    @Query("SELECT * FROM users WHERE login = :username")
    public Single<UserApi> getUser(String username);
}
