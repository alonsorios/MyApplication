package com.alonsorios.myapplication.retrofit;

import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.alonsorios.myapplication.retrofit.request.RequestLogin;
import com.alonsorios.myapplication.retrofit.request.RequestPerfil;
import com.alonsorios.myapplication.retrofit.request.RequestSignup;
import com.alonsorios.myapplication.retrofit.request.RequestVinculo;
import com.alonsorios.myapplication.retrofit.response.Muestra;
import com.alonsorios.myapplication.retrofit.response.ResponseFotoPerfil;
import com.alonsorios.myapplication.retrofit.response.ResponseLogin;
import com.alonsorios.myapplication.retrofit.response.ResponsePerfil;
import com.alonsorios.myapplication.retrofit.response.ResponsePrincipal;
import com.alonsorios.myapplication.retrofit.response.ResponseVinculaFoto;
import com.alonsorios.myapplication.retrofit.response.ResponseVinculo;

import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GlucoAppService {
    String user = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USER);

    @POST("auth/login.php")
    Call<ResponseLogin>doLogin(@Body RequestLogin requestLogin);

    @POST("auth/signup.php")
    Call<ResponseLogin> doSignup(@Body RequestSignup requestSignup);

    @POST("auth/vincularmedico.php")
    Call<ResponseVinculo> doSincQr(@Body RequestVinculo requestVinculo);

    @GET("auth/muestras.php")
    Call<List<Muestra>> obtenerMuestras(@Query("username") String username);

    @GET("auth/infousuario.php")
    Call<ResponsePerfil> obtenerinfoUsuario(@Query("username") String username);

    @GET("auth/datosPrincipal.php")
    Call<ResponsePrincipal> obtenerdatosPrincipal(@Query("username") String username);

    @PUT("auth/modificarPerfil.php")
    Call<ResponsePerfil> modificarPerfil(@Body RequestPerfil requestPerfil);

    @Multipart
    @POST("auth/upload_foto_perfil.php")
    Call<ResponseFotoPerfil> subirFotoPerfil(@Part("file\"; filename=\"photo.jpeg\" ") RequestBody file);

    @GET("auth/vinculaFoto.php")
    Call<ResponseVinculaFoto> asociarFotoPerfil (@Query("username") String username, @Query("url") String url);

    @Multipart
    @POST("auth/uploadMuestra.php")
    Call<ResponseFotoPerfil> subirMuestra(@Part("file\"; filename=\"photo.jpeg\" ") RequestBody file);

    @GET("auth/valorMuestra.php")
    Call<Muestra> ValorMuestra (@Query("username") String username, @Query("url") String url);
}
