package com.example.app_ban_hang_tot_nghiep.model;

import java.util.List;

public class ChangeCartParams {
    String token;
    List<ItemProductCart> products;

    public ChangeCartParams(String token, List<ItemProductCart> list) {
        this.token = token;
        this.products = list;
    }
}
