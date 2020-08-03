package com.alonsorios.myapplication.data;

import android.app.Application;

import com.alonsorios.myapplication.data.PerfilRepository;
import com.alonsorios.myapplication.retrofit.request.RequestPerfil;
import com.alonsorios.myapplication.retrofit.response.ResponsePerfil;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class PerfilViewModel extends AndroidViewModel {

    public PerfilRepository perfilRepository;
    public LiveData<ResponsePerfil> userProfile;

    public PerfilViewModel(@NonNull Application application) {
        super(application);
        perfilRepository = new PerfilRepository();
        userProfile = perfilRepository.getProfile();
    }

    public  void  updateProfile (RequestPerfil requestPerfil){
        perfilRepository.updateProfile(requestPerfil);

    }
}
