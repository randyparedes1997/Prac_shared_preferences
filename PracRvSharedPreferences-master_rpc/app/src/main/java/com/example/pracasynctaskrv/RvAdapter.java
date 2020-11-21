package com.example.pracasynctaskrv;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RvAdapter extends RecyclerView.Adapter<RvAdapter.LibroViewHolder> implements View.OnClickListener {

    private View.OnClickListener listener;

    List<Libro> libros;
    Context context;

    RvAdapter(List<Libro> personas, Context context){
        this.libros = personas;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView){
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public RvAdapter.LibroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.libro,parent,false);
        LibroViewHolder lvh = new LibroViewHolder(view);

        view.setOnClickListener(this);

        return lvh;
    }

    @Override
    public void onBindViewHolder(@NonNull RvAdapter.LibroViewHolder holder, int position) {
        holder.tituloLibro.setText(libros.get(position).titulo);
        holder.autorlibro.setText(libros.get(position).autor);
        holder.anioLibro.setText(libros.get(position).anio);
        holder.descripcionLibro.setText(libros.get(position).descripcion);
    }

    @Override
    public int getItemCount() {
        return libros.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        if(listener != null){
            listener.onClick(v);
        }
    }

    public static class LibroViewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView tituloLibro;
        TextView autorlibro;
        TextView anioLibro;
        TextView descripcionLibro;

        LibroViewHolder(View itemView){
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cvLibro);
            tituloLibro = (TextView)itemView.findViewById(R.id.txtTituloLibro);
            autorlibro = (TextView)itemView.findViewById(R.id.txtAutorLibro);
            anioLibro = (TextView)itemView.findViewById(R.id.txtAnioLibro);
            descripcionLibro = (TextView)itemView.findViewById(R.id.txtDescripcionLibro);
        }
    }

}
