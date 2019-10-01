package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

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

public class SignupActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.buttonRegister).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textFName = findViewById(R.id.editTextFirstName);
                TextView textLName = findViewById(R.id.editTextLastName);
                TextView textEmail = findViewById(R.id.txtEmail);
                TextView textPassword = findViewById(R.id.txtPassword);
                TextView textPassword02 = findViewById(R.id.editTextPassword02);

                String fName = textFName.getText().toString();
                String lName = textLName.getText().toString();
                String email = textEmail.getText().toString();
                String password = textPassword.getText().toString();
                String password02 = textPassword02.getText().toString();


                OkHttpClient client = new OkHttpClient();

                MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
                String url = "https://visualexample.herokuapp.com/api/user/register";
                JSONObject postData = new JSONObject();
                try {
                    postData.put("fname", fName);
                    postData.put("lname", lName);
                    postData.put("email", email);
                    postData.put("password01", password);
                    postData.put("password02", password02);

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
                        if(response.code() == 200){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignupActivity.this, "succsss login", Toast.LENGTH_LONG).show();
                                }
                            });
                        }else{
                            final String mMessage = response.body().string();
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(SignupActivity.this, mMessage, Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }
                });
            }
        });
    }
}
