package com.alonsorios.myapplication.data;

import android.app.Application;

import com.alonsorios.myapplication.retrofit.response.ResponsePrincipal;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class InicioViewModel extends AndroidViewModel {

    public InicioRepository inicioRepository;
    public LiveData<ResponsePrincipal> responsePrincipal;
    public LiveData<String> photoProfile;


    public InicioViewModel(@NonNull Application application) {
        super(application);
        inicioRepository = new InicioRepository();
        responsePrincipal = inicioRepository.getDatosInicio();
        photoProfile = inicioRepository.getPhotoProfile();
    }

    public void uploadPhoto(String photo){
        inicioRepository.uploadPhoto(photo);
    }

    public void uploadMuestra(String photo){
        inicioRepository.uploadMuestra(photo);
    }
}
