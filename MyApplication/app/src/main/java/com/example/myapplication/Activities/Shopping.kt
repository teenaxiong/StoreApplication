package com.example.myapplication.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.ShoppingAdapter
import com.example.myapplication.Model.ItemPOJO
import com.example.myapplication.Model.ItemRoot
import com.example.myapplication.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_shopping2.*
import java.io.IOException

class Shopping : AppCompatActivity(), ShoppingAdapter.ShoppingAdapterInterface {

    lateinit var cartList: ArrayList<ItemPOJO>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shopping2)

         cartList = ArrayList()

        var gson =  Gson();
        var json = loadJSONFromAsset(); //reads the file from asset and put into a string here.
        var itemRoot = gson.fromJson(json, ItemRoot::class.java); //the string then gets put into the root, which contains the arraylist

        val recycleView = findViewById<RecyclerView>(R.id.recycleView_id);
        recycleView.layoutManager = GridLayoutManager(this, 2);
        val adapter = ShoppingAdapter(itemRoot.results, this);
        recycleView.adapter = adapter;

        viewCartButton.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            intent.putExtra("cartList", cartList)
            startActivity(intent)
        }
    }

    override fun addCartInterfaceFuntion(item: ItemPOJO) {
         cartList.add(item)
        Toast.makeText(this, "${item.name} is added to cart", Toast.LENGTH_LONG).show()

    }


    //read the json file.
    fun loadJSONFromAsset(): String? {
        var json: String? = null
        try {
            val inputStream = this.assets.open("discount.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            json = String(buffer, Charsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }

        return json
    }
}
