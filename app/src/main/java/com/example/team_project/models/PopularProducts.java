package com.example.team_project.models;

import java.io.Serializable;

public class PopularProducts implements Serializable {
    private String description;
    private String name;
    private String rating;
    private int price;
    private int quantity;
    private String img_url;

    public PopularProducts() {
    }

    public PopularProducts(String description, String name, String rating, int price, int quantity, String img_url) {
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.price = price;
        this.quantity = quantity;
        this.img_url = img_url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }

    @Override
    public String toString() {
        return "PopularProducts{" +
                "description='" + description + '\'' +
                ", name='" + name + '\'' +
                ", rating='" + rating + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", img_url='" + img_url + '\'' +
                '}';
    }
}
