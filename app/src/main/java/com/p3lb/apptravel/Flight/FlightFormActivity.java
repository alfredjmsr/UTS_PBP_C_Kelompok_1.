package com.p3lb.apptravel.Flight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.p3lb.apptravel.Model.Order.Order;
import com.p3lb.apptravel.Network.ApiHelper;
import com.p3lb.apptravel.Network.ApiInterface;
import com.p3lb.apptravel.R;
import com.p3lb.apptravel.Train.CreateTrainActivity;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FlightFormActivity extends AppCompatActivity {
    EditText txtFirstname, txtLastname, txtEmail, txtPhonenumber;
    Button btnConfirm, btnBack;
    TextView txtTitle;
    SharedPreferences sharedPreferences;
    ApiInterface mApiInterface;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_NAMA = "nama";
    String level, user = "";
    String id_flight,from_flight,to_flight,fromtime_flight,totime_flight, class_flight, price_flight, name_flight = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_flight);
        txtFirstname = (EditText) findViewById(R.id.txtFirstname);
        txtLastname = (EditText) findViewById(R.id.txtLastname);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPhonenumber = (EditText) findViewById(R.id.txtPhonenumber);
        txtTitle = (TextView) findViewById(R.id.txtTitle);
        btnConfirm = (Button) findViewById(R.id.btnBuy);
        btnBack = (Button) findViewById(R.id.btnBack);
        mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        level = sharedPreferences.getString(KEY_LEVEL,null);
        user = sharedPreferences.getString(KEY_NAMA,null);
        Intent mIntent = getIntent();
        id_flight = mIntent.getStringExtra("id_flight");
        from_flight = mIntent.getStringExtra("from_flight");
        to_flight = mIntent.getStringExtra("to_flight");
        fromtime_flight = mIntent.getStringExtra("fromtime_flight");
        totime_flight = mIntent.getStringExtra("totime_flight");
        class_flight = mIntent.getStringExtra("class_flight");
        price_flight = mIntent.getStringExtra("price_flight");
        name_flight = mIntent.getStringExtra("name_flight");
        txtTitle.setText(name_flight+" "+"("+from_flight+"->"+to_flight+")");
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(FlightFormActivity.this, FlightActivity.class);
                startActivity(intent);
            }
        });
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txtFirstname.getText().toString().isEmpty() ||
                        txtLastname.getText().toString().isEmpty() ||
                        txtEmail.getText().toString().isEmpty() ||
                        txtPhonenumber.getText().toString().isEmpty()) {
                    Toasty.warning(FlightFormActivity.this, "Isi data dengan lengkap!", Toast.LENGTH_SHORT).show();
                } else {
                    buyTicket();
                }

            }
        });

    }

    //Akses izin ambil gambar dari penyimpanan
    void buyTicket(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Order> orderCall = mApiInterface.buyFlightTicket(
            txtFirstname.getText().toString(),
                txtLastname.getText().toString(),
                txtEmail.getText().toString(),
                txtPhonenumber.getText().toString(),
                from_flight,
                to_flight,
                fromtime_flight,
                totime_flight,
                name_flight,
                user,
                price_flight
        );
        orderCall.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order> response) {
                if(response.isSuccessful()) {
                    Log.d("RETRO", "ON SUCCESS : " + response.message());
                    Toasty.success(getApplicationContext(), "Sukses beli ticket", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FlightFormActivity.this, FlightActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.error(getApplicationContext(), "Gagal membeli ticket", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();
            }
        });
    }

}
