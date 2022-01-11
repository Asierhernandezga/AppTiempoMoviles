package com.example.apptiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTitulo;
    private ImageView imageViewPortada;
    private ImageView imageView2;
    private Button btnSiguiente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textViewTitulo = findViewById(R.id.textViewTitulo);
        imageViewPortada = findViewById(R.id.imageViewPortada);
        imageView2 = findViewById(R.id.imageView2);
        btnSiguiente = findViewById(R.id.btnSiguiente);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .repeat(0)
                .playOn(imageViewPortada);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .repeat(0)
                .playOn(textViewTitulo);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .repeat(0)
                .playOn(imageView2);

        YoYo.with(Techniques.FadeIn)
                .duration(3000)
                .repeat(0)
                .playOn(btnSiguiente);

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
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}