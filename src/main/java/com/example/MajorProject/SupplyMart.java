package com.example.MajorProject;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class SupplyMart extends Application {

    public static final int width =1600, height=700, headerBar=150;

    Pane bodyPane = new Pane();
    LoginOrSignUpOrProductAdd loginAndSignUpAndProductadd = new LoginOrSignUpOrProductAdd();
    ProductDetails productDetails = new ProductDetails();

    //global buttons
    Button globalLoginButton;
    Button globalLogoutButton;
    Button deletefromcart;
    Button addToCartButton;
    Button goToCartButton;
    Button goToproducts;
    Button deletefromproduct;
    Button addtoProduct;
    Button buyNowButton;
    Button globalSignUpButton;
    Label customerEmailLabel = null;
    String sellerEmail = null;
    String customerEmail = null;


//Headerbar for logo and upper global buttons
    private GridPane headerBar(){
        Image logo = new Image("C:\\Users\\Vikas Yadav\\Desktop\\Major Project\\SupplyMart\\src\\logo.PNG",120,100,false,false);
        ImageView view = new ImageView(logo);
        Button logobutton = new Button();
        logobutton.setGraphic(view);
        logobutton.setOnAction((new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deletefromcart.setVisible(false);
                addtoProduct.setVisible(false);
                deletefromproduct.setVisible(false);
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(productDetails.getAllProducts());
            }
        }));
        TextField searchfield = new TextField();
        Button searchButton = new Button("Search");
        globalLoginButton = new Button("Log In");
        globalLogoutButton = new Button("Log Out");
        globalLogoutButton.setVisible(false);
        globalSignUpButton = new Button("Sign Up");

        customerEmailLabel = new Label("Welcome User");
        customerEmailLabel.setFont(Font.font("ROBOTO",20));
        searchButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String productName = searchfield.getText();
                //clear body pane first to put searched item
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(productDetails.getProductsByName(productName));
            }
        });
        globalLoginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deletefromcart.setVisible(false);
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginselectPage());
            }
        });

        globalLogoutButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deletefromcart.setVisible(false);
                globalLoginButton.setVisible(true);
                globalLogoutButton.setVisible(false);
                globalSignUpButton.setVisible(true);
                deletefromproduct.setVisible(false);
                goToproducts.setVisible(false);
                addToCartButton.setVisible(true);
                goToCartButton.setVisible(true);
                buyNowButton.setVisible(true);
                addtoProduct.setVisible(false);
                customerEmailLabel.setText("Welcome User");
                customerEmail = null;
                bodyPane.getChildren().clear();
                bodyPane.getChildren().addAll(productDetails.getAllProducts());
            }
        });

        globalSignUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deletefromcart.setVisible(false);
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(signupselectpage());
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), headerBar-10);
        gridPane.setVgap(5);
        gridPane.setHgap(5);
//        gridPane.setAlignment(Pos.CENTER_LEFT);
        gridPane.setStyle("-fx-background-color: #E0A73C");

        gridPane.add(searchfield,50,2);
        searchfield.setPrefWidth(600);
        searchfield.setPrefHeight(40);
        gridPane.add(searchButton,55,2);
        searchButton.setPrefWidth(80);
        searchButton.setPrefHeight(40);
//        gridPane.add(view,2,2);
        gridPane.add(logobutton,2,2);
        gridPane.add(globalLoginButton,110,2);
        gridPane.add(globalLogoutButton,115,2);
        gridPane.add(customerEmailLabel,115,3);
        gridPane.add(globalSignUpButton,115,2);
        globalLoginButton.setPrefWidth(80);
        globalLoginButton.setPrefHeight(40);
        globalLogoutButton.setPrefWidth(80);
        globalLogoutButton.setPrefHeight(40);
        globalSignUpButton.setPrefWidth(80);
        globalSignUpButton.setPrefHeight(40);

        return gridPane;
    }


