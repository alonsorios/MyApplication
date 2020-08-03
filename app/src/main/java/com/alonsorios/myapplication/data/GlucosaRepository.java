package com.alonsorios.myapplication.data;

import android.util.Log;
import android.widget.Toast;

import com.alonsorios.myapplication.MyRegistroGlucosaRecyclerViewAdapter;
import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.MyApp;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.alonsorios.myapplication.retrofit.GlucoAppClient;
import com.alonsorios.myapplication.retrofit.GlucoAppService;
import com.alonsorios.myapplication.retrofit.response.Muestra;
import com.alonsorios.myapplication.retrofit.response.ResponseFotoPerfil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GlucosaRepository {

    GlucoAppService glucoAppService;
    GlucoAppClient glucoAppClient;
    MutableLiveData<List<Muestra>> allMuestras;
    String user, fotomuestra;

    GlucosaRepository(){
        glucoAppClient = GlucoAppClient.getInstace();
        glucoAppService = glucoAppClient.getGlucoAppService();
        user = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USER);
        allMuestras = getAllMuestras();

    }


    public MutableLiveData<List<Muestra>> getAllMuestras (){
        if(allMuestras == null){
            allMuestras = new MutableLiveData<>();
        }
        Call<List<Muestra>> call = glucoAppService.obtenerMuestras(user);
        call.enqueue(new Callback<List<Muestra>>() {
            @Override
            public void onResponse(Call<List<Muestra>> call, Response<List<Muestra>> response) {
                if (response.isSuccessful()){
                    allMuestras.setValue(response.body());
                }else{
                    Toast.makeText(MyApp.getContext(), "Algo ha ido mal", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Muestra>> call, Throwable t) {
                Toast.makeText(MyApp.getContext(), "Error en la conexion", Toast.LENGTH_SHORT).show();

            }
        });
        return allMuestras;
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
                    List<Muestra> listaClonada = new ArrayList<>();
                    listaClonada.add(response.body());
                    for(int i=0;i<allMuestras.getValue().size();i++){
                        listaClonada.add(new Muestra(allMuestras.getValue().get(i)));
                    }
                    allMuestras.setValue(listaClonada);
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
