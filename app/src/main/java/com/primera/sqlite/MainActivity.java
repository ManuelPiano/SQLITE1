package com.primera.sqlite;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.animation.Animator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class MainActivity extends AppCompatActivity {
    private EditText et_codigo, et_descripcion, et_precio;
    private Button btn_guardar, btn_consultar1, btn_consultar2, btn_eliminar, btn_actualizar;
    private Object Toolbar;

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
            })
                    .show();
            return true;

        }
        return super.onKeyDown(keycode, event);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back));
        toolbar.setTitleTextColor(getResources().getColor(R.color.mycolor1));
        toolbar.setTitleMargin(0,0,0,0);
        toolbar.setSubtitle("CRUD SQLite-2022");
        toolbar.setTitle("Manuel Alvarado Sis21A");
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
                descripcion = bundle.getString("descripcion");
                precio = bundle.getString("precio");
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
        dialogo = new AlertDialog.Builder(MainActivity.this);
        dialogo.setIcon(R.drawable.ic_close);
        dialogo.setTitle("Warning");
        dialogo.setMessage(mensaje);
        dialogo.setCancelable(false);
        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
              MainActivity.this.finish();
            }
        });
        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        dialogo.show();

    }
    public void alta (View view){
        if (et_codigo.getText().toString().length()==0){
            et_codigo.setError("Campo obligatorio");
            inputET=false;
        }else{
            inputET=true;
        }
        if (et_descripcion.getText().toString().length()==0){
            et_descripcion.setError("Campo obligatorio");
            inputEd=false;
        }else{
            inputEd=true;
        }
        if (et_precio.getText().toString().length()==0){
            et_precio.setError("Campo obligatorio");
            input1=false;
        }else{
            input1=true;
        }
        if (inputET && inputEd && input1){
            try {
                datos.setCodigo(Integer.parseInt(et_codigo.getText().toString()));
                datos.setDescripcion(et_descripcion.getText().toString());
                datos.setPrecio(Double.parseDouble(et_precio.getText().toString()));
                if (conexion.InserTradicional(datos)){
                    Toast.makeText(this, "Registro Agregado satisfactoriamente", Toast.LENGTH_SHORT).show();
                    limpiarDatos();
                }else{
                    Toast.makeText(getApplicationContext(), "Error. ya existe un registro"+"Codigo:" + et_codigo.getText().toString(),Toast.LENGTH_LONG).show();
                    limpiarDatos();
                }
            }catch (Exception e){
                Toast.makeText(this,"Error. Ya existe.",Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void limpiarDatos() {
        et_codigo.setText(null);
        et_descripcion.setText(null);
        et_precio.setText(null);
        et_codigo.requestFocus();
    }

    public void mensaje(String mensaje){
        Toast.makeText(this,""+mensaje, Toast.LENGTH_SHORT).show();
    }
    public void consultaporcodigo(View v){
        if (et_codigo.getText().toString().length()==0){
            et_codigo.setError("Campo Obligatorio");
            inputET=false;

        }else{
            inputET=true;
        }
        if (inputET){
            String Codigo = et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(Codigo));
            if (conexion.consultaArticulos(datos)){
                et_descripcion.setText(""+datos.getPrecio());
            }else{
                Toast.makeText(this, "No existe un articulo con dicho codigo.",Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }else{
            Toast.makeText(this,"Ingrese el código del articulo a buscar",Toast.LENGTH_SHORT).show();

        }
    }
    public void consultapordescripcion(View view){
        if(et_descripcion.getText().toString().length()==0){
            et_descripcion.setError("Campo obligatorio");
            inputEd=false;
        }else{
            inputEd=true;
        }
        if(inputEd){
            String descripcion = et_descripcion.getText().toString();
            datos.setDescripcion(descripcion);
            if (conexion.consultarDescripcion(datos)){
                et_codigo.setText(""+datos.getCodigo());
                et_descripcion.setText(datos.getDescripcion());
                et_precio.setText(""+datos.getPrecio());
            }else{
                Toast.makeText(this, "No existe un articulo con dicha descripcion", Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }else{
            Toast.makeText(this,"Ingrese la descripcion del articulo a buscar",Toast.LENGTH_SHORT).show();
        }
    }
    public void bajaporcodigo(View view){
        if (et_codigo.getText().toString().length()==0){
            et_codigo.setError("campo obligatorio");
            inputET=false;
        }else{
            inputET= true;
        }
        if (inputET){
            String cod=et_codigo.getText().toString();
            datos.setCodigo(Integer.parseInt(cod));
            if (conexion.bajaCodigo(MainActivity.this,datos)){
                limpiarDatos();
            }else{
                Toast.makeText(this,"No existe un articulo con dicho codigo",Toast.LENGTH_SHORT).show();
                limpiarDatos();
            }
        }
    }
    public void modificacion(View view){
        if (et_codigo.getText().toString().length()==0){
            et_codigo.setError("campo obligatorio");
            inputET=false;
        }else{
            inputET=true;
        }
        if (inputET){
            String cod = et_codigo.getText().toString();
            String descripcion = et_descripcion.getText().toString();
            double precio = Double.parseDouble(et_precio.getText().toString());
            datos.setCodigo(Integer.parseInt(cod));
            datos.setDescripcion(descripcion);
            datos.setPrecio(precio);
            if (conexion.modificar(datos)){
                Toast.makeText(this,"Registro modificado Correctamente",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this,"no se han encontrado resultados para la busqueda especificada",Toast.LENGTH_SHORT).show();
            }
        }
    }
}