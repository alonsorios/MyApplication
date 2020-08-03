package com.alonsorios.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import com.alonsorios.myapplication.R;
import com.alonsorios.myapplication.RegistroGlucosaFragment;
import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.alonsorios.myapplication.data.InicioViewModel;
import com.alonsorios.myapplication.ui.fragments.ChartFragment;
import com.alonsorios.myapplication.ui.fragments.InicioFragment;
import com.alonsorios.myapplication.ui.fragments.PerfilFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

public class DashboardActivity extends AppCompatActivity implements PermissionListener {

    ImageView cerrarSesion;
    InicioViewModel  inicioViewModel;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment f = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    f = new InicioFragment();
                    break;
                case R.id.navigation_registros:
                    f = new RegistroGlucosaFragment();
                    break;
                case R.id.navigation_chart:
                    f = new ChartFragment();
                    break;
                case R.id.navigation_perfil:
                    f = new PerfilFragment();
                    break;
            }

            if(f != null) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragmentContainer, f)
                        .commit();
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        inicioViewModel = ViewModelProviders.of(this).get(InicioViewModel.class);

        findViews();
        getSupportActionBar().hide();


        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragmentContainer, new InicioFragment())
                .commit();

        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openLogout(DashboardActivity.this);
            }
        });

        String user = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USER);

    }

    private void findViews() {
        cerrarSesion = findViewById(R.id.imageViewCerrarSesion);
    }

    public void  openLogout (Context contex){
        BottomSettingsFragment pestanaCerrar = BottomSettingsFragment.newInstance();
        pestanaCerrar.show(((AppCompatActivity)contex).getSupportFragmentManager(),"Acciones");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == Constantes.SELECT_PHOTO_GALERY) {
                if (data != null) {
                    Uri imagenSeleccionada = data.getData();
                    String[] filePathColumn = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(imagenSeleccionada, filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();
                        int imagenIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String fotoPath = cursor.getString(imagenIndex);
                        inicioViewModel.uploadPhoto(fotoPath);
                        cursor.close();
                    }
                }
            }
        }
    }

    @Override
    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
        //
        Intent seleccionarFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(seleccionarFoto,Constantes.SELECT_PHOTO_GALERY);
    }

    @Override
    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {
        Toast.makeText(this, "No se puede seleccionar la fotografia", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

    }
}
