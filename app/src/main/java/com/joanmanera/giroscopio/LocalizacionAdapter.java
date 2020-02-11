package com.joanmanera.giroscopio;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocalizacionAdapter extends RecyclerView.Adapter<LocalizacionAdapter.LocalizacionViewHolder> {

    private ArrayList<Localizacion> localizaciones;
    private ILocalizacionListener listener;

    public LocalizacionAdapter(ArrayList<Localizacion> localizaciones, ILocalizacionListener listener){
        this.localizaciones = localizaciones;
        this.listener = listener;
    }

    @NonNull
    @Override
    public LocalizacionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_localizacion, parent, false);
        return new LocalizacionViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalizacionViewHolder holder, int position) {
        holder.bindLocalizacion(position);
    }

    @Override
    public int getItemCount() {
        return localizaciones.size();
    }

    public void setLocalizaciones(ArrayList<Localizacion> localizaciones){
        this.localizaciones = localizaciones;
        notifyDataSetChanged();
    }


    public class LocalizacionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView tvName;
        private ILocalizacionListener listener;

        public LocalizacionViewHolder(@NonNull View view, ILocalizacionListener listener) {
            super(view);
            this.listener = listener;
            view.setOnClickListener(this);

            tvName = view.findViewById(R.id.tvName);
        }

        public void bindLocalizacion(int position){
            Localizacion l = localizaciones.get(position);

            tvName.setText(l.getNombre());
        }

        @Override
        public void onClick(View v) {
            if (listener != null){
                listener.onItemSelected(getAdapterPosition());
            }
        }
    }

    public interface ILocalizacionListener{
        void onItemSelected(int pos);
    }
}
