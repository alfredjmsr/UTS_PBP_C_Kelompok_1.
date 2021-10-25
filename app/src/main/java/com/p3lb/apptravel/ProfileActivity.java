package com.p3lb.apptravel;

import android.content.Context;
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

import com.p3lb.apptravel.Model.Login.Login;
import com.p3lb.apptravel.Model.Login.LoginUsers;
import com.p3lb.apptravel.Network.ApiHelper;
import com.p3lb.apptravel.Network.ApiInterface;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    TextView txtUsername, txtPhonenumber, txtBirthdate;
    Button btnBack, btnLogout;
    String level, nama;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_NAMA = "nama";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtUsername = (TextView) findViewById(R.id.txtUsername);
        txtPhonenumber = (TextView) findViewById(R.id.txtPhonenumber);
        txtBirthdate = (TextView) findViewById(R.id.txtBirthdate);
        btnBack    = (Button) findViewById(R.id.btnBack);
        btnLogout    = (Button) findViewById(R.id.btnLogout);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME,MODE_PRIVATE);
        level = sharedPreferences.getString(KEY_LEVEL,null);
        nama = sharedPreferences.getString(KEY_NAMA,null);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProfileActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences =getSharedPreferences("mypref", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.apply();
                Toasty.success(ProfileActivity.this, "Berhasil Logout", Toast.LENGTH_SHORT).show();
                finish();
                Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
        profile();
    }

    public void profile(){
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Login> postUsersCall = apiInterface.getUser(nama);
        postUsersCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()) {
                    List<LoginUsers> usersList = response.body().getLoginUsers();
                    String phonenumber = usersList.get(0).getPhonenumber_user();
                    String birthdate = usersList.get(0).getBirthdate_user();
                    txtUsername.setText(nama);
                    txtPhonenumber.setText(phonenumber);
                    txtBirthdate.setText(birthdate);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.info(ProfileActivity.this, "Gagal memuat profil !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(ProfileActivity.this, "Gagal Login !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
