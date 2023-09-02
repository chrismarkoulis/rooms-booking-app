package com.example.rentaroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rentaroom.api.Api;
import com.example.rentaroom.api.RetrofitClient;
import com.example.rentaroom.models.User;
import com.example.rentaroom.store.StoreManager;
import com.example.rentaroom.utils.Utils;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextPassword;

    Api api = RetrofitClient.makeRequest(null).create(Api.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);

        findViewById(R.id.buttonLogin).setOnClickListener(this);
        findViewById(R.id.textViewRegister).setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (StoreManager.getInstance(this).isLoggedIn()) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }
    }

    private void userLogin() {

        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password should be atleast 6 character long");
            editTextPassword.requestFocus();
            return;
        }

        Call<User> call = api.userLogin(email, password);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                try {
                    if (response.code() == 200) {
                        if (response.body() != null) {
                            User userRes = response.body();
                            String id = userRes.getId();
                            String name = userRes.getName();
                            String email = userRes.getEmail();
                            boolean isAdmin = userRes.isAdmin();
                            String token = userRes.getToken();
                            User user = new User(id, name, email, isAdmin, token);
                            Log.d("~~ USER ~~", user.toString());
                            StoreManager.getInstance(LoginActivity.this)
                                    .saveUser(user);
                            Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                        } else {
                            Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        String s = response.errorBody().string();
                        Toast.makeText(LoginActivity.this, Utils.s_json(s, "message"), Toast.LENGTH_LONG).show();
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.buttonLogin) {
            userLogin();
        } else if (id == R.id.textViewRegister) {
            startActivity(new Intent(this, MainActivity.class));
        }
    }
}