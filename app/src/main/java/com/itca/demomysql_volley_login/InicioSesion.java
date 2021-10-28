package com.itca.demomysql_volley_login;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

public class InicioSesion extends AppCompatActivity {

    private EditText et_usu, et_clave;
    private Button btn_Login, btn_Perdiste, btn_Registrate;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pruebas_volley);

        et_usu = (EditText) findViewById(R.id.et_usu);
        et_clave = (EditText) findViewById(R.id.et_clave);

        btn_Login = (Button) findViewById(R.id.btn_Login);
        btn_Perdiste = (Button) findViewById(R.id.btn_Perdiste);
        btn_Registrate = (Button) findViewById(R.id.btn_Registrate);


        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(getApplicationContext(), et_usu.getText().toString(), et_clave.getText().toString());
            }
        });

        btn_Perdiste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_Registrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InicioSesion.this, registroUsuario.class);
                startActivity(intent);
                finish();
            }
        });



    }

    private void login(final Context context, final String user, final String pass) {

        String url = "https://alexis-lopez.000webhostapp.com/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                //Toast.makeText(context, "" + response, Toast.LENGTH_SHORT).show();

                if (response.equals("0")) {
                    Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        JSONObject respuestaJSON = new JSONObject(response.toString());
                        String id = respuestaJSON.getString("id");
                        String nombre = respuestaJSON.getString("nombre");
                        String apellidos = respuestaJSON.getString("apellidos");
                        String correo = respuestaJSON.getString("correo");
                        String usuario = respuestaJSON.getString("usuario");
                        String clave = respuestaJSON.getString("clave");
                        String tipo = respuestaJSON.getString("tipo");
                        String estado = respuestaJSON.getString("estado");
                        String pregunta = respuestaJSON.getString("pregunta");
                        String respuesta = respuestaJSON.getString("respuesta");
                        String fecha_registro = respuestaJSON.getString("fecha_registro");

                        Intent intent = new Intent(InicioSesion.this, MainActivity.class);
                        startActivity(intent);

                        //Toast.makeText(InicioSesion.this, response.toString(), Toast.LENGTH_SHORT).show();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(InicioSesion.this, "Error: Problemas con la conexion al servidor", Toast.LENGTH_SHORT).show();
            }
        }){

            @Override
            protected HashMap<String, String> getParams(){
                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("usu", user.trim());
                parametros.put("pass", pass.trim());
                return parametros;
            }
        };
    MySingleton.getInstance(context).addToRequestQueue(request);
    }
}



