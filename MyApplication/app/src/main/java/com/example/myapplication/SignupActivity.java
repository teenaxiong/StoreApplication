package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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

        findViewById(R.id.registerButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView textUserName = findViewById(R.id.txtEmail);
                TextView textEmail = findViewById(R.id.txtEmail);
                TextView textPassword = findViewById(R.id.txtPassword);

                String username = textUserName.getText().toString();
                String email = textEmail.getText().toString();
                String password = textPassword.getText().toString();

                OkHttpClient client = new OkHttpClient();

                MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=utf-8");
                String url = "https://visualexample.herokuapp.com/api/user/register";
                JSONObject postData = new JSONObject();
                try {
                    postData.put("name", username);
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
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String mMessage = response.body().string();
                        Log.e("SUCCESS", mMessage);
                    }
                });
            }
        });
    }
}
