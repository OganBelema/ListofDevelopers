package com.oganbelema.listofdevelopersinlagosgithub.database;


import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.oganbelema.listofdevelopersinlagosgithub.database.dao.ResultDao;
import com.oganbelema.listofdevelopersinlagosgithub.database.dao.UserDao;
import com.oganbelema.listofdevelopersinlagosgithub.database.typeconverter.ItemTypeConverter;
import com.oganbelema.listofdevelopersinlagosgithub.model.items.ApiResult;
import com.oganbelema.listofdevelopersinlagosgithub.model.users.UserApi;


@TypeConverters(ItemTypeConverter.class)
@Database(entities = {ApiResult.class, UserApi.class},
        version = 1, exportSchema = false)
public abstract class DeveloperDatabase extends RoomDatabase {

    public abstract ResultDao getResultDao();

    public abstract UserDao getUserDao();

}
