package com.example.pracasynctaskrv;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.coloromatic.ColorOMaticDialog;
import es.dmoral.coloromatic.IndicatorMode;
import es.dmoral.coloromatic.OnColorSelectedListener;
import es.dmoral.coloromatic.colormode.ColorMode;

public class MainActivity extends AppCompatActivity {

    private EditText mInputLibro;
    private TextView mTextoTitulo;
    private TextView mTextoAutor;
    private TextView txtTituloLibro;
    private TextView txtAutorLibro;
    private TextView txtAnioLibro;
    private TextView txtDescripcionLibro;
    private ImageView ImgLibro;

    public static List<Libro> libros;

    private void initializaData() {
        libros = new ArrayList<>();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputLibro = (EditText)findViewById(R.id.ingresoLibro);
        txtTituloLibro = (TextView)findViewById(R.id.txtTituloLibro);
        txtAutorLibro = (TextView)findViewById(R.id.txtAutorLibro);
        txtAnioLibro = (TextView)findViewById(R.id.txtAnioLibro);
        txtDescripcionLibro = (TextView)findViewById(R.id.txtDescripcionLibro);
        ImgLibro = (ImageView) findViewById(R.id.imgLibro);

        initializaData();

        final RecyclerView rv = (RecyclerView)findViewById(R.id.RvLibros);
        rv.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(false);
        rv.setLayoutManager(linearLayoutManager);

        RvAdapter rvAdapter = new RvAdapter(libros, this);

        rvAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LibroDetalle.class);
                intent.putExtra("titulo", libros.get(rv.getChildAdapterPosition(v)).titulo);
                intent.putExtra("autor", libros.get(rv.getChildAdapterPosition(v)).autor);
                intent.putExtra("anio", libros.get(rv.getChildAdapterPosition(v)).anio);
                intent.putExtra("descripcion", libros.get(rv.getChildAdapterPosition(v)).descripcion);
                startActivity(intent);
            }
        });

        rv.setAdapter(rvAdapter);
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
        SharedPreferences sharedPreferences = MainActivity.this.getPreferences(Context.MODE_PRIVATE);
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
                                MainActivity.this.getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor =
                                sharedPreferences.edit();
                        editor.putInt(getString(R.string.sp_color_bar),color);
                        editor.commit();
                        cambiarColorBarra(color,MainActivity.this);
                    }
                })
                .showColorIndicator(true)
                .create()
                .show(getSupportFragmentManager(), "ColorOMaticDialog");
    }


    public void buscarLibro(View view){
        if(mInputLibro.getText().toString().equals("")){
            libros.clear();
        }
        else {
            String cadenaBusqueda = mInputLibro.getText().toString();
            new ConseguirLibro(txtTituloLibro, txtAutorLibro, txtAnioLibro, txtDescripcionLibro).execute(cadenaBusqueda);
            super.onRestart();
        }
    }

}
