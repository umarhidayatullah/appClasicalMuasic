package com.example.jodoh.pages;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.daprlabs.cardstack.SwipeDeck;
import com.example.jodoh.R;
import com.example.jodoh.adapter.DeckAdapter;
import com.example.jodoh.entity.JodohEntity;
import com.example.jodoh.entity.UserEntity;
import com.example.jodoh.service.ApiClient;
import com.example.jodoh.service.UserInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TinderActivity extends AppCompatActivity {
    private SwipeDeck cardStack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tinder);
        String token = "Bearer "+getIntent().getStringExtra("jwttoken");
        cardStack = findViewById(R.id.swipe_deck);

        UserInterface userInterface = ApiClient.getRetrofit().create(UserInterface.class);
        Call<List<UserEntity>> call = userInterface.searchCalonGender(token);
        call.enqueue(new Callback<List<UserEntity>>() {
            @Override
            public void onResponse(Call<List<UserEntity>> call, Response<List<UserEntity>> response) {
                Toast.makeText(TinderActivity.this, "Get Data Success", Toast.LENGTH_SHORT).show();
                final DeckAdapter adapter = new DeckAdapter(response.body(), TinderActivity.this);
                cardStack.setAdapter(adapter);


                cardStack.setEventCallback(new SwipeDeck.SwipeEventCallback() {
                    @Override
                    public void cardSwipedLeft(int position) {
                        Toast.makeText(TinderActivity.this, "Card Swiped Left, Calon Ditolak", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void cardSwipedRight(int position) {
                        Toast.makeText(TinderActivity.this, "Card Swiped Right, Calon Diterima", Toast.LENGTH_SHORT).show();
                        JodohEntity jodoh = new JodohEntity();
                        jodoh.setIdJodoh(response.body().get(position));
                        Call<String> call2 = userInterface.addCalon(jodoh, token);
                        call2.enqueue(new Callback<String>() {
                            @Override
                            public void onResponse(Call<String> call, Response<String> response) {
                                Toast.makeText(TinderActivity.this, "Calon Ditambahkan", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onFailure(Call<String> call, Throwable t) {
                                Toast.makeText(TinderActivity.this, "Calon gagal Ditambahkan", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void cardsDepleted() {
                        Toast.makeText(TinderActivity.this, "No woman no cry!", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void cardActionDown() {

                    }

                    @Override
                    public void cardActionUp() {

                    }
                });
            }

            @Override
            public void onFailure(Call<List<UserEntity>> call, Throwable t) {
                System.out.println(t);

                Toast.makeText(TinderActivity.this, "Failed to Register", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
