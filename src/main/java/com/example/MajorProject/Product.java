package com.example.MajorProject;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Product {

    private SimpleIntegerProperty id;
    private SimpleStringProperty name;
    private SimpleDoubleProperty price;
    private SimpleIntegerProperty quantity;

    public int getId() {
        return id.get();
    }
    public String getName() {
        return name.get();
    }
    public double getPrice() {
        return price.get();
    }
    public int getQuantity() {
        return quantity.get();
    }

    public Product(int id, String name, double price) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
    }
    public Product(int id, String name, double price,int quantity) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.quantity = new SimpleIntegerProperty(quantity);
    }

//    to get all products
    public static ObservableList<Product> getAllProducts(){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String selectProducts = "SELECT * FROM product";
        try{
            ResultSet rs = databaseConnection.getQueryTable(selectProducts);
            while(rs.next()){
                productList.add(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("name"),
                                rs.getDouble("price")
                        )
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return productList;
    }

//    get products according to keyWord searched
    public static ObservableList<Product> getProductsByName(String productName){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String selectProducts = String.format("SELECT * FROM product WHERE lower(name) like '%%%s%%'", productName.toLowerCase());
        try{
            ResultSet rs = databaseConnection.getQueryTable(selectProducts);
            while(rs.next()){
                productList.add(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("name"),
                                rs.getDouble("price")
                        )
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return productList;
    }

//    for cart items
    public static ObservableList<Product> getCartItems(String customerEmail){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String selectProducts = String.format("SELECT cart.product_id, product.name, product.price FROM cart JOIN product ON cart.product_id = product.product_id JOIN customer ON cart.customer_id = customer.customer_id\n" +
                "WHERE customer.email = '%s'", customerEmail);
        try{
            ResultSet rs = databaseConnection.getQueryTable(selectProducts);
            while(rs.next()){
                productList.add(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("name"),
                                rs.getDouble("price")
                        )
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return productList;
    }


//    to get products uploaded by the seller
    public static ObservableList<Product> getMyProducts(String sellerEmail){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        ObservableList<Product> productList = FXCollections.observableArrayList();
        String selectProducts = String.format("SELECT name, price,quantity,product_id FROM product WHERE seller_id = (SELECT seller_id From seller where email = '%s')",sellerEmail);
        try{
            ResultSet rs = databaseConnection.getQueryTable(selectProducts);
            while(rs.next()){
                productList.add(
                        new Product(
                                rs.getInt("product_id"),
                                rs.getString("name"),
                                rs.getDouble("price"),
                                rs.getInt("quantity")
                                )
                );
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return productList;
    }
}