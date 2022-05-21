package com.example.app_ban_hang_tot_nghiep.model;

import java.util.List;

public class ItemProductCart {
    private List<String> image;
    private int amount;
    private String variant_id;
    private int price;
    private String product_name;

    public List<String> getImage() {
        return image;
    }

    public int getAmount() {
        return amount;
    }

    public String getVariantId() {
        return variant_id;
    }

    public int getPrice() {
        return price;
    }

    public String getProductName() {
        return product_name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}