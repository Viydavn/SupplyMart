package com.example.MajorProject;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.sql.ResultSet;

public class LoginOrSignUpOrProductAdd {


//    for password encryption
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

//for customer login
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

//    for seller login
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


//    for seller signUp
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

//    for customer signup
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

// for seller to add product
    public boolean productAdd(String productname, String Price, String quantity,String email){
        DatabaseConnection dbCon = new DatabaseConnection();

        String query = String.format("INSERT INTO product (name, price, quantity,seller_id) values('%s','%s','%s',(SELECT seller_id FROM seller WHERE email = '%s'))",productname,Price,quantity,email);
        int rowCount = 0;
        try{
            rowCount = dbCon.executeUpdateQuery(query);
        }catch(Exception e){
            e.printStackTrace();
        }
        return rowCount!=0;
    }
}