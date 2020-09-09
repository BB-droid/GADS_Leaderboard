package com.bbapps.gadsleaderboard;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.bbapps.gadsleaderboard.util.HoursAPI;
import com.bbapps.gadsleaderboard.util.HoursService;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubmitActivity extends AppCompatActivity {


    private EditText fName;
    private EditText lName;
    private EditText eAddress;
    private EditText pLink;
    private Dialog dialog;
    private ProgressBar loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        fName = findViewById(R.id.firstName);
        lName = findViewById(R.id.lastName);
        eAddress = findViewById(R.id.emailAddress);
        pLink = findViewById(R.id.projectLink);
        loading = findViewById(R.id.progressBar);

        dialog = new Dialog(this);

        Button submitButton = findViewById(R.id.button);
        submitButton.setOnClickListener(view -> {
            if (!getTexts()) {
                MyDialog();
            } else {
                Snackbar.make(view, "All fields are required", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });
//        submitButton.setOnClickListener(view -> MyDialog());
    }

    private void MyDialog() {
        Button yesButton;
        ImageView cancelButton;
        dialog.setContentView(R.layout.dialog_view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        yesButton = dialog.findViewById(R.id.yesButton);
        cancelButton = dialog.findViewById(R.id.cancelBtn);

        yesButton.setOnClickListener(view -> {
            dialog.dismiss();
            loading.setVisibility(View.VISIBLE);
            submitWork();
        });
        cancelButton.setOnClickListener(view -> dialog.dismiss());

        dialog.show();

    }

    private void Report( boolean b) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View view = getLayoutInflater().inflate(R.layout.sub_report, null);
        builder.setView(view);
        ImageView cancel = view.findViewById(R.id.subImage);
        TextView text = view.findViewById(R.id.subText);

        if (b) {
            cancel.setImageResource(R.drawable.ic_success_24);
            text.setText(R.string.submission_successful);
        } else {
            cancel.setImageResource(R.drawable.ic_error_24);
            text.setText(R.string.submission_error);
        }

        AlertDialog dialog = builder.create();
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
    }

    private boolean getTexts() {
        return fName.getText().toString().isEmpty() ||
                lName.getText().toString().isEmpty() ||
                eAddress.getText().toString().isEmpty() ||
                pLink.getText().toString().isEmpty() ;
    }

    private void submitWork() {

        HoursAPI service = HoursService.getFormUrl().create(HoursAPI.class);
        Call<Object> call = service.items(fName.getText().toString(),
                lName.getText().toString(), eAddress.getText().toString(), pLink.getText().toString());
        call.enqueue(new Callback<Object>() {
                @Override
                public void onResponse(Call<Object> call, Response<Object> response) {
                    loading.setVisibility(View.GONE);
                    Report(true);
                }

                @Override
                public void onFailure(Call<Object> call, Throwable t) {
                    call.cancel();
                    loading.setVisibility(View.GONE);
                    Report(false);
                }
            });

    }

}