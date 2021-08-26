package com.example.jodoh.pages;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jodoh.R;
import com.example.jodoh.entity.UserEntity;
import com.example.jodoh.utility.JWTUtil;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

public class MenuActivity extends AppCompatActivity {
    Button btnDataCalon, btnPilihCalon, btnKeluar;
    TextView txtHello;
    UserEntity userTemp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        btnDataCalon = findViewById(R.id.btnDataCalon);
        btnPilihCalon = findViewById(R.id.btnPilihCalon);
        btnKeluar = findViewById(R.id.btnKeluar);
        txtHello = findViewById(R.id.txtHello);
        String token = getIntent().getStringExtra("jwttoken");

        Gson gson = new Gson();
        try {
            userTemp = gson.fromJson(JWTUtil.getBodyDecode(token), UserEntity.class);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        txtHello.setText("Hello "+userTemp.getName());

        btnPilihCalon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, TinderActivity.class);
                i.putExtra("jwttoken", token);
                startActivity(i);
            }
        });

        btnKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
