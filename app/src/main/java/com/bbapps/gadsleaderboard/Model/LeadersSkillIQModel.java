package com.bbapps.gadsleaderboard.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class LeadersSkillIQModel implements Parcelable {

    @SerializedName("name")
    public String name;

    @SerializedName("score")
    public Integer score;

    @SerializedName("country")
    public String country;

    @SerializedName("badgeUrl")
    public String badgeUrl;

    protected LeadersSkillIQModel(Parcel in) {
        name = in.readString();
        if (in.readByte() == 0) {
            score = null;
        } else {
            score = in.readInt();
        }
        country = in.readString();
        badgeUrl = in.readString();
    }

    public static final Creator<LeadersSkillIQModel> CREATOR = new Creator<LeadersSkillIQModel>() {
        @Override
        public LeadersSkillIQModel createFromParcel(Parcel in) {
            return new LeadersSkillIQModel(in);
        }

        @Override
        public LeadersSkillIQModel[] newArray(int size) {
            return new LeadersSkillIQModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        if (score == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(score);
        }
        parcel.writeString(country);
        parcel.writeString(badgeUrl);
    }
}
