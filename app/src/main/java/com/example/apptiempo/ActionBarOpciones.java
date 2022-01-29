package com.example.apptiempo;

import android.content.Context;
import android.content.Intent;

public class ActionBarOpciones {

    public ActionBarOpciones(){}

    public void abAcercaDe(Context contexto){
        Intent i = new Intent(contexto, PantallaAcercaDe.class);
        contexto.startActivity(i);
    }

    public void abHome(Context contexto){
        Intent i = new Intent(contexto, PantallaPrincipal.class);
        contexto.startActivity(i);
    }

}
