package com.example.apptiempo;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginActivity extends AppCompatActivity {

    private EditText editNombre;
    private EditText editContrasena;
    private Button btnEntrar;
    private TextView textViewError;

    private ConnectionClass connectionClass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editNombre = findViewById(R.id.editNombre);
        editContrasena = findViewById(R.id.editContrasena);
        btnEntrar = findViewById(R.id.btnEntrar);
        textViewError =findViewById(R.id.textViewError);

        connectionClass = new ConnectionClass();
    }

    public void clickBotonEntrar(View view){
        String usuario = editNombre.getText().toString();
        String contraseña = editContrasena.getText().toString();

        String use ="";
        String contr= "";

        if(!usuario.equals("") && !contraseña.equals("")) {

        try {
            Connection con = connectionClass.CONN();
            if (con == null) {
                textViewError.setText(R.string.internetError);

            } else {

                String query = "select User, Password from usuarios where User = "  + "\'" + usuario + "\' and Password = "  + "\'" + contraseña + "\'";
               Statement st = con.createStatement();
                ResultSet rs = st.executeQuery(query);
                while (rs.next()) {
                    use = rs.getString("User");
                    contr = rs.getString("Password");
                }

        if(editContrasena.getText().toString().equals(contr) && editNombre.getText().toString().equals(use)) {
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

        } catch (Exception ex) {
            textViewError.setText(R.string.error);
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