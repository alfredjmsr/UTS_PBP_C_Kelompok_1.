package com.p3lb.apptravel;

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
import io.github.muddz.styleabletoast.StyleableToast;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    EditText txtUsername, txtPassword;
    TextView txtRegistrasi;
    Button btnLogin;
    String username, password;
    SharedPreferences sharedPreferences;

    private static final String SHARED_PREF_NAME = "mypref";
    private static final String KEY_LEVEL = "level";
    private static final String KEY_NAMA = "nama";
    private static final String KEY_PASS = "password";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtUsername = (EditText) findViewById(R.id.txtUsername);
        txtPassword = (EditText) findViewById(R.id.txtPassword);
        txtRegistrasi = (TextView) findViewById(R.id.txtRegistrasi);
        btnLogin    = (Button) findViewById(R.id.btnLogin);
        sharedPreferences = getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(txtUsername.getText().toString().isEmpty() || txtPassword.getText().toString().isEmpty()){
                    Toasty.warning(LoginActivity.this, "Isi data dengan benar !", Toast.LENGTH_SHORT).show();
                }else{
                    login();
                }
            }
        });
        txtRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }

    public void login(){
        ApiInterface apiInterface = ApiHelper.getClient().create(ApiInterface.class);
        Call<Login> postUsersCall = apiInterface.loginUsers(txtUsername.getText().toString(),txtPassword.getText().toString());
        postUsersCall.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if(response.isSuccessful()) {
                    List<LoginUsers> usersList = response.body().getLoginUsers();
                    String level = usersList.get(0).getRole_user();
                    String nama = usersList.get(0).getNama_user();
                    Log.d("e", ""+level);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(KEY_LEVEL,level);
                    editor.putString(KEY_NAMA,nama);
                    editor.apply();
                    Toasty.success(LoginActivity.this, "Berhasil Login", Toast.LENGTH_SHORT, true).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
                else {
                    Log.d("RETRO", "ON FAIL : " + response.message());
                    Toasty.info(LoginActivity.this, "Pastikan akun anda telah terdaftar !", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable t) {
                Log.d("RETRO", "ON FAILURE : " + t.getMessage());
                Toasty.error(LoginActivity.this, "Gagal Login !", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
