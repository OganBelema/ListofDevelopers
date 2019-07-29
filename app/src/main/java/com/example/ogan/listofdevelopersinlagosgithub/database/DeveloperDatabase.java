package com.example.ogan.listofdevelopersinlagosgithub.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.ogan.listofdevelopersinlagosgithub.database.dao.ResultDao;
import com.example.ogan.listofdevelopersinlagosgithub.database.dao.UserDao;
import com.example.ogan.listofdevelopersinlagosgithub.database.typeconverter.ItemTypeConverter;
import com.example.ogan.listofdevelopersinlagosgithub.model.items.ApiResult;
import com.example.ogan.listofdevelopersinlagosgithub.model.users.UserApi;

@TypeConverters(ItemTypeConverter.class)
@Database(entities = {ApiResult.class, UserApi.class},
        version = 1, exportSchema = false)
public abstract class DeveloperDatabase extends RoomDatabase {

    public abstract ResultDao getResultDao();

    public abstract UserDao getUserDao();

}
