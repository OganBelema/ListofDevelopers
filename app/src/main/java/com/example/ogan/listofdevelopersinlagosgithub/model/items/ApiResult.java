
package com.example.ogan.listofdevelopersinlagosgithub.model.items;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "result")
public class ApiResult implements Parcelable {

    @PrimaryKey
    private int id = 1;

    @SerializedName("total_count")
    @Expose
    private Integer totalCount;
    @SerializedName("incomplete_results")
    @Expose
    private Boolean incompleteResults;
    @SerializedName("items")
    @Expose
    private ArrayList<Item> items = null;


    public ApiResult() {
    }

    protected ApiResult(Parcel in) {
        id = in.readInt();
        if (in.readByte() == 0) {
            totalCount = null;
        } else {
            totalCount = in.readInt();
        }
        byte tmpIncompleteResults = in.readByte();
        incompleteResults = tmpIncompleteResults == 0 ? null : tmpIncompleteResults == 1;
        items = in.createTypedArrayList(Item.CREATOR);
    }

    public static final Creator<ApiResult> CREATOR = new Creator<ApiResult>() {
        @Override
        public ApiResult createFromParcel(Parcel in) {
            return new ApiResult(in);
        }

        @Override
        public ApiResult[] newArray(int size) {
            return new ApiResult[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Boolean getIncompleteResults() {
        return incompleteResults;
    }

    public void setIncompleteResults(Boolean incompleteResults) {
        this.incompleteResults = incompleteResults;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        if (totalCount == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(totalCount);
        }
        parcel.writeByte((byte) (incompleteResults == null ? 0 : incompleteResults ? 1 : 2));
        parcel.writeTypedList(items);
    }
}
