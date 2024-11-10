package com.example.bmicalculator;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


import com.example.bmicalculator.databinding.ActivityMainBinding;

import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private EditText editTextHeight, editTextWeight, editTextViewLabel, editTextViewMessage;
    private Button buttonCalculateBMI, buttonEducateMe;
    private List<String> moreLinks;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextHeight = findViewById(R.id.editTextHeight);
        editTextWeight = findViewById(R.id.editTextWeight);
        editTextViewLabel = findViewById(R.id.editTextViewLabel);
        editTextViewMessage = findViewById(R.id.editTextViewMessage);
        buttonCalculateBMI = findViewById(R.id.buttonCalculateBMI);
        buttonEducateMe = findViewById(R.id.buttonEducateMe);

        buttonCalculateBMI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateBMI();
            }
        });

        buttonEducateMe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                if(moreLinks != null && !moreLinks.isEmpty()){
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(moreLinks.get(0)));
                    startActivity(browserIntent);
                }else{
                    Toast.makeText(MainActivity.this, "No lines available.",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void calculateBMI(){
        int height = Integer.parseInt(editTextHeight.getText().toString());
        int weight = Integer.parseInt(editTextWeight.getText().toString());
        Retrofit retrofit = new Retrofit.Builder().baseUrl("http://webstrar99.fulton.asu.edu/page8/Service1.svc/")
                .addConverterFactory(GsonConverterFactory.create()).build();


        BmiApiService apiService = retrofit.create(BmiApiService.class);
        Call<BmiResponse> call = apiService.calculateBMI(height, weight);


//        Call<BmiResponse> qqcall = apiService.qqqcalculateBMI();

        call.enqueue(new Callback<BmiResponse>() {
            @Override
            public void onResponse(Call<BmiResponse> call, Response<BmiResponse> response) {
                if (response.isSuccessful()) {
                    BmiResponse bmiResponse = response.body();
                    if (bmiResponse == null) {
                        System.out.println("null ================== ");
                    }

                    System.out.println("bmi is ---------------- " + bmiResponse.getBmi());
                    if (bmiResponse != null) {
                        editTextViewLabel.setText(String.format("BMI: %.2f", bmiResponse.getBmi()));
                        editTextViewMessage.setText(bmiResponse.getRisk());

                        double bmi = bmiResponse.getBmi();
                        if (bmi < 18) {
                            editTextViewMessage.setTextColor(Color.BLUE);
                        } else if (bmi >= 18 && bmi < 25) {
                            editTextViewMessage.setTextColor(Color.GREEN);
                        } else if (bmi >= 25 && bmi < 30) {
                            editTextViewMessage.setTextColor(Color.MAGENTA);
                        } else if (bmi > 30) {
                            editTextViewMessage.setTextColor(Color.RED);
                        }

                        moreLinks = bmiResponse.getMore();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to get BMI", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BmiResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "API call failed", Toast.LENGTH_SHORT).show();
            }
        });


    }





}