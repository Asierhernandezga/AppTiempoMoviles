package com.example.apptiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class PantallaRegistrar extends AppCompatActivity {

    private EditText editNombre;
    private EditText editContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrar);

        editNombre = findViewById(R.id.editNombre);
        editContrasena = findViewById(R.id.editContrasena);
    }

    public void clickBotonCrearRegistro(View view){

        String usuario = editNombre.getText().toString();
        String contraseña = editContrasena.getText().toString();

        if(!usuario.equals("") && !contraseña.equals("")) {
            Toast.makeText(this,R.string.registroCorrecto,Toast.LENGTH_LONG).show();
            Intent i = new Intent(this, PantallaPrincipal.class );
            startActivity(i);
        }else{
            Toast.makeText(this,R.string.registroIncorrectoCamposVacios,Toast.LENGTH_LONG).show();
        }


    }
}