package com.example.apptiempo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EstacionesDatos extends AppCompatActivity {

    private Context esto = this;
    private String dato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_estaciones_datos);

    }

    public void abrirMapa(View view){

        Bundle bundle = getIntent().getExtras();
        String dato = bundle.getString("nombre");

        Intent i=new Intent(esto,MapsActivityConsulta.class);
        i.putExtra("nombre", dato);
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }
}