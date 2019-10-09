package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.Model.CurrentUser;
import com.example.myapplication.Model.User;
import com.example.myapplication.R;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class MainActivity extends AppCompatActivity {

    static String JWT_TOKEN;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.buttonCancelSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, SignupActivity.class);
                startActivity(intent);

            }
        });

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textEmail = findViewById(R.id.txtEmail);
                TextView textPassword = findViewById(R.id.editTextPassword01);

                String email = textEmail.getText().toString();
                String password = textPassword.getText().toString();

                OkHttpClient client = new OkHttpClient();

                MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
                String url = "https://visualexample.herokuapp.com/api/user/login";
                JSONObject postData = new JSONObject();
                try {
                    postData.put("email", email);
                    postData.put("password", password);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                RequestBody requestBody = RequestBody.create(postData.toString(), MEDIA_TYPE);
                Request request = new Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .header("Content-Type", "application/json")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        String mMessage = e.getMessage().toString();
                        Log.w("failure Response", mMessage);
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                        if(response.code() == 400){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Toast.makeText(MainActivity.this, response.body().string(), Toast.LENGTH_LONG).show();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }else if (response.code() == 200){
                            final String userBody = response.body().string();
                            System.out.println(userBody);
                            Gson gson = new Gson();
                            final User user = gson.fromJson(userBody, User.class);
                            System.out.println(user.getFirstName());
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Login success", Toast.LENGTH_LONG).show();
                                    Intent intent = new Intent(MainActivity.this, Shopping.class);
                                    CurrentUser.Companion.initializeCurrentUser(MainActivity.this);
                                    CurrentUser.Companion.writeSharedPref(user);
                                    startActivity(intent);
                                    finish();
                                }
                            });
                        }
                    }
                });
            }
        });



    }
}
