package com.example.pracasynctaskrv;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;

public class ConseguirLibro extends AsyncTask<String,Void,String> {

    private WeakReference<TextView> txtTituloLibro;
    private WeakReference<TextView> txtAutorLibro;
    private WeakReference<TextView> txtAnioLibro;
    private WeakReference<TextView> txtDescripcionLibro;
    private WeakReference<ImageView> imgLibro;

    ConseguirLibro(TextView tituloLibro, TextView autorLibro, TextView anioLibro, TextView descripcionLibro){
        this.txtTituloLibro = new WeakReference<>(tituloLibro);
        this.txtAutorLibro = new WeakReference<>(autorLibro);
        this.txtAnioLibro = new WeakReference<>(anioLibro);
        this.txtDescripcionLibro = new WeakReference<>(descripcionLibro);
    }

    @Override
    protected String doInBackground(String... strings) {
        return UtilidadesRed.obtenerInformacionLibro(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        try{
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            int i=0;
            String titulo = null;
            String autores = null;
            String anio = null;
            String descripcion = null;
            int foto = 0;

            MainActivity.libros.clear();

            for (i=0 ; i<itemsArray.length(); i++){
                JSONObject libro = itemsArray.getJSONObject(i);
                JSONObject volumeInfo = libro.getJSONObject("volumeInfo");
                try {
                    titulo = volumeInfo.getString("title");
                    autores = volumeInfo.getString("authors");
                    anio = volumeInfo.getString("publishedDate");
                    descripcion = volumeInfo.getString("description");
                    MainActivity.libros.add(new Libro(titulo,autores,anio,descripcion));

                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            if (titulo != null && autores !=null){

            }else {

            }

        }catch (JSONException e){
            e.printStackTrace();
        }
    }


}

