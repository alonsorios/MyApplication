package com.alonsorios.myapplication;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alonsorios.myapplication.common.Constantes;
import com.alonsorios.myapplication.common.SharedPreferencesManager;
import com.alonsorios.myapplication.data.GlucosaViewModel;
import com.alonsorios.myapplication.retrofit.GlucoAppClient;
import com.alonsorios.myapplication.retrofit.GlucoAppService;
import com.alonsorios.myapplication.retrofit.response.Muestra;

import java.util.List;

public class RegistroGlucosaFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    RecyclerView recyclerView;
    MyRegistroGlucosaRecyclerViewAdapter adapter;
    List<Muestra> listaMuestras;
    GlucosaViewModel glucosaViewModel;
    /*GlucoAppService glucoAppService;
    GlucoAppClient glucoAppClient;*/
    String user;

    public RegistroGlucosaFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static RegistroGlucosaFragment newInstance(int columnCount) {
        RegistroGlucosaFragment fragment = new RegistroGlucosaFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        glucosaViewModel = ViewModelProviders.of(getActivity())
                .get(GlucosaViewModel.class);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

        user = SharedPreferencesManager.getSomeStringValue(Constantes.PREF_USER);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registro_glucosa_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }

            adapter = new MyRegistroGlucosaRecyclerViewAdapter(
                    getActivity(),
                    listaMuestras
            );
            recyclerView.setAdapter(adapter);

            loadMuestra();
        }
        return view;
    }


    private void loadMuestra() {
        glucosaViewModel.getMuestras().observe(getActivity(), new Observer<List<Muestra>>() {
            @Override
            public void onChanged(List<Muestra> muestras) {
                listaMuestras = muestras;
                adapter.setData(listaMuestras);
            }
        });
    }


}
