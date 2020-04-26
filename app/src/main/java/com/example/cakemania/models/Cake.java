package com.example.cakemania.models;

public class Cake {
    private String name,weight,price,image_url;
    private boolean add_to_cart,selected;

    public Cake() {
    }

    public Cake(String name, String weight, String price, String image_id) {
        this.name = name;
        this.weight = weight;
        this.price = price;
        this.image_url = "http://kekizadmin.com/uploads/catrgories/"+image_id;
        this.add_to_cart = false;
        this.selected = false;
    }

    public String getName() {
        return name;
    }

    public String getWeight() {
        return weight;
    }

    public String getPrice() {
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

    public void setAdd_to_cart(boolean add_to_cart) {
        this.add_to_cart = add_to_cart;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
