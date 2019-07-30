package com.oganbelema.listofdevelopersinlagosgithub.database.dao;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;


import com.oganbelema.listofdevelopersinlagosgithub.model.users.UserApi;

import io.reactivex.Single;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface UserDao {

    @Insert(onConflict = REPLACE)
    void insertUser(UserApi user);

    @Query("SELECT * FROM users WHERE login = :username")
    Single<UserApi> getUser(String username);
}
