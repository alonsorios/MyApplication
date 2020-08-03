package com.alonsorios.myapplication.data;

import android.app.Application;

import com.alonsorios.myapplication.retrofit.response.Muestra;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

public class GlucosaViewModel extends AndroidViewModel {
    private  GlucosaRepository glucosaRepository;
    LiveData<List<Muestra>> muestras;
    public LiveData<String> photoProfile;

    public GlucosaViewModel(@NonNull Application application) {
        super(application);
        glucosaRepository = new GlucosaRepository();
        muestras = glucosaRepository.getAllMuestras();

    }
    public  LiveData<List<Muestra>> getMuestras(){ return muestras;}

    public void uploadMuestra(String photo){
        glucosaRepository.uploadMuestra(photo);
    }
}
