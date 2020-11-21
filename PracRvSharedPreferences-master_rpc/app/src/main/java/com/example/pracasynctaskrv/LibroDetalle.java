package com.example.pracasynctaskrv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import es.dmoral.coloromatic.ColorOMaticDialog;
import es.dmoral.coloromatic.IndicatorMode;
import es.dmoral.coloromatic.OnColorSelectedListener;
import es.dmoral.coloromatic.colormode.ColorMode;

public class LibroDetalle extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_libro_detalle);

        String titulo ="";
        String autor ="";
        String anio ="";
        String descripcion ="";

        Bundle extras = getIntent().getExtras();
        if (extras !=null){
            titulo = extras.getString("titulo");
            autor = extras.getString("autor");
            anio = extras.getString("anio");
            descripcion = extras.getString("descripcion");
        }

        TextView txttitulo = (TextView)findViewById(R.id.txttitulo);
        TextView txtautor= (TextView)findViewById(R.id.txtautor);
        TextView txtanio = (TextView)findViewById(R.id.txtanio);
        TextView txtdescripcion = (TextView)findViewById(R.id.txtdescripcion);

        txttitulo.setText("Titulo: "+titulo);
        txtautor.setText("Autor: "+autor);
        txtanio.setText("Fecha: "+anio);
        txtdescripcion.setText(descripcion);
    }

    public void cambiarColorBarra(int color, Activity activity){
        Window window = activity.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(color);
        window.setNavigationBarColor(color);
    }

    @Override
    protected void onResume(){
        super.onResume();
        SharedPreferences sharedPreferences = LibroDetalle.this.getPreferences(Context.MODE_PRIVATE);
        int color = sharedPreferences.getInt(getString(R.string.sp_color_bar),0);
        if (color != 0){
            cambiarColorBarra(color, this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            cargarColores();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cargarColores() {
        new ColorOMaticDialog.Builder()
                .initialColor(Color.BLACK)
                .colorMode(ColorMode.ARGB)
                .indicatorMode(IndicatorMode.HEX)
                .onColorSelected(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int color){
                        SharedPreferences sharedPreferences =
                                LibroDetalle.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor =
                                sharedPreferences.edit();
                        editor.putInt(getString(R.string.sp_color_bar),color);
                        editor.commit();
                        cambiarColorBarra(color,LibroDetalle.this);
                    }
                })
                .showColorIndicator(true)
                .create()
                .show(getSupportFragmentManager(), "ColorOMaticDialog");
    }
}
