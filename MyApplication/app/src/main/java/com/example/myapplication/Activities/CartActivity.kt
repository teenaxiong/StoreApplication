package com.example.myapplication.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Adapter.CartAdapter
import com.example.myapplication.DiscountCalculation
import com.example.myapplication.Model.ItemPOJO
import com.example.myapplication.R
import com.example.myapplication.calulateDiscount
import kotlinx.android.synthetic.main.activity_cart.*

class CartActivity() : AppCompatActivity() {

    lateinit var cart: ArrayList<ItemPOJO>
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
        }else{
            Toast.makeText(this, "No items in cart yet", Toast.LENGTH_LONG).show()
            finish()
        }

        keepShoppingButton.setOnClickListener {
            finish()
        }

        clearCartButton.setOnClickListener {

        }

        checkOutButton.setOnClickListener {
            if(cart != null){
                var totalCost : Double = 0.00
                for (item in cart){
                    if(DiscountCalculation.calulateDiscount(item) != null){
                        totalCost += DiscountCalculation.calulateDiscount(item)!!
                    }
                }


            }else{
                Toast.makeText(this, "No items in cart", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun discountCalculation(item: ItemPOJO) : Double? {
        if(item.discount > 0) {
            var percentageOFF = 1 - (item.discount.div(100))
            var finalPrice = item.price.times(percentageOFF)
            return finalPrice

        }else return null

    }
}
