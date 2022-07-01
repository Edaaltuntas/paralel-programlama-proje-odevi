package com.edaaltuntas;

public class Order {
    String customer_city;
    int order_item_id;
    String product_category_name;

    public String getKey() {
        return customer_city + "|" + product_category_name;
    }

    public int getOrderItemId() {
        return order_item_id;
    }

    public Order(String order_id, String customer_id, String customer_city, String order_item_id, String price, String product_id, String product_category_name) {
        this.customer_city = customer_city;
        this.order_item_id = Integer.parseInt(order_item_id);
        this.product_category_name = product_category_name;
    }
}
