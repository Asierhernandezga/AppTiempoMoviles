package com.example.apptiempo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PantallaRegistrar extends AppCompatActivity {

    private EditText editNombre;
    private EditText editContrasena;
    private EditText editContrasena2;
    private TextView textViewError;

    private ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_registrar);

        editNombre = findViewById(R.id.editNombre);
        editContrasena = findViewById(R.id.editContrasena);
        editContrasena2 = findViewById(R.id.editContrasena2);

        textViewError =findViewById(R.id.textViewError);

        connectionClass = new ConnectionClass();
    }

    public void clickBotonCrearRegistro(View view){

        String usuario = editNombre.getText().toString();
        String contraseña = editContrasena.getText().toString();
        String repetirContraseña = editContrasena2.getText().toString();

        String use ="";
        String contr= "";

        if(!usuario.equals("") && !contraseña.equals("") && !repetirContraseña.equals("")) {

            if(contraseña.equals(repetirContraseña)) {

                try {
                    Connection con = connectionClass.CONN();
                    if (con == null) {
                        textViewError.setText(R.string.internetError);
                    } else {

                        String numero ="2";
                        String query = "insert into usuarios values('"+numero+"','"+usuario+"','"+contraseña+"')";

                        Statement st = con.createStatement();
                        st.executeUpdate(query);

                        Toast.makeText(this,R.string.registroCorrecto,Toast.LENGTH_LONG).show();
                Intent i = new Intent(this, PantallaPrincipal.class );
                startActivity(i);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

                    }

                } catch (Exception ex) {
                    textViewError.setText(R.string.error);
                }

            } else {
                Toast.makeText(this,R.string.registroContraseñaNoCoincide,Toast.LENGTH_LONG).show();

                YoYo.with(Techniques.Shake)
                        .duration(1000)
                        .repeat(0)
                        .playOn(editContrasena);

                YoYo.with(Techniques.Shake)
                        .duration(1000)
                        .repeat(0)
                        .playOn(editContrasena2);
            }

        }else{
            Toast.makeText(this,R.string.registroIncorrectoCamposVacios,Toast.LENGTH_LONG).show();

            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .repeat(0)
                    .playOn(editNombre);

            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .repeat(0)
                    .playOn(editContrasena);

            YoYo.with(Techniques.Shake)
                    .duration(1000)
                    .repeat(0)
                    .playOn(editContrasena2);

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}