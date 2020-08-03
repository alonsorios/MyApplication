package com.alonsorios.myapplication.ui;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alonsorios.myapplication.R;
import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationView;

public class BottomSettingsFragment extends BottomSheetDialogFragment {


    public static BottomSettingsFragment newInstance() {
        return new BottomSettingsFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.bottom_settings_fragment, container, false);

        final NavigationView  nav = view.findViewById(R.id.navigation_view_bottom_settings);

        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                if(id == R.id.cerrar_sesion){
                    //Eliminamos las shared preferences
                    Toast.makeText(getActivity(), "Vuelve pronto", Toast.LENGTH_SHORT).show();
                    getDialog().dismiss();
                    //Limpiamos variables
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_USER, "");
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_PHOTOURL, "");
                    SharedPreferencesManager.setSomeStringValue(Constantes.PREF_CONTRA, "");
                    Intent i = new Intent(getActivity(), MainActivity.class);
                    startActivity(i);
                    // Destruimos este Activity para que no se pueda volver.
                    getActivity().finish();
                }
                return false;
            }
        });

        return view;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }



}
