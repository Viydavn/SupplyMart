package com.example.MajorProject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class LoginOrSignUpOrProductAdd {

    private byte[] getSHA(String input){
        try{
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            return messageDigest.digest(input.getBytes(StandardCharsets.UTF_8));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    private String getEncryptedPassword(String password){
        String encryptedPassword = "";
        try{
            BigInteger number = new BigInteger(1, getSHA(password));
            StringBuilder hexString = new StringBuilder(number.toString(16));
            return hexString.toString();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public String customerLogin(String email, String password){
        String passafterencryption = getEncryptedPassword(password);
        String query = String.format("SELECT * FROM customer WHERE email = '%s' ", email, passafterencryption);
        try{
            DatabaseConnection dbCon = new DatabaseConnection();
            ResultSet rs = dbCon.getQueryTable(query);
            if(rs != null && rs.next()) {
                return rs.getString("first_name");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String sellerLogin(String email, String password){
        String passafterencryption = getEncryptedPassword(password);
        String query = String.format("SELECT * FROM seller WHERE email = '%s' ", email, passafterencryption);
        try{
            DatabaseConnection dbCon = new DatabaseConnection();
            ResultSet rs = dbCon.getQueryTable(query);
            if(rs != null && rs.next()) {
                return rs.getString("seller_name");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public boolean sellerSignUp(String sellername, String GST, String email, String password, String mobile, String address){
        DatabaseConnection dbCon = new DatabaseConnection();
        String query = String.format("INSERT INTO seller (seller_name, GST, email, password, mobile, address) values('%s','%s','%s','%s','%s','%s')", sellername, GST, email, getEncryptedPassword(password), mobile, address);
        int rowCount = 0;
        try{
            rowCount = dbCon.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }

    public boolean customerSignUp(String firstName, String lastName, String email, String password, String mobile, String address){
        DatabaseConnection dbCon = new DatabaseConnection();
        String query = String.format("INSERT INTO customer (first_name, last_name, email, password, mobile, address) values('%s','%s','%s','%s','%s','%s')", firstName, lastName, email, getEncryptedPassword(password), mobile, address);
        int rowCount = 0;
        try{
            rowCount = dbCon.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }


    public boolean productAdd(String productId, String productname, String Price, String quantity){
        DatabaseConnection dbCon = new DatabaseConnection();
        String query = String.format("INSERT INTO product (product_id, name, quantity, price) values('%s','%s','%s','%s')", productId, productname,quantity,Price);
        int rowCount = 0;
        try{
            rowCount = dbCon.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }
}