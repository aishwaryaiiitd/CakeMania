package com.example.cakemania.models;

import java.util.ArrayList;

public class Cake {
    private String name,image_url;
    private ArrayList<String> weight,price;
    private boolean add_to_cart,selected;
    private int current_index;

    public Cake() {
    }

    public Cake(String name, ArrayList<String> weight, ArrayList<String> price, String image_id) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.image_url = "http://kekizadmin.com/uploads/catrgories/"+image_id;
        this.add_to_cart = false;
        this.selected = false;
        this.current_index = 0;
    }

    public String getName() {
        return name;
    }

    public ArrayList<String> getWeight() {
        return weight;
    }

    public ArrayList<String> getPrice() {
        return price;
    }

    public String getImage_url() {
        return image_url;
    }

    public boolean isAdd_to_cart() {
        return add_to_cart;
    }

    public boolean isSelected() {
        return selected;
    }

    public int getCurrent_index() {
        return current_index;
    }

    public void setCurrent_index(int current_index) {
        this.current_index = current_index;
    }

    public void setAdd_to_cart(boolean add_to_cart) {
        this.add_to_cart = add_to_cart;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