// seller login page
    private GridPane loginsellerPage(){
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");

        TextField emailTextField = new TextField();
        PasswordField passwordTextfield = new PasswordField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailTextField.getText();
                String password = passwordTextfield.getText();
                String tempCustomerName = loginAndSignUpAndProductadd.sellerLogin(email,password);
                if(tempCustomerName != null) {
                    dialogBox("Login Success");
                    sellerEmail = email;
                    globalLoginButton.setVisible(false);
                    globalLogoutButton.setVisible(true);
                    globalSignUpButton.setVisible(false);
                    goToproducts.setVisible(true);
                    deletefromproduct.setVisible(false);
                    deletefromproduct.setVisible(false);
                    addtoProduct.setVisible(false);
                    addToCartButton.setVisible(false);
                    goToCartButton.setVisible(false);
                    buyNowButton.setVisible(false);
                    customerEmailLabel.setText("Hi!!  " + tempCustomerName);
                    bodyPane.getChildren().clear();

//                    add page to add products
                    bodyPane.getChildren().add(sellerProductPage());
//
                }
                else
                    dialogBox("Login Failed");
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #F2EEE6");

        gridPane.add(emailLabel,0,0);
        gridPane.add(passwordLabel, 0,1);
        gridPane.add(emailTextField, 1,0);
        gridPane.add(passwordTextfield, 1,1);
        gridPane.add(loginButton,0,2);

        return gridPane;
    }


//    customer login page
    private GridPane logincustomerPage(){
        Label emailLabel = new Label("Email");
        Label passwordLabel = new Label("Password");

        TextField emailTextField = new TextField();
        PasswordField passwordTextfield = new PasswordField();
        Button loginButton = new Button("Login");

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String email = emailTextField.getText();
                String password = passwordTextfield.getText();
                String tempCustomerName = loginAndSignUpAndProductadd.customerLogin(email,password);
                if(tempCustomerName != null) {
                    dialogBox("Login Success");
                    customerEmail = email;
                    globalLoginButton.setVisible(false);
                    globalLogoutButton.setVisible(true);
                    globalSignUpButton.setVisible(false);
                    customerEmailLabel.setText("Hi!!  " + tempCustomerName);
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productDetails.getAllProducts());
                }
                else
                    dialogBox("Login Failed");
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #F2EEE6");

        gridPane.add(emailLabel,0,0);
        gridPane.add(passwordLabel, 0,1);
        gridPane.add(emailTextField, 1,0);
        gridPane.add(passwordTextfield, 1,1);
        gridPane.add(loginButton,0,2);

        return gridPane;
    }


// page to select who wants to login seller or customer
    private GridPane loginselectPage(){
        Button sellerloginButton = new Button("Login as a SELLER");
        Button customerloginButton = new Button("Login as a CUSTOMER");
        customerloginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(logincustomerPage());

            }
        });
        sellerloginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(loginsellerPage());



            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #F2EEE6");

        gridPane.add(sellerloginButton,1,0);
        gridPane.add(customerloginButton,2,0);
        sellerloginButton.setPrefWidth(150);
        sellerloginButton.setPrefHeight(40);
        customerloginButton.setPrefWidth(150);
        customerloginButton.setPrefHeight(40);


        return gridPane;
    }


//    page to select who wants to sign up seller or customer
    private GridPane signupselectpage(){
        Button sellerButton = new Button("Sign Up as a SELLER");
        Button customerButton = new Button("Sign Up as a CUSTOMER");
        customerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(signUpcustomerPage());

            }
        });
        sellerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                bodyPane.getChildren().clear();
                bodyPane.getChildren().add(signUpsellerPage());
            }
        });
        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #F2EEE6");

       gridPane.add(sellerButton,1,0);
       gridPane.add(customerButton,2,0);
        sellerButton.setPrefWidth(150);
        sellerButton.setPrefHeight(40);
        customerButton.setPrefWidth(150);
        customerButton.setPrefHeight(40);

        return gridPane;
    }


