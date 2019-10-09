package com.example.myapplication.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.myapplication.Model.ItemPOJO;
import com.example.myapplication.Model.ItemRoot;
import com.example.myapplication.R;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;

public class ShoppingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);


        Gson gson = new Gson();
        String json = loadJSONFromAsset(); //reads the file from asset and put into a string here.
        ItemRoot itemRoot = gson.fromJson(json, ItemRoot.class); //the string then gets put into the root, which contains the arraylist




    }


    //read the json file.
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("discount.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
