package com.alonsorios.myapplication.data;

import android.util.Log;
import android.widget.Toast;

import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.MyApp;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.alonsorios.myapplication.retrofit.GlucoAppClient;
import com.alonsorios.myapplication.retrofit.GlucoAppService;
import com.alonsorios.myapplication.retrofit.response.Muestra;
import com.alonsorios.myapplication.retrofit.response.ResponseFotoPerfil;
import com.alonsorios.myapplication.retrofit.response.ResponsePrincipal;
import com.alonsorios.myapplication.retrofit.response.ResponseVinculaFoto;

import java.io.File;

import androidx.lifecycle.MutableLiveData;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InicioRepository {

    GlucoAppService glucoAppService;
    GlucoAppClient glucoAppClient;
    MutableLiveData<ResponsePrincipal> datosInicio;
    MutableLiveData<String> photoProfile;
    String user,foto,fotomuestra;

    InicioRepository(){
        glucoAppClient = GlucoAppClient.getInstace();
        glucoAppService = glucoAppClient.getGlucoAppService();
        user = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USER);
        datosInicio = getDatosInicio();
        if(photoProfile ==null){
            photoProfile = new MutableLiveData<>();
        }

    }

    public MutableLiveData<String> getPhotoProfile() {
        return photoProfile;
    }

    public MutableLiveData<ResponsePrincipal> getDatosInicio (){
        if(datosInicio == null){
            datosInicio = new MutableLiveData<>();
        }

        Call<ResponsePrincipal> call = glucoAppService.obtenerdatosPrincipal(user);
        call.enqueue(new Callback<ResponsePrincipal>() {
            @Override
            public void onResponse(Call<ResponsePrincipal> call, Response<ResponsePrincipal> response) {
                if(response.isSuccessful()){
                    datosInicio.setValue(response.body());
                }else {
                    Toast.makeText(MyApp.getContext(), "Algo a ido mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePrincipal> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        });

        return datosInicio;
    }

    public void uploadPhoto(String photopath){
        File file = new File(photopath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image.jpg"),file);
        Call<ResponseFotoPerfil> call = glucoAppService.subirFotoPerfil(requestBody);//,user);
        call.enqueue(new Callback<ResponseFotoPerfil>() {
            @Override
            public void onResponse(Call<ResponseFotoPerfil> call, Response<ResponseFotoPerfil> response) {
                if(response.isSuccessful()){
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_PHOTOURL,response.body().getUrl());
                    photoProfile.setValue(response.body().getUsername());
                    foto = response.body().getUsername();
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_PHOTOURL, response.body().getUsername());
                    //La enviamos a guardar
                    asociarImagen();
                    Log.d("ayuda", String.valueOf(response.raw()));
                    Log.d("ayuda", String.valueOf(response.body().getUrl()));
                    Log.d("ayuda", String.valueOf(response.body().getUsername()));

                }else{
                    Toast.makeText(MyApp.getContext(), "No funciono"+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseFotoPerfil> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void asociarImagen() {
        Call<ResponseVinculaFoto> call = glucoAppService.asociarFotoPerfil(user,foto);
        call.enqueue(new Callback<ResponseVinculaFoto>() {
            @Override
            public void onResponse(Call<ResponseVinculaFoto> call, Response<ResponseVinculaFoto> response) {
                if(response.isSuccessful()){
                    Log.d("ayuda", String.valueOf(response.body()));
                }else{
                    Log.d("ayuda", String.valueOf(response.raw()));
                }
            }

            @Override
            public void onFailure(Call<ResponseVinculaFoto> call, Throwable t) {
                Log.d("ayuda", "Error de conexion");
            }
        });

    }

    public void uploadMuestra(String photopath){
        File file = new File(photopath);

        RequestBody requestBody = RequestBody.create(MediaType.parse("image.jpg"),file);
        Call<ResponseFotoPerfil> call = glucoAppService.subirMuestra(requestBody);//,user);
        call.enqueue(new Callback<ResponseFotoPerfil>() {
            @Override
            public void onResponse(Call<ResponseFotoPerfil> call, Response<ResponseFotoPerfil> response) {
                if(response.isSuccessful()){
                    fotomuestra = response.body().getUsername();

                    Log.d("ayuda", String.valueOf(response.raw()));
                    Log.d("ayuda", String.valueOf(response.body().getUrl()));
                    Log.d("ayuda", String.valueOf(response.body().getUsername()));
                    //La enviamos a guardar
                    ValorMuestra();
                }else{
                    Toast.makeText(MyApp.getContext(), "No funciono"+response.errorBody(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseFotoPerfil> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ValorMuestra() {
        Call<Muestra> call = glucoAppService.ValorMuestra(user,fotomuestra);
        call.enqueue(new Callback<Muestra>() {
            @Override
            public void onResponse(Call<Muestra> call, Response<Muestra> response) {
                if(response.isSuccessful()){
                    Log.d("ayuda", String.valueOf(response.body().getIdMuestra()));
                    Log.d("ayuda", String.valueOf(response.body().getFechaMuestra()));
                    Log.d("ayuda", String.valueOf(response.body().getValorGlucosa()));
                }else{
                    Log.d("ayuda", String.valueOf(response.raw()));
                }
            }

            @Override
            public void onFailure(Call<Muestra> call, Throwable t) {
                Log.d("ayuda", "Error de conexion");
            }
        });


    }


}
