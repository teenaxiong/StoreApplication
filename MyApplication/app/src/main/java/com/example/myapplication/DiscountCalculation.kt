package com.example.myapplication

import com.example.myapplication.Model.ItemPOJO
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat

class DiscountCalculation {
    companion object{
        public  fun currencyFormat(amount: Double) : String {
            val  format = NumberFormat.getCurrencyInstance();
            return format.format(amount);
        }

        fun roundOffDecimal(number: Double): Double? {
            val df = DecimalFormat("#.##")
            df.roundingMode = RoundingMode.FLOOR
            return df.format(number).toDouble()
        }


        fun  calulateDiscount(item: ItemPOJO) : Double? {
            if(item.discount > 0) {
                var percentageOFF = 1 - (item.discount.div(100))
                var finalPrice = item.price.times(percentageOFF)
                return roundOffDecimal(finalPrice)

            }else return null

        }
    }
}
