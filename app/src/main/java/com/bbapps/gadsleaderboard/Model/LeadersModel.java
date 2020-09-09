package com.bbapps.gadsleaderboard.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LeadersModel implements Parcelable {

    @SerializedName("name")
    public String name;

    @SerializedName("hours")
    public Integer hours;

    @SerializedName("country")
    public String country;

    @SerializedName("badgeUrl")
    public String badgeUrl;


    protected LeadersModel(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0) {
            hours = null;
        } else {
            hours = in.readInt();
        }
        country = in.readString();
        badgeUrl = in.readString();
    }

    public static final Creator<LeadersModel> CREATOR = new Creator<LeadersModel>() {
        @Override
        public LeadersModel createFromParcel(Parcel in) {
            return new LeadersModel(in);
        }

        @Override
        public LeadersModel[] newArray(int size) {
            return new LeadersModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        if (hours == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(hours);
        }
        parcel.writeString(country);
        parcel.writeString(badgeUrl);
    }
}
