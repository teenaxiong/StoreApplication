package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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
    String fName, lName, email, password, password02;

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
                TextView textPassword = findViewById(R.id.editTextPassword01);
                TextView textPassword02 = findViewById(R.id.editTextPassword02);
                fName = textFName.getText().toString();
                lName = textLName.getText().toString();
                email = textEmail.getText().toString();
                password = textPassword.getText().toString();
                password02 = textPassword02.getText().toString();

                JSONObject jsonObject = parseJson();

                if(jsonObject != null) {
                    getReponse(jsonObject);
                }
            }
        });

        findViewById(R.id.buttonCancelSignup).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void getReponse(JSONObject postData) {
        OkHttpClient client = new OkHttpClient();

        MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
        String url = "https://visualexample.herokuapp.com/api/user/register";


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
                if (response.code() == 200) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignupActivity.this, "Successfully Register", Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(SignupActivity.this, ShoppingActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                } else if (response.code() == 401) {
                    final String mMessage = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SignupActivity.this, mMessage, Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    final String mMessage = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (mMessage.contains("fname")) {
                                TextView textFName = findViewById(R.id.editTextFirstName);
                                textFName.setError("Please enter a first name");
                            } else if (mMessage.contains("lname")) {
                                TextView textLName = findViewById(R.id.editTextLastName);
                                textLName.setError("Please enter a last name");
                            } else {
                                Toast.makeText(SignupActivity.this, mMessage, Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private JSONObject parseJson() {
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
        return  postData;
   }


}
