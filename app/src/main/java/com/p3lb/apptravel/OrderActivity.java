package com.p3lb.apptravel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.p3lb.apptravel.Adapter.FlightAdapter;
import com.p3lb.apptravel.Adapter.OrderAdapter;
import com.p3lb.apptravel.Model.Flight.Flight;
import com.p3lb.apptravel.Model.Flight.GetFlight;
import com.p3lb.apptravel.Model.Order.GetOrder;
import com.p3lb.apptravel.Model.Order.Order;
import com.p3lb.apptravel.Network.ApiHelper;
import com.p3lb.apptravel.Network.ApiInterface;
import com.p3lb.apptravel.Transportation.TransportationActivity;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    TextView txtPrice, txtFromflight,
            txtToflight, txtTimefromflight,
            txtTimetoflight;
    ImageView imgMaskapai;
    Button btnBack;
    SharedPreferences sharedPreferences;
    private RecyclerView mRecyclerView;
    FlightAdapter flightAdapter;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_NAMA = "nama";
    String nama = "";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_order);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv_order);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        txtFromflight = (TextView) findViewById(R.id.txtFromflight);
        txtToflight = (TextView) findViewById(R.id.txtToflight);
        txtTimefromflight = (TextView) findViewById(R.id.txtTotimeflight);
        txtTimetoflight = (TextView) findViewById(R.id.txtTotimeflight);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        nama = sharedPreferences.getString(KEY_NAMA,null);
        btnBack = (Button) findViewById(R.id.btnBack);

        flight();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(OrderActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    public void flight(){
        ApiInterface mApiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Order> call = mApiInterface.showOrder(nama);
        call.enqueue(new Callback<Order>() {
            @Override
            public void onResponse(Call<Order> call, Response<Order>
                    response) {
                Log.d("Tes", ""+response.body().getGetOrders());
                List<GetOrder> getOrders = response.body().getGetOrders();
                mAdapter = new OrderAdapter(getOrders);
                mRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<Order> call, Throwable t) {
                Log.e("Retrofit Get", t.toString());
                Toasty.error(OrderActivity.this, "Gagal memuat data tiket  " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
