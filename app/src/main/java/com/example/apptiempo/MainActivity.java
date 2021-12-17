package com.example.apptiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTitulo;
    private ImageView imageViewPortada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTitulo = findViewById(R.id.textViewTitulo);
        imageViewPortada = findViewById(R.id.imageViewPortada);

    }

    public void clickBotonEntrar(View view){

        /*
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */

        Intent i = new Intent(this, LoginActivity.class );
        startActivity(i);
    }
}