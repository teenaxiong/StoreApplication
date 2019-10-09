package com.example.myapplication

import com.example.myapplication.Model.ItemPOJO

class DiscountCalculation {
    companion object
}

fun  DiscountCalculation.Companion.calulateDiscount(item: ItemPOJO) : Double? {
    if(item.discount > 0) {
        var percentageOFF = 1 - (item.discount.div(100))
        var finalPrice = item.price.times(percentageOFF)
        return finalPrice

    }else return null

}