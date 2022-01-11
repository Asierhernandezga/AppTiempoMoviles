package com.example.apptiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class LoginActivity extends AppCompatActivity {

    private EditText editNombre;
    private EditText editContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editNombre = findViewById(R.id.editNombre);
        editContrasena = findViewById(R.id.editContrasena);

    }

    public void clickBotonEntrar(View view){
        String usuario = editNombre.getText().toString();
        SharedPreferences preferences = getSharedPreferences("contrasena", Context.MODE_PRIVATE);
        String contrasenaPreferences = preferences.getString("contrasena", "asdf");
        if(editContrasena.getText().toString().equals(contrasenaPreferences) && usuario.equals("Usuario")) {
            Toast.makeText(this,R.string.entradaCorrecta,Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, PantallaPrincipal.class );
            startActivity(i);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        }else{
            Toast.makeText(this,R.string.errorContrasena,Toast.LENGTH_LONG).show();

            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .repeat(0)
                    .playOn(editNombre);

            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .repeat(0)
                    .playOn(editContrasena);

        }
    }

    public void clickBotonRegistrar(View view){
        Intent i = new Intent(this, PantallaRegistrar.class );
        startActivity(i);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}