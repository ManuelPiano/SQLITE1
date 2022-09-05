package com.primera.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toolbar;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private EditText et_codigo, et_descripcion, et_precio;
    private Button btn_guardar, btn_consultar1, btn_consultar2, btn_eliminar, btn_actualizar;

    boolean inputET=false;
    boolean inputEd=false;
    boolean input1=false;
    int resultadoInsert=0;
    Modal ventanas = new Modal();

    ConexionSQLite conexion = new ConexionSQLite(this);
    Dto datos = new Dto();
    AlertDialog.Builder dialogo;

    @Override
    public boolean onKeyDown(int keycode, KeyEvent event){
        if (keycode == KeyEvent.KEYCODE_BACK){
            new android.app.AlertDialog.Builder(this);
            dialogo.setIcon(R.drawable.ic_close);
            dialogo.setTitle("Warning");
            dialogo.setMessage("¿Realmente desea salir?");
            dialogo.setNegativeButton(android.R.string.cancel, null);
            dialogo.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int which) {
                    finishAffinity();

                }
            }).show();
            return true;

        }
        return super.onKeyDown(keycode, event);
    }

    @SuppressLint({"ResourceType", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.mycolor1));
        toolbar.setTitleMargin(0,0,0,0);
        toolbar.setSubtitle("CRUD SQLite-2022");
        toolbar.setSubtitleTextColor(getResources().getColor(R.color.mycolor));
        toolbar.setTitle("Prof. Gámez");
        setSupportActionBar(toolbar);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmacion();
            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ventanas.Search(MainActivity.this);
            }
        });
        et_codigo = findViewById(R.id.et_codigo);
        et_descripcion = findViewById(R.id.et_descripcion);
        et_precio = findViewById(R.id.et_articulo);
        btn_guardar = findViewById(R.id.btn_guardar);
        btn_consultar1=findViewById(R.id.btn_consultar1);
        btn_consultar2=findViewById(R.id.btn_consultar2);
        btn_eliminar = findViewById(R.id.btn_eliminar);

        String senal = "";
        String codigo = "";
        String descripcion = "";
        String precio = "";
        try {
            Intent intent = getIntent();
            Bundle bundle = intent.getExtras();
            if (bundle!=null){
                codigo = bundle.getString("codigo");
                senal = bundle.getString("senal");
                descripcion = bundle.getString("precio");
                if (senal.equals("1")){
                    et_codigo.setText(codigo);
                    et_descripcion.setText(descripcion);
                    et_precio.setText(precio);

                }
            }
        }catch (Exception e){

        }


    }
    private void confirmacion(){
        String mensaje = "¿Realmente desea salir?";
    }
}