package com.bbapps.gadsleaderboard;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.bbapps.gadsleaderboard.Model.LeadersModel;
import com.bbapps.gadsleaderboard.Model.LeadersSkillIQModel;
import com.bbapps.gadsleaderboard.util.HoursAPI;
import com.bbapps.gadsleaderboard.util.HoursService;
import com.bbapps.gadsleaderboard.util.Values;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity {

    private ArrayList<LeadersModel> lm;
    private ArrayList<LeadersSkillIQModel> sm;
    private boolean skillSuccess = false;
    private boolean learnerSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        lm = new ArrayList<>();
        sm = new ArrayList<>();

        Handler hand = new Handler();
        hand.postDelayed(this::loadLeaders, 1000);

    }

    private void loadLeaders() {
        if (Values.checkConnectivity(this)) {
            HoursAPI service = HoursService.getHoursInstance().create(HoursAPI.class);

            Call<ArrayList<LeadersModel>> call = service.getJSON();
            Call<ArrayList<LeadersSkillIQModel>> call1 = service.getSkillIQ();

            call.enqueue(new Callback<ArrayList<LeadersModel>>() {
                @Override
                public void onResponse(Call<ArrayList<LeadersModel>> call, Response<ArrayList<LeadersModel>> response) {

                    lm = response.body();
                    learnerSuccess = true;

                }

                @Override
                public void onFailure(Call<ArrayList<LeadersModel>> call, Throwable t) {
                    if (t instanceof UnknownHostException || t instanceof TimeoutException) {
                        learnerSuccess = false;
                        call.cancel();
                    }
                }
            });


            call1.enqueue(new Callback<ArrayList<LeadersSkillIQModel>>() {
                @Override
                public void onResponse(Call<ArrayList<LeadersSkillIQModel>> call, Response<ArrayList<LeadersSkillIQModel>> response) {

                    sm = response.body();
                    skillSuccess = true;
                    checkSuccess();

                }

                @Override
                public void onFailure(Call<ArrayList<LeadersSkillIQModel>> call, Throwable t) {
                    if (t instanceof UnknownHostException || t instanceof TimeoutException) {
                        skillSuccess = false;
                        call.cancel();
                        openActivity();
                    }
                }
            });

        } else {
            openActivity();
        }

    }

    private void checkSuccess() {
        if (learnerSuccess || skillSuccess) {
            openActivity(lm, sm);
        } else {
            openActivity();
        }
    }

    private void openActivity(ArrayList<LeadersModel> body, ArrayList<LeadersSkillIQModel> sq) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putParcelableArrayListExtra(Values.LEADERS_HOURS, body)
                .putParcelableArrayListExtra(Values.LEADERS_SKILLIQ, sq);
        startActivity(intent);
        finish();
    }

    private void openActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}