// page for seller to add products
    private GridPane sellerProductPage(){
        Label productnameLabel = new Label("Product Name *");
        Label PriceLabel = new Label("Price *");
        Label quantityLabel = new Label("Quantity *");

        Label mandatoryLabel = new Label("* marked are mandatory");

        TextField productnameField = new TextField();
        TextField PriceField = new TextField();
        TextField quantityField = new TextField();


        Button addProductButton = new Button("Add");


        addProductButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String productname = productnameField.getText();
                String Price = PriceField.getText();
                String quantity = quantityField.getText();

                if(productname.equals("") || Price.equals("")||quantity.equals("")){
                    dialogBox("Please fill all mandatory fields");
                }
                else if(loginAndSignUpAndProductadd.productAdd(productname, Price, quantity,sellerEmail)){
                    dialogBox("Added Successful");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(sellerProductPage());
                }
                else{
                    dialogBox("Product already registered");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(sellerProductPage());
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #F2EEE6");


        gridPane.add(productnameLabel,0,0);
        gridPane.add(PriceLabel,0,1);
        gridPane.add(quantityLabel,0,2);
        gridPane.add(mandatoryLabel,0,3);


        gridPane.add(productnameField,1,0);
        gridPane.add(PriceField,1,1);
        gridPane.add(quantityField,1,2);

        gridPane.add(addProductButton,1,3);

        return gridPane;
    }



// page for seller signuP
    private GridPane signUpsellerPage(){
        Label sellerNameLabel = new Label("Seller Name *");
        Label emailLabel = new Label("Email *");
        Label passwordLabel = new Label("Password *");
        Label mobileLabel = new Label("Mobile No");
        Label addressLabel = new Label("Address");
        Label GSTLabel = new Label("GST No. *");
        Label mandatoryLabel = new Label("* marked are mandatory");

        TextField sellerNameField = new TextField();
        TextField emailField = new TextField();
        TextField passwordField = new TextField();
        TextField mobileField = new TextField();
        TextField addressField = new TextField();
        TextField GSTField = new TextField();

        Button signUpButton = new Button("Sign Up");


        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String sellername = sellerNameField.getText();
                String GST = GSTField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String mobile = mobileField.getText();
                String address = addressField.getText();

                if(sellername.equals("") || email.equals("") || password.equals("")|| GST.equals("")||address.equals("")){
                    dialogBox("Please fill all mandatory fields");
                }
                else if(loginAndSignUpAndProductadd.sellerSignUp(sellername, GST, email, password, mobile, address)){
                    dialogBox("SignUp Successful");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginsellerPage());
                }
                else{
                    dialogBox("Email Already registered");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(loginsellerPage());
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #F2EEE6");

        gridPane.add(sellerNameLabel,0,0);
        gridPane.add(emailLabel,0,1);
        gridPane.add(passwordLabel,2,1);
        gridPane.add(mobileLabel,2,0);
        gridPane.add(addressLabel,2,2);
        gridPane.add(GSTLabel,0,2);
        gridPane.add(mandatoryLabel,0,4);

        gridPane.add(sellerNameField,1,0);
        gridPane.add(emailField,1,1);
        gridPane.add(passwordField,3,1);
        gridPane.add(mobileField,3,0);
        gridPane.add(addressField,3,2);
        gridPane.add(GSTField,1,2);

        gridPane.add(signUpButton,1,4);

        return gridPane;
    }


//  page for customer signUP
    private GridPane signUpcustomerPage(){
        Label firstNameLabel = new Label("First Name *");
        Label lastNameLabel = new Label("Last Name");
        Label emailLabel = new Label("Email *");
        Label passwordLabel = new Label("Password *");
        Label mobileLabel = new Label("Mobile No");
        Label addressLabel = new Label("Address");
        Label mandatoryLabel = new Label("* marked are mandatory");

        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        TextField emailField = new TextField();
        TextField passwordField = new TextField();
        TextField mobileField = new TextField();
        TextField addressField = new TextField();

        Button signUpButton = new Button("Sign Up");


        signUpButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String firstName = firstNameField.getText();
                String lastName = lastNameField.getText();
                String email = emailField.getText();
                String password = passwordField.getText();
                String mobile = mobileField.getText();
                String address = addressField.getText();

                if(firstName.equals("") || email.equals("") || password.equals("")){
                    dialogBox("Please fill all mandatory fields");
                }
                else if(loginAndSignUpAndProductadd.customerSignUp(firstName, lastName, email, password, mobile, address)){
                    dialogBox("SignUp Successful");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(logincustomerPage());
                }
                else{
                    dialogBox("Email Already registered");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(logincustomerPage());
                }
            }
        });

        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), bodyPane.getMinHeight());
        gridPane.setVgap(5);
        gridPane.setHgap(5);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #F2EEE6");

        gridPane.add(firstNameLabel,0,0);
        gridPane.add(lastNameLabel,2,0);
        gridPane.add(emailLabel,0,1);
        gridPane.add(passwordLabel,2,1);
        gridPane.add(mobileLabel,0,2);
        gridPane.add(addressLabel,2,2);
        gridPane.add(mandatoryLabel,0,3);

        gridPane.add(firstNameField,1,0);
        gridPane.add(lastNameField,3,0);
        gridPane.add(emailField,1,1);
        gridPane.add(passwordField,3,1);
        gridPane.add(mobileField,1,2);
        gridPane.add(addressField,3,2);

        gridPane.add(signUpButton,2,3);

        return gridPane;
    }

