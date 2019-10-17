package com.example.myapplication.Adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.DiscountCalculation
import com.example.myapplication.Model.ItemPOJO
import com.example.myapplication.R

class CartAdapter(val itemList: ArrayList<ItemPOJO>) : RecyclerView.Adapter <CartAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.recycleview_cart, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(itemList[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(item: ItemPOJO) {
            val itemImageView = itemView.findViewById<ImageView>(R.id.item_image);
            val itemNameTextView = itemView.findViewById<TextView>(R.id.item_name);
            val itemOrignalCost = itemView.findViewById<TextView>(R.id.originalCost);
            val itemSaleCost = itemView.findViewById<TextView>(R.id.saleCost);

            itemNameTextView.text = item.name;
            itemOrignalCost.text = "$" + item.price
            var finalPriceAfterDiscount = DiscountCalculation.calulateDiscount(item)
            if(finalPriceAfterDiscount != null){
                itemSaleCost.text = DiscountCalculation.currencyFormat(finalPriceAfterDiscount)
                        //"$"+ "%.2f".format(finalPriceAfterDiscount)
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
        }

    }





}