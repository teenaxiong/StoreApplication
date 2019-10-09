package com.example.myapplication.Adapter

import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.Model.ItemPOJO
import com.example.myapplication.R
import java.util.concurrent.CopyOnWriteArrayList


class ShoppingAdapter (val itemList: List<ItemPOJO>, val interfaceListner: ShoppingAdapterInterface): RecyclerView.Adapter<ShoppingAdapter.ViewHolder>(){

    //this method is returning the view for each item in the list
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.items_cardview, parent, false)
        return ViewHolder(v)
    }
    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bindItems(itemList[position], interfaceListner)
    }

    //this method is giving the size of the list
    override fun getItemCount(): Int {
        return itemList.count();
    }

    //the class is hodling the list view
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: ItemPOJO, interfaceListner: ShoppingAdapterInterface){
            val itemImageView = itemView.findViewById<ImageView>(R.id.item_image);
            val itemNameTextView = itemView.findViewById<TextView>(R.id.item_name);
            val itemOrignalCost = itemView.findViewById<TextView>(R.id.originalCost);
            val itemSaleCost = itemView.findViewById<TextView>(R.id.saleCost);
            val addButton = itemView.findViewById<Button>(R.id.addButton);


            itemNameTextView.text = item.name;
            itemOrignalCost.text = "$" + item.price
            var finalPriceAfterDiscount = discountCalculation(item)

            if(finalPriceAfterDiscount != null){
                itemSaleCost.text = "$"+ "%.2f".format(finalPriceAfterDiscount)
                itemOrignalCost.setPaintFlags(itemOrignalCost.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
            }


            if (item.photo != null) {
                println(item.name)
                val photoParts = item.photo.split(".")
                val imageStringWithOutPNG = photoParts[0]
                val imgResource = itemView.context.getResources().getIdentifier(imageStringWithOutPNG, "drawable", itemView.context.getPackageName())
                itemImageView.setImageResource(imgResource)
            } else {
                val imgResource = itemView.context.getResources().getIdentifier("not_found", "drawable", itemView.context.getPackageName())
                itemImageView.setImageResource(imgResource)
            }

            addButton.setOnClickListener {
                interfaceListner.addCartInterfaceFuntion(item)
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

    interface ShoppingAdapterInterface{
        fun addCartInterfaceFuntion(item: ItemPOJO)
    }

}