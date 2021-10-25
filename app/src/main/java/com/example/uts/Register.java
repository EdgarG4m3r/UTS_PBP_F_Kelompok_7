package com.example.uts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uts.database.DatabaseClient;
import com.example.uts.model.User;
import com.example.uts.preferences.UserPreferences;
import com.example.uts.dao.UserDao;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class Register extends AppCompatActivity
{
    private TextInputEditText etName, etUsername, etPassword,etTelp;
    private MaterialButton btnSignup;
    private UserPreferences userPreferences;
    private TextView tv_Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        userPreferences = new UserPreferences(Register.this);

        etName = findViewById(R.id.etName);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        tv_Login = findViewById(R.id.tv_login);
        btnSignup = findViewById(R.id.btnSignup);
        etTelp = findViewById(R.id.etTelp);


        tv_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validateForm()){
                    registerUser(etName.getText().toString(), etUsername.getText().toString(), etPassword.getText().toString(), etTelp.getText().toString());
                }
            }
        });
    }

    private void registerUser(String name, String username, String password, String noTelp){

        class RegisterUser extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                User user = new User();
                user.setName(name);
                user.setUsername(username);
                user.setPassword(password);
                user.setNoTelp(noTelp);

                DatabaseClient.getInstance(Register.this)
                        .getDatabase()
                        .userDao()
                        .registerUser(user);

                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
                Toast.makeText(Register.this, "Berhasil mendaftar", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Register.this, Login.class));
                finish();
            }

        }

        RegisterUser registerUser = new RegisterUser();
        registerUser.execute();
    }

    private boolean validateForm(){
        /* Check username & password is empty or not */
        if(etUsername.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty() || etName.getText().toString().isEmpty() || etTelp.getText().toString().isEmpty()){
            Toast.makeText(Register.this,"Field tidak boleh Kosong",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
}