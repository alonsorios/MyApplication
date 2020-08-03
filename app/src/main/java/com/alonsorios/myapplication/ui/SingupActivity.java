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
import android.widget.TextView;
import android.widget.Toast;

import com.alonsorios.myapplication.R;
import com.alonsorios.myapplication.retrofit.GlucoAppClient;
import com.alonsorios.myapplication.retrofit.GlucoAppService;
import com.alonsorios.myapplication.retrofit.request.RequestSignup;
import com.alonsorios.myapplication.retrofit.response.ResponseLogin;

public class SingupActivity extends AppCompatActivity implements View.OnClickListener{

    Button button_registro ;
    TextView textView_backtologin;
    EditText etCorreo, etNombre, etApellidos, etUsername, etContrasena, etRContrasena, etPeso, etSexo;
    
    GlucoAppClient glucoAppClient;
    GlucoAppService glucoAppService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);

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
        button_registro = findViewById(R.id.Button_registro);
        textView_backtologin = findViewById(R.id.TextView_backlogin);
        etCorreo = findViewById(R.id.EditTextCorreo);
        etNombre = findViewById(R.id.EditTextNombre);
        etApellidos = findViewById(R.id.EditTextApellidos);
        etUsername = findViewById(R.id.EditTextUsername);
        etContrasena = findViewById(R.id.EditTextContrasena);
        etRContrasena = findViewById(R.id.EditTextRContrasena);
        etPeso = findViewById(R.id.EditTextPeso);
        etSexo = findViewById(R.id.EditTextSexo);
    }

    private void events() {
        button_registro.setOnClickListener(this);
        textView_backtologin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case  R.id.Button_registro:
                /*Intent i = new Intent(SingupActivity.this, VinculaMedico.class);
                i.putExtra("Username","SamanthaE");
                startActivity(i);
                finish();*/
                goToSignUp();
            break;
            case  R.id.TextView_backlogin:
                goToLogin();
            break;
        }
    }

    private void goToSignUp() {
        String Correo = etCorreo.getText().toString();
        String Nombre = etNombre.getText().toString();
        String Apellidos = etApellidos.getText().toString();
        String Username = etUsername.getText().toString();
        String Contrasena = etContrasena.getText().toString();
        String RContrasnea = etRContrasena.getText().toString();
        String Peso = etPeso.getText().toString();
        String Sexo = etSexo.getText().toString();

        if(Correo.isEmpty()){
            etCorreo.setError("El campo de correo de electronico es requerido");
        }else if (Nombre.isEmpty()){
            etNombre.setError("El campo Nombre es requerido");
        }else if (Apellidos.isEmpty()){
            etApellidos.setError("El campo Apellidos es requerido");
        }else if (Username.isEmpty()){
            etUsername.setError("El campo username es requerido");
        }else if (Contrasena.isEmpty()){
            etContrasena.setError("El campo de contraseña es requerido");
        }else if (RContrasnea.isEmpty()){
            etRContrasena.setError("El corroborar la contraseña no puede estar en blanco");
        }else if (Peso.isEmpty()){
            etPeso.setError("Campoo requerido");
        }else if (Sexo.isEmpty()){
            etSexo.setError("Campo requerido");
        }else {
            if(Contrasena.equals(RContrasnea)){
                RequestSignup requestSignup = new RequestSignup(Correo, Nombre, Apellidos, Username,Contrasena, Peso, Sexo);
                Call<ResponseLogin> call = glucoAppService.doSignup(requestSignup);

                call.enqueue(new Callback<ResponseLogin>() {
                    @Override
                    public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
                        if (response.isSuccessful()) {
                            String respuesta= response.body().getUsername();
                            if(respuesta.equals("failed")){
                                Toast.makeText(SingupActivity.this, "El usuario ya existe intenta con nuevos datos", Toast.LENGTH_SHORT).show();
                            }else {
                                Intent i = new Intent(SingupActivity.this, VinculaMedico.class);
                                i.putExtra("Username",response.body().getUsername());
                                startActivity(i);
                                finish();
                            }
                        }else {
                            Toast.makeText(SingupActivity.this, "Algo salio mal", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<ResponseLogin> call, Throwable t) {
                        Toast.makeText(SingupActivity.this, "Error de conexion", Toast.LENGTH_SHORT).show();
                    }
                });

            }else {
                Toast.makeText(this, "La contraseña no coincide con la confirmacion", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void goToLogin() {
        Intent i = new Intent(SingupActivity.this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
