package com.example.jodoh.pages;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.jodoh.R;
import com.example.jodoh.entity.UserEntity;
import com.example.jodoh.service.ApiClient;
import com.example.jodoh.service.UserInterface;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    EditText editUsername, editNama, editNomorHP, editUmur, editPassword;
    Spinner spnJK;
    ImageView imageView;
    Button btnSimpan;
    String mediaPath;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        editNama = findViewById(R.id.editNama);
        editNomorHP = findViewById(R.id.editNoHP);
        editUmur = findViewById(R.id.editUmur);
        editPassword = findViewById(R.id.editPassword);
        spnJK = findViewById(R.id.spn_jk);
        imageView = findViewById(R.id.imageView);
        editUsername = findViewById(R.id.editUsername);
        btnSimpan = findViewById(R.id.btnSimpan);

        UserInterface userInterface = ApiClient.getRetrofit().create(UserInterface.class);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent library = new Intent(getApplicationContext(), ImageSelectActivity.class);
                library.putExtra(ImageSelectActivity.FLAG_COMPRESS, true);
                library.putExtra(ImageSelectActivity.FLAG_CAMERA, true);
                library.putExtra(ImageSelectActivity.FLAG_GALLERY, true);
                startActivityForResult(library, 1);
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FusedLocationProviderClient mFusedLocationProviderClient = LocationServices
                        .getFusedLocationProviderClient(RegisterActivity.this);

                mFusedLocationProviderClient.getLastLocation().addOnSuccessListener(RegisterActivity.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        UserEntity user = new UserEntity();
                        user.setUsername(editUsername.getText().toString());
                        user.setUmur(editUmur.getText().toString());
                        user.setName(editNama.getText().toString());
                        user.setPassword(editPassword.getText().toString());
                        user.setNohp(editNomorHP.getText().toString());
                        user.setLatitude(location.getLatitude());
                        user.setLongitude(location.getLongitude());
                        user.setJeniskelamin(spnJK.getSelectedItem().toString());

                        Gson gson = new Gson();
                        String json = gson.toJson(user);
                        File file = new File(mediaPath);

                        RequestBody requestBody = RequestBody.create(MediaType.parse("*/*"), file);
                        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
                        RequestBody data = RequestBody.create(MediaType.parse("text/plain"), json);

                        Call<String> call = userInterface.daftarUser(fileToUpload, data);
                        call.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(RegisterActivity.this, response.body(), Toast.LENGTH_SHORT).show();
                                finish();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                System.out.println(t);
                                Toast.makeText(RegisterActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(1 == requestCode && resultCode == RESULT_OK) {
            mediaPath = data.getStringExtra(ImageSelectActivity.RESULT_FILE_PATH);
            imageView.setImageBitmap(BitmapFactory.decodeFile(mediaPath));
        }
    }
}
