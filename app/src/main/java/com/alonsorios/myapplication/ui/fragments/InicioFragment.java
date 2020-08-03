package com.alonsorios.myapplication.ui.fragments;

import android.Manifest;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alonsorios.myapplication.data.GlucosaViewModel;
import com.alonsorios.myapplication.data.InicioViewModel;
import com.alonsorios.myapplication.R;
import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.alonsorios.myapplication.data.PerfilViewModel;
import com.alonsorios.myapplication.retrofit.response.ResponsePrincipal;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.listener.single.CompositePermissionListener;
import com.karumi.dexter.listener.single.DialogOnDeniedPermissionListener;
import com.karumi.dexter.listener.single.PermissionListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.alonsorios.myapplication.common.Constantes.REQUEST_IMAGE_CAPTURE;

/**
 * A simple {@link Fragment} subclass.
 */
public class InicioFragment extends Fragment {
    private static InicioViewModel inicioViewModel;
    private static GlucosaViewModel glucosaViewModel;
    TextView tvnombreUsuario,tvPromedio , tvUltimo, tvFecha;
    ImageView ivPerfil, ivCamara;
    String user;
    PermissionListener permissionListener;
    static final int REQUEST_TAKE_PHOTO = 3;
    String currentPhotoPath;

    String photoUrl;

    public InicioFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        photoUrl = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_PHOTOURL);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_inicio, container, false);
        user = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USER);
        findViews(view);
        assingVal();
        // Seteamos la imagen del usuario de perfil



        if(!photoUrl.isEmpty()) {
            Glide.with(this)
                    .load(Constantes.API_GLUCOAPP_BASE_URL_PHOTO + photoUrl)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .centerCrop()
                    .into(ivPerfil);
        }
        inicioViewModel = ViewModelProviders.of(getActivity()).get(InicioViewModel.class);
        glucosaViewModel = ViewModelProviders.of(getActivity()).get(GlucosaViewModel.class);

        inicioViewModel.photoProfile.observe(getActivity(), new Observer<String>() {
            @Override
            public void onChanged(String photo) {
                Glide.with(getActivity())
                        .load(Constantes.API_GLUCOAPP_BASE_URL_PHOTO + photo)
                        .diskCacheStrategy(DiskCacheStrategy.NONE)
                        .centerCrop()
                        .skipMemoryCache(true)
                        .into(ivPerfil);


            }
        });


        //ViewModel Datos
        inicioViewModel.responsePrincipal.observe(getActivity(), new Observer<ResponsePrincipal>() {
            @Override
            public void onChanged(ResponsePrincipal responsePrincipal) {
                tvPromedio.setText(responsePrincipal.getPromedio());
                tvUltimo.setText(responsePrincipal.getUltimo());
                tvFecha.setText(responsePrincipal.getFechaUltimo());
                //Aqui podemos poner la foto
            }
        });

        ivPerfil.setOnClickListener(v -> {
            checkPermissions();
        });

        ivCamara.setOnClickListener(v->{
            dispatchTakePictureIntent();
        });

        return  view;
    }

    private void dispatchTakePictureIntent() {
        /*Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }*/
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(getContext(), "Error occurred while creating the File", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
               Uri photoURI = FileProvider.getUriForFile(getContext(),
                        "com.alonsorios.myapplication.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    private void assingVal() {
        tvnombreUsuario.setText(user);
    }

    private void findViews(@NotNull View v) {
        tvnombreUsuario = v.findViewById(R.id.Nombre_usuario);
        tvPromedio = v.findViewById(R.id.textViewPromedio);
        tvUltimo = v.findViewById(R.id.textViewUltimo);
        tvFecha = v.findViewById(R.id.textViewFechaUltimo);
        ivPerfil = v.findViewById(R.id.imageViewProfile);
        ivCamara = v.findViewById(R.id.imageViewCamera);
    }

    private void checkPermissions() {
        PermissionListener dialogOnDeniedPermissionListener =
                DialogOnDeniedPermissionListener.Builder.withContext(getActivity())
                .withTitle("Permisos")
                .withMessage("Los permisos solicitados son necesarios para poder seleccionar una foto de perfil")
                .withButtonText("Aceptar")
                .withIcon(R.mipmap.ic_launcher)
                .build();

        permissionListener = new CompositePermissionListener(
                (PermissionListener) getActivity(),
                dialogOnDeniedPermissionListener
        );

        Dexter.withContext(getContext())
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(permissionListener)
                .check();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            /*Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");*/
            galleryAddPic();
            //ivPerfil.setImageBitmap(imageBitmap);

            glucosaViewModel.uploadMuestra(currentPhotoPath);
        }
    }




    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(currentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        getActivity().sendBroadcast(mediaScanIntent);
    }


}
