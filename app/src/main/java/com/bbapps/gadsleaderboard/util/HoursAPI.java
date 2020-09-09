package com.bbapps.gadsleaderboard.util;

import com.bbapps.gadsleaderboard.Model.LeadersModel;
import com.bbapps.gadsleaderboard.Model.LeadersSkillIQModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface HoursAPI {

    @GET("/api/hours/")
    Call<ArrayList<LeadersModel>> getJSON();

    @GET("/api/skilliq")
    Call<ArrayList<LeadersSkillIQModel>> getSkillIQ();

    @POST("1FAIpQLSf9d1TcNU6zc6KR8bSEM41Z1g1zl35cwZr2xyjIhaMAz8WChQ/formResponse")
    @FormUrlEncoded
    Call<Object> items(@Field("entry.1877115667") String fName,
                     @Field("entry.2006916086") String lName,
                     @Field("entry.1824927963") String email,
                     @Field("entry.284483984")  String link);

}
