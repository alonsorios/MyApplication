package com.alonsorios.myapplication;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alonsorios.myapplication.retrofit.response.Muestra;

import java.util.List;


public class MyRegistroGlucosaRecyclerViewAdapter extends RecyclerView.Adapter<MyRegistroGlucosaRecyclerViewAdapter.ViewHolder> {

    private Context ctx;
    private List<Muestra> mValues;

    public MyRegistroGlucosaRecyclerViewAdapter(Context contexto, List<Muestra> items) {
        mValues = items;
        ctx = contexto;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_registro_glucosa, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) { //Este se comporta como un ciclo
        if(mValues != null ){
            holder.mItem = mValues.get(position);

            holder.tvGlucosa.setText(holder.mItem.getValorGlucosa());
            holder.tvFecha.setText(holder.mItem.getFechaMuestra());
        }
    }

    public void setData (List<Muestra> listaMuestra){
        this.mValues = listaMuestra;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mValues!=null)
            return mValues.size();
        else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvGlucosa;
        public final TextView tvFecha;
        public Muestra mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvGlucosa = view.findViewById(R.id.textViewGlucosa);
            tvFecha = view.findViewById(R.id.textViewFecha);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvGlucosa.getText() + "'";
        }
    }
}
