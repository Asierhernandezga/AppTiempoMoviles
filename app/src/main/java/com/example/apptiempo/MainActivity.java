package com.example.apptiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText editNombre;
    private EditText editContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
        }else{
            Toast.makeText(this,R.string.errorContrasena,Toast.LENGTH_LONG).show();
        }
    }
}