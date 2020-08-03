package com.alonsorios.myapplication.ui.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alonsorios.myapplication.R;
import com.alonsorios.myapplication.data.GlucosaViewModel;
import com.alonsorios.myapplication.retrofit.response.Muestra;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import java.util.ArrayList;
import java.util.List;

public class ChartFragment extends Fragment {

    LineChart chart;
    GlucosaViewModel glucosaViewModel;
    ArrayList<Entry> arrayval;
    ArrayList<String> xAxisLabel;

    public ChartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chart, container, false);

        glucosaViewModel = ViewModelProviders.of(getActivity()).get(GlucosaViewModel.class);

        findViews(view);
        //asignardatos();
        loadMuestra();

        //Datos del array
        LineDataSet valores =new LineDataSet(arrayval,"Registros de glucosa");
        valores.setColor(getResources().getColor(R.color.colorPrimaryDark));
        valores.setLineWidth(5f);
        valores.setMode(LineDataSet.Mode.CUBIC_BEZIER);
        valores.setDrawFilled(true);
        valores.setFillColor(getResources().getColor(R.color.colorPrimary));
        valores.setFillAlpha(80);
        valores.setValueTextSize(15f);

        LineData lineData = new LineData(valores);

        chart.setData(lineData);

        chart.getDescription().setEnabled(false);
        chart.setHighlightPerDragEnabled(true);
        chart.setHighlightPerTapEnabled(true);
        chart.invalidate();

        //Desactiva los numeros de la izquierda y la derecha
        chart.getAxisLeft().setDrawLabels(false);
        chart.getAxisRight().setDrawLabels(false);
        //Agrega nuevos labels en el eje x
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setLabelRotationAngle(70f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xAxisLabel));
        return view;
    }

    private void findViews(View v) {
        chart = v.findViewById(R.id.chartMuestras);
    }
    private void loadMuestra() {
        glucosaViewModel.getMuestras().observe(getActivity(), new Observer<List<Muestra>>() {
            @Override
            public void onChanged(List<Muestra> muestras) {
                //listaMuestras = muestras;
                llenar_arrays(muestras);
                //glucosaViewModel.getMuestras().getValue();
                //agregarValores(listaMuestras);
            }
        });
    }

    private void llenar_arrays(List<Muestra> lista) {
        arrayval =new ArrayList<>();
        xAxisLabel = new ArrayList<>();
        Muestra Nmuestra;
        String fecha;
        float valor;
        for(int i= 0; i<lista.size();i++){
            Nmuestra = lista.get(i);
            valor = Float.parseFloat(Nmuestra.getValorGlucosa());
            fecha = Nmuestra.getFechaMuestra();
            arrayval.add(new Entry(((float) i),valor));
            xAxisLabel.add(fecha);
        }
    }

}

