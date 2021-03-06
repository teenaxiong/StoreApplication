package com.example.myapplication.Activities

import android.content.Intent
import android.content.SharedPreferences

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.braintreepayments.api.dropin.DropInRequest
import com.example.myapplication.Adapter.CartAdapter
import com.example.myapplication.DiscountCalculation
import com.example.myapplication.Model.CurrentUser
import com.example.myapplication.Model.ItemPOJO
import com.example.myapplication.Model.ItemRoot
import com.example.myapplication.Model.User
import com.example.myapplication.R
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_cart.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.prefs.Preferences
import com.braintreepayments.api.dropin.DropInActivity
import android.R.attr.data
import com.braintreepayments.api.dropin.DropInResult
import android.R.attr.data
import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.example.myapplication.DiscountCalculation.Companion.currencyFormat
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat


class CartActivity() : AppCompatActivity() {

    lateinit var cart: ArrayList<ItemPOJO>
    val REQUEST_CODE = 1
    var totalCost : Double = 0.00
    val TRANSACTION_COMPLETE : String = "COMPLETE"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        if(intent.extras != null) {
            cart = ArrayList()
             cart = intent.getSerializableExtra("cartList") as ArrayList<ItemPOJO>
            val recyclerView = findViewById<RecyclerView>(R.id.linearLayoutRecycleView)
            recyclerView.layoutManager = LinearLayoutManager(this)
            val adapter = CartAdapter(cart)
            recyclerView.adapter = adapter

            for (item in cart){
                if(DiscountCalculation.calulateDiscount(item) != null){
                    totalCost += DiscountCalculation.calulateDiscount(item)!!
                }
            }
            findViewById<TextView>(R.id.editTextTotalCost).setText("TOTAL: ${currencyFormat(totalCost)}")

        }else{
            Toast.makeText(this, "No items in cart yet", Toast.LENGTH_LONG).show()
            finish()
        }

        keepShoppingButton.setOnClickListener {
            finish()
        }


        checkOutButton.setOnClickListener {
            if(cart != null){
                getToken()
            }else{
                Toast.makeText(this, "No items in cart", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun getToken() {

        val client = OkHttpClient()

        val url = "https://visualexample.herokuapp.com/api/braintree/customerBrainTree"
        val postData = JSONObject()
        try {
            postData.put("token", "Bearer ${CurrentUser.getJWT()}")
        } catch (e: JSONException) {
            e.printStackTrace()
        }

       // val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), postData.toString())
        val request = Request.Builder()
                .url(url)
                .header("Content-Type", "application/json")
                .header("authorization", "Bearer ${CurrentUser.getJWT()}")
                .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                val mMessage = e.message.toString()
                Log.w("failure Response", mMessage)
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    val clientToken = JSONObject(response.body?.string())["clientToken"].toString()
                    val dropInRequest = DropInRequest().clientToken(clientToken)
                    startActivityForResult(dropInRequest.getIntent(applicationContext), REQUEST_CODE)
                } else {
                    runOnUiThread {
                        try {
                            Toast.makeText(applicationContext, response.body!!.string(), Toast.LENGTH_LONG).show()
                        } catch (e: IOException) {
                            e.printStackTrace()
                        }
                    }
                }
            }

        })

    }

    @SuppressLint("MissingSuperCall")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode === REQUEST_CODE) {
            if (resultCode === Activity.RESULT_OK) {

                val result = data!!.getParcelableExtra<DropInResult>(DropInResult.EXTRA_DROP_IN_RESULT)
                // use the result to update your UI and send the payment method nonce to your server
                val client = OkHttpClient()
                val url = "https://visualexample.herokuapp.com/api/braintree/transaction"
                val postData = JSONObject()
                try {
                    postData.put("paymentMethodNonce", "${result.paymentMethodNonce?.nonce}")
                    postData.put("paymentAmount", "${totalCost}")
                } catch (e: JSONException) {
                    e.printStackTrace()
                }

                val requestBody = RequestBody.create("application/json; charset=utf-8".toMediaTypeOrNull(), postData.toString())
                val request = Request.Builder()
                        .url(url)
                        .post(requestBody)
                        .header("Content-Type", "application/json")
                        .build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(call: Call, e: IOException) {
                        val mMessage = e.message.toString()
                        Log.w("failure Response", mMessage)
                    }

                    @Throws(IOException::class)
                    override fun onResponse(call: Call, response: Response) {
                        if (response.code == 400) {
                            runOnUiThread {
                                try {
                                    Toast.makeText(applicationContext, response.body!!.string(), Toast.LENGTH_LONG).show()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                        } else if (response.code == 200) {
                            runOnUiThread {
                                try {
                                    Toast.makeText(applicationContext, response.body!!.string(), Toast.LENGTH_LONG).show()
                                    val intent01 = Intent(applicationContext, Shopping::class.java)

                                    intent01.putExtra(TRANSACTION_COMPLETE, TRANSACTION_COMPLETE)
                                    startActivity(intent01)
                                    finish()
                                } catch (e: IOException) {
                                    e.printStackTrace()
                                }
                            }
                        }
                    }
                })


            } else if (resultCode === Activity.RESULT_CANCELED) {
                // the user canceled
                println("CANCELED")
            } else {
                // handle errors here, an exception may be available in
                val error = data?.getSerializableExtra(DropInActivity.EXTRA_ERROR) as Exception
                println(error)
            }
        }
    }
}
