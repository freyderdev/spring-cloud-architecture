package com.freyder.cloud.msvc.items.models;

import com.freyder.libs.msvc.commons.entities.Product;

/*
 * Esta clase va contener el objeto producto, lo va envolver
 */
public class Item {

    private Product product;
    private int quantity;

   //constructor con parametros:
    public Item(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }
    public void setProduct(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    //método para calcular el total:
    public Double getTotal() {
        return product.getPrice() * quantity;
    }
}
