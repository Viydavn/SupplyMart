package com.example.MajorProject;


import java.sql.ResultSet;

public class OrderAndCart {



    //    this is to place Order
    public static boolean placeOrder(String customerEmail, Product product){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = String.format("INSERT INTO orders (customer_id, product_id) VALUES((SELECT customer_id FROM customer WHERE email = '%s'), %s)",customerEmail, product.getId());
        int rowCount = 0;
        try{
            rowCount = databaseConnection.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }

//    This is add products to cart
    public static boolean addToCart(String customerEmail, int product_id){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = String.format("INSERT INTO cart VALUES((SELECT customer_id FROM customer WHERE email = '%s'), %s)",customerEmail, product_id);
        int rowCount = 0;
        try{
            rowCount = databaseConnection.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }


//    This is to delete items from the cart
    public static boolean deletefromCart(String customerEmail, int product_id){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = String.format("DELETE FROM cart where(SELECT customer_id FROM customer WHERE email = '%s') && product_id = '%s'",customerEmail, product_id);
        int rowCount = 0;
        try{
            rowCount = databaseConnection.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }

//    to delete products added by seller
    public static boolean deletefromMyProducts(String sellerEmail, int product_id){
        DatabaseConnection databaseConnection = new DatabaseConnection();
        String query = String.format("DELETE FROM product where(SELECT seller_id FROM seller WHERE email = '%s') && product_id = '%s'",sellerEmail, product_id);
        int rowCount = 0;
        try{
            rowCount = databaseConnection.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }





}