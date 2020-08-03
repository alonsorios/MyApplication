package com.alonsorios.myapplication.data;


import android.widget.Toast;

import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.MyApp;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.alonsorios.myapplication.retrofit.GlucoAppClient;
import com.alonsorios.myapplication.retrofit.GlucoAppService;
import com.alonsorios.myapplication.retrofit.request.RequestPerfil;
import com.alonsorios.myapplication.retrofit.response.ResponsePerfil;

import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PerfilRepository {

    GlucoAppService glucoAppService;
    GlucoAppClient glucoAppClient;
    MutableLiveData<ResponsePerfil> userProfile;
    String user;

    PerfilRepository(){
        glucoAppClient = GlucoAppClient.getInstace();
        glucoAppService = glucoAppClient.getGlucoAppService();
        user = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USER);
        userProfile = getProfile();

    }

    public MutableLiveData<ResponsePerfil> getProfile (){
        if(userProfile == null){
            userProfile = new MutableLiveData<>();
        }

        Call<ResponsePerfil> call = glucoAppService.obtenerinfoUsuario(user);
        call.enqueue(new Callback<ResponsePerfil>() {
            @Override
            public void onResponse(Call<ResponsePerfil> call, Response<ResponsePerfil> response) {
                if(response.isSuccessful()){
                    userProfile.setValue(response.body());
                }else {
                    Toast.makeText(MyApp.getContext(), "Algo a ido mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePerfil> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        });

        return  userProfile;
    }

    public void updateProfile(RequestPerfil requestPerfil){
        Call<ResponsePerfil> call = glucoAppService.modificarPerfil(requestPerfil);
        call.enqueue(new Callback<ResponsePerfil>() {
            @Override
            public void onResponse(Call<ResponsePerfil> call, Response<ResponsePerfil> response) {
                if(response.isSuccessful()){
                    userProfile.setValue(response.body());
                }else {
                    Toast.makeText(MyApp.getContext(), "Algo a ido mal modificando", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponsePerfil> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
