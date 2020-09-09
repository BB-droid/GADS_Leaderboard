package com.bbapps.gadsleaderboard;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.bbapps.gadsleaderboard.Model.LeadersModel;
import com.bbapps.gadsleaderboard.Model.LeadersSkillIQModel;
import com.bbapps.gadsleaderboard.ui.main.SectionsPagerAdapter;
import com.bbapps.gadsleaderboard.util.HoursAPI;
import com.bbapps.gadsleaderboard.util.HoursService;
import com.bbapps.gadsleaderboard.util.Values;
import com.google.android.material.tabs.TabLayout;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.concurrent.TimeoutException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ArrayList<LeadersModel> leadersModels;
    private ArrayList<LeadersSkillIQModel> skillIQ;
    private ViewPager viewPager;
    private ProgressBar refreshBar;
    private boolean skillSuccess = false;
    private boolean learnerSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.view_pager);
        refreshBar = findViewById(R.id.refreshBar);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        tabs.setTabMode(TabLayout.MODE_FIXED);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        Intent intent = getIntent();
        leadersModels = intent.getParcelableArrayListExtra(Values.LEADERS_HOURS);
        skillIQ = intent.getParcelableArrayListExtra(Values.LEADERS_SKILLIQ);

        if (leadersModels == null || skillIQ == null ){
            errorDialog();
        } else {
            setAdapters(leadersModels, skillIQ);
        }

    }

    private void setAdapters(ArrayList<LeadersModel> leadersModels,
                             ArrayList<LeadersSkillIQModel> skillIQ) {
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this,
                getSupportFragmentManager(), leadersModels, skillIQ);

        viewPager.setAdapter(sectionsPagerAdapter);
    }

    public void onSubmit(View view) {
        Intent submit = new Intent(this, SubmitActivity.class);
        startActivity(submit);
    }

    private void errorDialog() {
        Dialog dialog = new Dialog(this);
        dialog.setCancelable(false);

        dialog.setContentView(R.layout.dialog_view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        Button retryButton = dialog.findViewById(R.id.yesButton);
        ImageView cancelButton = dialog.findViewById(R.id.cancelBtn);
        ImageView errorIcon = dialog.findViewById(R.id.errorIcon);
        TextView text = dialog.findViewById(R.id.textView2);

        errorIcon.setVisibility(View.VISIBLE);
        cancelButton.setVisibility(View.GONE);
        retryButton.setText(R.string.retry);
        text.setText(R.string.network_error_message);

        retryButton.setOnClickListener(view -> {
            dialog.dismiss();
            refreshBar.setVisibility(View.VISIBLE);
            Refresh();
        });

        dialog.show();
    }

    private void Refresh() {
        if (Values.checkConnectivity(this)) {
            HoursAPI service = HoursService.getHoursInstance().create(HoursAPI.class);

            Call<ArrayList<LeadersModel>> call = service.getJSON();
            call.enqueue(new Callback<ArrayList<LeadersModel>>() {
                @Override
                public void onResponse(Call<ArrayList<LeadersModel>> call, Response<ArrayList<LeadersModel>> response) {

                    leadersModels = response.body();
                    learnerSuccess = true;

                }

                @Override
                public void onFailure(Call<ArrayList<LeadersModel>> call, Throwable t) {
                    if (t instanceof UnknownHostException || t instanceof TimeoutException) {
                        call.cancel();
                        learnerSuccess = false;
                    }
                }
            });

            Call<ArrayList<LeadersSkillIQModel>> call1 = service.getSkillIQ();
            call1.enqueue(new Callback<ArrayList<LeadersSkillIQModel>>() {
                @Override
                public void onResponse(Call<ArrayList<LeadersSkillIQModel>> call, Response<ArrayList<LeadersSkillIQModel>> response) {

                    skillIQ = response.body();
                    skillSuccess = true;

                    checkSuccess();
                }

                @Override
                public void onFailure(Call<ArrayList<LeadersSkillIQModel>> call, Throwable t) {
                    if (t instanceof UnknownHostException || t instanceof TimeoutException) {
                        call.cancel();
                        skillSuccess = false;
                        errorDialog();
                    }
                }

            });

        } else {
            errorDialog();
        }

    }

    private void checkSuccess() {
        if (skillSuccess || learnerSuccess) {
            refreshBar.setVisibility(View.GONE);
            setAdapters(leadersModels, skillIQ);
        } else {
            errorDialog();
        }

    }

}