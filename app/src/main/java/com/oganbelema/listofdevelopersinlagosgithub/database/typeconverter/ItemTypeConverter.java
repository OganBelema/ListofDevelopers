package com.oganbelema.listofdevelopersinlagosgithub.database.typeconverter;


import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.oganbelema.listofdevelopersinlagosgithub.model.items.Item;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class ItemTypeConverter {

    private Gson gson = new Gson();

    @TypeConverter
    public ArrayList<Item> fromString(String value) {
        Type listType = new TypeToken<ArrayList<Item>>(){}.getType();
        return gson.fromJson(value, listType);
    }

    @TypeConverter
    public String fromArrayList(ArrayList<Item> list) {
        return gson.toJson(list);
    }
}
