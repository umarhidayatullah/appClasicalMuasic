package com.example.jodoh.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jodoh.MainActivity;
import com.example.jodoh.R;
import com.example.jodoh.entity.UserEntity;
import com.example.jodoh.service.ApiClient;
import com.example.jodoh.service.UserInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText editUsername, editPassword;
    Button btnMasuk;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        editPassword = findViewById(R.id.editPassword2);
        editUsername = findViewById(R.id.editUsername2);
        btnMasuk = findViewById(R.id.btnMasuk);

        UserInterface userInterface = ApiClient.getRetrofit().create(UserInterface.class);

        btnMasuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserEntity user = new UserEntity();
                user.setUsername(editUsername.getText().toString());
                user.setPassword(editPassword.getText().toString());
                Call<String> call = userInterface.loginUser(user);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(LoginActivity.this, "Login Success", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(LoginActivity.this, MenuActivity.class);
                            i.putExtra("jwttoken", response.body());
                            startActivity(i);
                        } else {
                            Toast.makeText(LoginActivity.this, "Login Failed, Message", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        System.out.println(t);
                        Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
