package com.example.uts;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.uts.preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;

public class Profile extends AppCompatActivity
{
    private TextView tv_userName,tv_nama, tv_noTelp;
    UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        userPreferences = new UserPreferences(Profile.this);

        tv_userName = findViewById(R.id.tv_userName);
        tv_nama = findViewById(R.id.tv_nama);
        tv_noTelp = findViewById(R.id.tv_noTelp);

        tv_userName.setText(userPreferences.getUserLogin().getUsername());
        tv_nama.setText(userPreferences.getUserLogin().getName());
        tv_noTelp.setText(userPreferences.getUserLogin().getNoTelp());

    }

    public void btn_Back (View view) {
        finish();
    };
}

