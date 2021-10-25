package com.example.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uts.database.DatabaseClient;
import com.example.uts.model.User;
import com.example.uts.preferences.UserPreferences;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class Login extends AppCompatActivity {
    private EditText etUserName, etPass;
    private MaterialButton btnLogin;
    private TextView tvSignup;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        userPreferences = new UserPreferences(Login.this);

        etUserName = findViewById(R.id.etUserName);
        etPass = findViewById(R.id.etPass);

        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignup);

        /* Apps will check the login first from shared preferences */
        checkLogin();

        tvSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this, Register.class));
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    attemptLogin(etUserName.getText().toString().trim(), etPass.getText().toString().trim());
                }
            }
        });

    }


    private void attemptLogin(String username, String password){

        class AttemptLogin extends AsyncTask<Void, Void, User> {
            @Override
            protected User doInBackground(Void... voids) {

                User user = DatabaseClient.getInstance(Login.this)
                        .getDatabase()
                        .userDao()
                        .attemptLogin(username,password);

                return user;
            }

            @Override
            protected void onPostExecute(User user) {
                super.onPostExecute(user);
                if(user == null){
                    Toast.makeText(Login.this, "Username atau password salah", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Login.this, "Berhasil login", Toast.LENGTH_SHORT).show();
                    userPreferences.setUser(user.getId(),user.getName(), user.getUsername(),user.getPassword(),user.getNoTelp());
                }
                checkLogin();

            }

        }

        AttemptLogin attemptLogin = new AttemptLogin();
        attemptLogin.execute();
    }

    private boolean validateForm(){
        /* Check username & password is empty or not */
        if(etUserName.getText().toString().trim().isEmpty() || etPass.getText().toString().trim().isEmpty()){
            Toast.makeText(Login.this,"Username Atau Password Kosong",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void checkLogin(){
        if(userPreferences.checkLogin()){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
    }

}
