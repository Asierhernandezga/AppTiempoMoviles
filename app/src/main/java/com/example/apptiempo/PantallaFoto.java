package com.example.apptiempo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import android.util.Base64;

public class PantallaFoto extends AppCompatActivity {

    private int STORAGE_PERMISSION_CODE = 1;

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private Button btnCamara;
    private Button btnCamara2;
    private ImageView imgView;
    private ConnectionClass connectionClass;
    private String s;

    private  File imagenArchivo;
    private  String rutaImagen;

    private BitmapDrawable drawable;
    private Bitmap bitmap;

    private String nombreImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_foto);

        btnCamara = findViewById(R.id.btnCamara);
        btnCamara2 = findViewById(R.id.btnCamara2);
        imgView = findViewById(R.id.imageView);

        connectionClass = new ConnectionClass();

        btnCamara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirCamara();
            }
        });

        btnCamara2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                compartirImagen();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_bar_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        ActionBarOpciones abOpciones = new ActionBarOpciones();
        int id = item.getItemId();

        if (id==R.id.abAcercaDe) {
            abOpciones.abAcercaDe(this);
        }
        if(id == R.id.abHome){
            abOpciones.abHome(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == RESULT_OK) {
           //Bundle extras = data.getExtras();
            Bitmap imgBitmap = BitmapFactory.decodeFile(rutaImagen);
            imgView.setImageBitmap(imgBitmap);

        }
    }

    private void abrirCamara() {
        Toast.makeText(this, R.string.abriendoCamara, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // if(intent.resolveActivity(getPackageManager()) != null) {

        imagenArchivo = null;

        try {

            imagenArchivo = crearImagen();

        } catch (IOException ex) {
            Log.e("ERROR", ex.toString());

        }

        if (imagenArchivo != null) {

            Uri fotoUri = FileProvider.getUriForFile(this, "com.example.apptiempo.fileprovider", imagenArchivo);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fotoUri);
            startActivityForResult(intent, 1);

            try {
                Connection con = connectionClass.CONN();

                InputStream is = new FileInputStream(imagenArchivo);

                //encode image to base64 string
                /*
                Bitmap imgBitmapp = BitmapFactory.decodeFile(rutaImagen);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                imgBitmapp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] imageBytes = baos.toByteArray();
                String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                 */

                //decode base64 string to image
                /*
                imageBytes = Base64.decode(imageString, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                image.setImageBitmap(decodedImage);
                */

                Bundle bundle = getIntent().getExtras();
                String dato = bundle.getString("nombre");

     // Toast.makeText(this, dato, Toast.LENGTH_SHORT).show();

                PreparedStatement st = con.prepareStatement("insert into lugares_usuario values(?,?,?,?)");

                st.setString(1,"1");        //No puede repetir esto
                st.setInt(2,1);;
                st.setString(3, rutaImagen);
                st.setInt(4, 1);

                st.execute();
                is.close();
                st.close();

                Toast.makeText(this, "Se ha insertado correctamente", Toast.LENGTH_SHORT).show();

            } catch (FileNotFoundException | SQLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
   // }

    private File crearImagen() throws IOException {
        nombreImagen = "foto_";
        File directorio = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File imagen = File.createTempFile(nombreImagen,".jpg",directorio);

        rutaImagen = imagen.getAbsolutePath();
        return imagen;
    }

    private void compartirImagen() {

       // Toast.makeText(this, "Entra", Toast.LENGTH_SHORT).show();


        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        BitmapDrawable drawable = (BitmapDrawable) imgView.getDrawable();
        Bitmap bitmap = drawable.getBitmap();

        File file = new File(getExternalCacheDir()+"/"+getResources().getString(R.string.app_name)+".png");
        Intent shareint;

        try {
            FileOutputStream outputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
            outputStream.flush();
            outputStream.close();
            shareint = new Intent(Intent.ACTION_SEND);
            shareint.setType("image/*");
            shareint.putExtra(Intent.EXTRA_STREAM,Uri.parse("file://"+rutaImagen));
            shareint.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        } catch (Exception e) {
            throw  new RuntimeException(e);
        }
        startActivity(Intent.createChooser(shareint,"Share image Via:"));

    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}