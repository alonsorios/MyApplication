package com.alonsorios.myapplication.ui;

import androidx.annotation.Nullable;
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
import com.alonsorios.myapplication.retrofit.request.RequestVinculo;
import com.alonsorios.myapplication.retrofit.response.ResponseVinculo;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class VinculaMedico extends AppCompatActivity implements View.OnClickListener {

    String username;
    TextView tvRecibeNombre,tvOmitir;
    Button btCamara,btVincula;
    EditText etCodigo;

    GlucoAppClient glucoAppClient;
    GlucoAppService glucoAppService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vincula_medico);

        getSupportActionBar().hide();

        retrofitInit();
        findViews();
        recibirDatos();
        events();
    }

    private void retrofitInit() {
        glucoAppClient = GlucoAppClient.getInstace();
        glucoAppService = glucoAppClient.getGlucoAppService();
    }

    private void findViews() {
        tvRecibeNombre = findViewById(R.id.textViewRecibeNombre);
        btCamara = findViewById(R.id.buttonCamera);
        etCodigo = findViewById(R.id.editTextCodigo);
        tvOmitir = findViewById(R.id.textViewOmitir);
        btVincula = findViewById(R.id.buttonVincular);
    }

    private void recibirDatos() {
        Bundle extras = getIntent().getExtras();
        username = extras.getString("Username");
        tvRecibeNombre.setText(username+" Escanea el  QR de tu medico");
    }

    private void events() {
        btCamara.setOnClickListener(this);
        tvOmitir.setOnClickListener(this);
        btVincula.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if(result != null) {
            if (result.getContents() != null) {
                etCodigo.setText(result.getContents());
            } else {
                Toast.makeText(this, "Error al escanear el codigo de barras", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id= v.getId();
        switch (id){
            case  R.id.buttonCamera:
                new IntentIntegrator(VinculaMedico.this).initiateScan();
                break;
            case R.id.textViewOmitir:
                Toast.makeText(this, "Inicie sesion", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(VinculaMedico.this, MainActivity.class);
                startActivity(i);
                finish();
                break;
            case  R.id.buttonVincular:
                goToSinc();
                break;
        }
    }

    private void goToSinc() {
        String codigo = etCodigo.getText().toString();
        if(codigo.isEmpty()){
            etCodigo.setError("Si deseas vincular escribe o escanea el código");
        }else {
            //Lammada a la api para asociarle un madico a un paciente
            RequestVinculo requestVinculo = new RequestVinculo(username,codigo);
            Call<ResponseVinculo> call = glucoAppService.doSincQr(requestVinculo);

            call.enqueue(new Callback<ResponseVinculo>() {
                @Override
                public void onResponse(Call<ResponseVinculo> call, Response<ResponseVinculo> response) {
                    if(response.isSuccessful()){
                        String respuesta =  response.body().getResult();
                        if(respuesta.equals("success")){
                            Toast.makeText(VinculaMedico.this, "Usuario vinculado", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(VinculaMedico.this, MainActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            Toast.makeText(VinculaMedico.this, "Codigo erroneo", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(VinculaMedico.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseVinculo> call, Throwable t) {
                    Toast.makeText(VinculaMedico.this, "Error en la conexion", Toast.LENGTH_SHORT).show();
                }
            });

        }
    }
}
