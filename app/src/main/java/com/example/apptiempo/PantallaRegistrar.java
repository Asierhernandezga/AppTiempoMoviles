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
    private EditText editContrasena2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrar);

        editNombre = findViewById(R.id.editNombre);
        editContrasena = findViewById(R.id.editContrasena);
        editContrasena2 = findViewById(R.id.editContrasena2);
    }

    public void clickBotonCrearRegistro(View view){

        String usuario = editNombre.getText().toString();
        String contraseña = editContrasena.getText().toString();
        String repetirContraseña = editContrasena2.getText().toString();

        if(!usuario.equals("") && !contraseña.equals("") && !repetirContraseña.equals("")) {

            if(contraseña.equals(repetirContraseña)) {
                Toast.makeText(this,R.string.registroCorrecto,Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, PantallaPrincipal.class );
                startActivity(i);
            } else {
                Toast.makeText(this,R.string.registroContraseñaNoCoincide,Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(this,R.string.registroIncorrectoCamposVacios,Toast.LENGTH_LONG).show();
        }
    }
}