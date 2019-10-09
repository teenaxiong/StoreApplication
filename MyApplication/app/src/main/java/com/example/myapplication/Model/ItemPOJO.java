package com.example.myapplication.Model;

import java.io.Serializable;

public class ItemPOJO implements Serializable {
    String name, photo, region;
    double discount, price;

    public ItemPOJO(String name, String photo, String region, double discount, double price) {
        this.name = name;
        this.photo = photo;
        this.region = region;
        this.discount = discount;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}