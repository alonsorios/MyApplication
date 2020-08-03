package com.alonsorios.myapplication.ui.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.alonsorios.myapplication.data.PerfilViewModel;
import com.alonsorios.myapplication.R;
import com.alonsorios.myapplication.retrofit.request.RequestPerfil;
import com.alonsorios.myapplication.retrofit.response.ResponsePerfil;

/**
 * A simple {@link Fragment} subclass.
 */
public class PerfilFragment extends Fragment {
    private static PerfilViewModel perfilViewModel;
    TextView tvCorreo,tvUsername, tvMedico;
    EditText etModifiaNombre, etModificaApellidos, etPeso, etSexo, etContrasena;
    Button btGuardar;
    String user,contra;
    boolean loadingData = true;

    public static PerfilFragment newInstance() {
        return new PerfilFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        perfilViewModel = ViewModelProviders.of(this).get(PerfilViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_perfil, container, false);

        user = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USER);
        contra = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_CONTRA);
        findViews(view);

        btGuardar.setOnClickListener(v->{
            String username = user;
            String nombre = etModifiaNombre.getText().toString();
            String apellidos = etModificaApellidos.getText().toString();
            String peso = etPeso.getText().toString();
            String sexo = etSexo.getText().toString();
            String password =etContrasena.getText().toString();

            if(nombre.isEmpty()){
                etModifiaNombre.setError("No puedes dejar este campo en blanco");
            }else if(apellidos.isEmpty()){
                etModifiaNombre.setError("No puedes dejar este campo en blanco");
            }else if(peso.isEmpty()){
                etModifiaNombre.setError("No puedes dejar este campo en blanco");
            }else if(sexo.isEmpty()){
                etModifiaNombre.setError("No puedes dejar este campo en blanco");
            }else if(password.isEmpty()){
                etContrasena.setError("No puedes dejar este campo en blanco");
            }else{
                if(!password.equals(contra)){
                    Toast.makeText(getContext(), "No coinciden las contraseñas", Toast.LENGTH_SHORT).show();
                }else {
                    RequestPerfil requestPerfil = new RequestPerfil(username, nombre, apellidos, peso, sexo);
                    perfilViewModel.updateProfile(requestPerfil);
                    etContrasena.setText("");
                    Toast.makeText(getActivity(), "Datos modificados", Toast.LENGTH_SHORT).show();
                    btGuardar.setEnabled(false);
                }
            }
        });


        //ViewModel
        perfilViewModel.userProfile.observe(getActivity(), new Observer<ResponsePerfil>() {
            @Override
            public void onChanged(ResponsePerfil responsePerfil) {
                loadingData = false;
                tvCorreo.setText(responsePerfil.getCorreo());
                etModifiaNombre.setText(responsePerfil.getNombrePaciente());
                etModificaApellidos.setText(responsePerfil.getApellidoPaciente());
                tvUsername.setText(responsePerfil.getUsername());
                etPeso.setText(responsePerfil.getPeso());
                etSexo.setText(responsePerfil.getSexo());
                tvMedico.setText(responsePerfil.getMedicoAsociado());
                //Aqui podemos poner la foto
                if(!loadingData){
                    btGuardar.setEnabled(true);
                }
            }
        });

        return view;
    }

    private void findViews(View view) {
        tvCorreo = view.findViewById(R.id.textViewCorreo);
        etModifiaNombre = view.findViewById(R.id.editTextModificaNombre);
        etModificaApellidos = view.findViewById(R.id.editTextModificaApellidos);
        tvUsername = view.findViewById(R.id.textViewUsername);
        etPeso = view.findViewById(R.id.editTextModificaPeso);
        etSexo = view.findViewById(R.id.editTextModificaSexo);
        tvMedico = view.findViewById(R.id.textViewMedico);
        etContrasena = view.findViewById(R.id.editTextContrasena);
        btGuardar = view.findViewById(R.id.buttonConfirmar);
    }

}
