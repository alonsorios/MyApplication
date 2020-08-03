package com.alonsorios.myapplication.ui;

import androidx.appcompat.app.AppCompatActivity;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.alonsorios.myapplication.R;
import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.alonsorios.myapplication.retrofit.GlucoAppClient;
import com.alonsorios.myapplication.retrofit.GlucoAppService;
import com.alonsorios.myapplication.retrofit.request.RequestLogin;
import com.alonsorios.myapplication.retrofit.response.ResponseLogin;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button button_login, button_go_signup ;
    EditText etUsuario, etContrasena;

    GlucoAppClient glucoAppClient;
    GlucoAppService glucoAppService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();
        retrofitInit();
        findViews();
        events();

    }

    private void retrofitInit() {
        glucoAppClient = GlucoAppClient.getInstace();
        glucoAppService = glucoAppClient.getGlucoAppService();
    }

    private void findViews() {
        button_login = findViewById(R.id.Button_login);
        button_go_signup = findViewById(R.id.Button_registro);
        etUsuario = findViewById(R.id.EditTextUsuario);
        etContrasena = findViewById(R.id.EditTextContra);
    }

    private void events() {
        button_login.setOnClickListener(this);
        button_go_signup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.Button_login:
                goToLogin();
                break;
            case R.id.Button_registro:
                goToSignUp();
                break;
        }
    }

    private void goToLogin() {
        String username = etUsuario.getText().toString();
        String contra = etContrasena.getText().toString();

        if (username.isEmpty()) {
            etUsuario.setError("El username es requerido");
        } else if (contra.isEmpty()) {
            etContrasena.setError("La contraseña es requerida");
        } else {
            RequestLogin requestLogin = new RequestLogin(username,contra);
            Call<ResponseLogin> call = glucoAppService.doLogin(requestLogin);

            call.enqueue(new Callback<ResponseLogin>() {
                @Override
                public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                    if(response.isSuccessful()) {
                        String userconf= response.body().getUsername();
                        if(userconf.equals("failed")){
                            Toast.makeText(MainActivity.this, "Error, revisa tus datos", Toast.LENGTH_SHORT).show();
                        }else{
                            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_USER, response.body().getUsername());
                            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CONTRA, response.body().getContrasena());
                            SharedPreferencesManager.setSomeStringValue(Constantes.PREF_PHOTOURL, response.body().getPhotourl());

                            Toast.makeText(MainActivity.this, "Sesión iniciada correctamente", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                            startActivity(i);
                            // Destruimos este Activity para que no se pueda volver.
                            finish();
                        }
                    }else{
                        Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseLogin> call, Throwable t) {
                    Toast.makeText(MainActivity.this, "Problemas de conexion.Intentelo de nuevo", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }

    private void goToSignUp() {
        Intent i = new Intent(MainActivity.this, SingupActivity.class);
        startActivity(i);
        finish();
    }
}