//    this is footbar to add buttons in footer like add to cart, by now, products add etc
    private GridPane footerBar(){
        addToCartButton = new Button("Add to Cart");
        goToCartButton = new Button("Go to Cart");
        buyNowButton = new Button("Buy Now");
        deletefromcart = new Button("Remove");
        deletefromproduct = new Button("Remove");
        goToproducts = new Button("My Products");
        addtoProduct = new Button("Add");

        buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                deletefromcart.setVisible(false);
                Product selectedProduct = productDetails.getSelectedProduct();
                if(selectedProduct != null && OrderAndCart.placeOrder(customerEmail, selectedProduct)){
                    dialogBox("Order Placed");

                    if(selectedProduct != null && OrderAndCart.deletefromCart(customerEmail, selectedProduct.getId())){
                        bodyPane.getChildren().clear();
                        bodyPane.getChildren().add(productDetails.getCartItems(customerEmail));
                    }

                }
                else if(customerEmail == null){
                    dialogBox("You need to be logged In to make a order");
                }
                else{
                    dialogBox("Please select a item");
                }
            }
        });

        addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(customerEmail != null){
                    deletefromcart.setVisible(false);
                    Product selectedProduct = productDetails.getSelectedProduct();
                    if(selectedProduct != null && OrderAndCart.addToCart(customerEmail, selectedProduct.getId())){
                        dialogBox("Item added");
                    }
                } else{
                    dialogBox("You need to Login first and then try adding to cart");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(logincustomerPage());
                }
            }
        });

        goToCartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if(customerEmail != null){
                    deletefromcart.setVisible(true);
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productDetails.getCartItems(customerEmail));
                }
                else{
                    dialogBox("Login to view your cart");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(logincustomerPage());
                }
            }
        });

        deletefromcart.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(customerEmail != null){
                    deletefromcart.setVisible(true);
                    Product selectedProduct = productDetails.getSelectedProduct();
                    if(selectedProduct != null && OrderAndCart.deletefromCart(customerEmail, selectedProduct.getId())){
                        dialogBox("Item deleted");
                        bodyPane.getChildren().clear();
                        bodyPane.getChildren().add(productDetails.getCartItems(customerEmail));
                    }
                } else{
                    dialogBox("You need to select product from the cart to Delete");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(logincustomerPage());
                }
            }
        });

        goToproducts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(sellerEmail != null){
                    addtoProduct.setVisible(true);
                    deletefromproduct.setVisible(true);
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(productDetails.getMyProducts(sellerEmail));
                }
                else{
                    dialogBox("Login to view your products Or No Products");
                }
            }
        });
        deletefromproduct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(sellerEmail != null){
                    deletefromproduct.setVisible(true);
                    Product selectedProduct = productDetails.getSelectedProduct();
                    if(selectedProduct != null && OrderAndCart.deletefromMyProducts(sellerEmail, selectedProduct.getId())){
                        dialogBox("Item deleted");
                        bodyPane.getChildren().clear();
                        bodyPane.getChildren().add(productDetails.getMyProducts(sellerEmail));
                    }
                } else{
                    dialogBox("You need to select product from the uploaded products to Delete");
                    bodyPane.getChildren().clear();
                    bodyPane.getChildren().add(logincustomerPage());
                }
            }
        });

        addtoProduct.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                        if(sellerEmail != null){
                            deletefromproduct.setVisible(true);
                            bodyPane.getChildren().clear();
                            bodyPane.getChildren().add(sellerProductPage());
                        }
                     else{
                        dialogBox("You need to select product from the cart to Delete");

                    }
                }
            });


        GridPane gridPane = new GridPane();
        gridPane.setMinSize(bodyPane.getMinWidth(), headerBar-9);
        gridPane.setVgap(5);
        gridPane.setHgap(20);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #E0A73C");
        gridPane.setTranslateY(headerBar+height+5);

        gridPane.add(addToCartButton,0,0);
        gridPane.add(goToCartButton,1,0);
        gridPane.add(buyNowButton,2,0);
        gridPane.add(deletefromcart,1,1);
        gridPane.add(goToproducts,0,0);
        gridPane.add(deletefromproduct,1,0);
        gridPane.add(addtoProduct,2,0);
        addtoProduct.setPrefWidth(120);
        addtoProduct.setPrefHeight(40);
        goToproducts.setPrefWidth(120);
        goToproducts.setPrefHeight(40);
        deletefromproduct.setPrefWidth(120);
        deletefromproduct.setPrefHeight(40);
        deletefromcart.setPrefWidth(80);
        deletefromcart.setPrefHeight(40);
        addToCartButton.setPrefWidth(80);
        addToCartButton.setPrefHeight(40);
        goToCartButton.setPrefWidth(80);
        goToCartButton.setPrefHeight(40);
        buyNowButton.setPrefWidth(80);
        buyNowButton.setPrefHeight(40);
        deletefromcart.setVisible(false);
        goToproducts.setVisible(false);
        deletefromproduct.setVisible(false);
        addtoProduct.setVisible(false);



        return gridPane;
    }

//    Dialog box is common for all and used to give information
    public static void dialogBox(String message){
        Dialog<String> dialog = new Dialog<String>();
        dialog.setTitle("Attention!!!");
        ButtonType button = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.setContentText(message);
        dialog.getDialogPane().getButtonTypes().add(button);
        dialog.showAndWait();
    }

    //this is creating the main window which is added on scene and scene is added to stage
    private Pane createContent(){
        Pane root = new Pane();
        root.setPrefSize(width,height+2*headerBar);

        bodyPane.setMinSize(width,height);
        bodyPane.setTranslateY(headerBar);

        bodyPane.getChildren().addAll(productDetails.getAllProducts());

        // this is adding the grid structured window to main window
        root.getChildren().addAll(headerBar(),bodyPane, footerBar());
        return root;
    }


//    main file where execution starts
    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(SupplyMart.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(createContent());
        stage.setTitle("Supply Mart!!");
        stage.getIcons().add(new Image("C:\\Users\\Vikas Yadav\\Desktop\\Major Project\\SupplyMart\\src\\logo.PNG"));
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}