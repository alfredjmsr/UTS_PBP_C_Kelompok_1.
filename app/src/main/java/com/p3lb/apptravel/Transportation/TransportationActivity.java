package com.p3lb.apptravel.Transportation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.p3lb.apptravel.Flight.CreateFlightActivity;
import com.p3lb.apptravel.Flight.FlightActivity;
import com.p3lb.apptravel.LoginActivity;
import com.p3lb.apptravel.MainActivity;
import com.p3lb.apptravel.R;
import com.p3lb.apptravel.Train.CreateTrainActivity;
import com.p3lb.apptravel.Train.TrainActivity;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

public class TransportationActivity extends AppCompatActivity {
    Button btnBack, btnFlight, btnTrain;
    String level, nama = "";
    SharedPreferences sharedPreferences;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_NAMA = "nama";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        level = sharedPreferences.getString(KEY_LEVEL,null);
        nama = sharedPreferences.getString(KEY_NAMA,null);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnFlight = (Button) findViewById(R.id.btnFlight);
        btnTrain = (Button) findViewById(R.id.btnTrain);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TransportationActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level.equalsIgnoreCase("1")){
                    Intent intent = new Intent(TransportationActivity.this, CreateFlightActivity.class);
                    startActivity(intent);
                }else{
                    Intent intent = new Intent(TransportationActivity.this, FlightActivity.class);
                    startActivity(intent);
                }

            }
        });
        btnTrain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(level.equalsIgnoreCase("1")){
                   Intent intent = new Intent(TransportationActivity.this, CreateTrainActivity.class);
                   startActivity(intent);
                }else{
                    Intent intent = new Intent(TransportationActivity.this, TrainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }
}