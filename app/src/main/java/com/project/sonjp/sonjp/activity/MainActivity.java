package com.project.sonjp.sonjp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.sonjp.sonjp.R;
import com.project.sonjp.sonjp.model.ServerRequest;
import com.project.sonjp.sonjp.model.ServerResponse;
import com.project.sonjp.sonjp.model.User;
import com.project.sonjp.sonjp.rest.ApiClient;
import com.project.sonjp.sonjp.rest.ApiInterface;
import com.project.sonjp.sonjp.utils.Constants;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity {

    private EditText correo, contrasena;
    private Button login, registrate, llamanos, ubicanos;
    private TextView textView1, textView2;
    private ProgressBar progressBar;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Vincular las variables con el diseño o con el componente
        correo = (EditText) findViewById(R.id.etCorreo);
        contrasena = (EditText) findViewById(R.id.etContrasena);
        login = (Button) findViewById(R.id.btn_login);
        registrate = (Button) findViewById(R.id.btn_registrate);

        ubicanos = (Button) findViewById(R.id.btn_ubicanos);
        llamanos = (Button) findViewById(R.id.btn_llamanos);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);

    }

    @Override
    protected void onResume(){
        super.onResume();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = correo.getText().toString(),
                        password = contrasena.getText().toString();

                if(!username.isEmpty() && !password.isEmpty()) {
                    progressBar.setVisibility(View.VISIBLE);
                    loginProcess(username,password);

                } else {
                    Toast.makeText(MainActivity.this,"Datos Incompletos!",Toast.LENGTH_SHORT).show();

                }

            }
        });



    }

    private void loginProcess(String username,String password){

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        ServerRequest request = new ServerRequest();
        request.setUsername(username);
        request.setPassword(password);
        Call<ServerResponse> response = apiService.operation(username, password);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                //ServerResponse resp = response.body();


                /*if(resp.getExito().equals(Constants.STATUS)){
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putBoolean(Constants.IS_LOGGED_IN,true);
                    editor.putString(Constants.TAG1,resp.getMensaje());
                    editor.putString(Constants.STATUS,resp.getExito());
                    editor.apply();
                }*/


                /*if(resp.getExito().equals(Constants.SUCCESS)){
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else if (resp.getExito().equals((Constants.FAILURE))){
                    Toast.makeText(MainActivity.this,"Usuario o Contraseña incorrecta!",Toast.LENGTH_SHORT).show();


                }*/

                if(response.body().getExito().equals("false")){
                    Toast.makeText(MainActivity.this, response.body().getExito(),Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this,response.body().getExito(),Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                progressBar.setVisibility(View.INVISIBLE);

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progressBar.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Toast.makeText(MainActivity.this,"No hay conexión!",Toast.LENGTH_SHORT).show();
            }
        });
    }




}
