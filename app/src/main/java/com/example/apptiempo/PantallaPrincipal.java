package com.example.apptiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class PantallaPrincipal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);

    }

    public void abrirMapa(View view){
        Intent i = new Intent(this, MapsActivity.class );
        startActivity(i);
    }

}