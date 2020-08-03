package com.alonsorios.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.alonsorios.myapplication.ui.DashboardActivity;
import com.alonsorios.myapplication.ui.MainActivity;

public class MainEmpty extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String user = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USER);

        if (user=="" || user==null){
            Intent i = new Intent(MainEmpty.this, MainActivity.class);
            startActivity(i);
            finish();
        }else{
            Intent i = new Intent(MainEmpty.this, DashboardActivity.class);
            startActivity(i);
            finish();
        }
    }
}
