
package c482.View;

import c482.Model.InHouse;
import static c482.Model.Inventory.getAllParts;
import static c482.Model.Inventory.getAllProducts;
import static c482.Model.Inventory.deleteProduct;
import static c482.Model.Inventory.addPart;
import static c482.Model.Inventory.deletePart;
import static c482.Model.Inventory.lookupPart;
import static c482.Model.Inventory.lookupProduct;
import c482.Model.Outsourced;
import c482.Model.Part;
import c482.Model.Product;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainAppController { //implements Initializable {
    
    @FXML
    private Button partsAdd;
    
    @FXML
    private TableView<Part> partTable;
        
    @FXML
    private TableColumn<Part, Integer> partsID;
    
    @FXML
    private TableColumn<Part, String> partsName;
    
    @FXML
    private TableColumn<Part, Integer> partsInvLevel;
    
    @FXML
    private TableColumn<Part, Double> partsPriceCost;
    
    @FXML
    private TableColumn<Product, Integer> productsID;
    
    @FXML
    private TableColumn<Product, String> productsName;
    
    @FXML
    private TableColumn<Product, Integer> productsInvLevel;
    
    @FXML
    private TableColumn<Product, Double> productsPriceCost;
    
    @FXML
    private TableView<Product> productTable;
    
    @FXML
    private TextField partSearchTF;
    
    @FXML
    private TextField productSearchTF;
    
    static boolean entered;
    Part selectedPart;
    Product selectedProduct;
    int selectedPartIndex;
    int selectedProductIndex;
    
    public MainAppController(){
    
    }
    
    @FXML
    public void addPartClicked(ActionEvent e) throws IOException {
        Parent addParts;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddPart.fxml"));
        addParts = loader.load();
        
        Scene addPartScene = new Scene(addParts);
        Stage addPartStage = new Stage();
        addPartStage.setScene(addPartScene);
        addPartStage.setTitle("Add Part");
        addPartStage.initModality(Modality.WINDOW_MODAL);
        addPartStage.show();
    }
    
    @FXML
    public void modifyPartClicked(ActionEvent e) throws IOException {
        try{
            selectedPart = partTable.getSelectionModel().getSelectedItem();
            selectedPartIndex = partTable.getSelectionModel().getSelectedIndex();

            Parent modifyParts;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyPart.fxml"));
            modifyParts = loader.load();

            ModifyPartController mpController = loader.getController();
            mpController.transferSelectedPart(selectedPart, selectedPartIndex);


            Scene modifyPartScene = new Scene(modifyParts);
            Stage modifyPartStage = new Stage();
            modifyPartStage.setScene(modifyPartScene);
            modifyPartStage.setTitle("Modify Part");
            modifyPartStage.initModality(Modality.WINDOW_MODAL);
            modifyPartStage.show();
        } catch(Exception error){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modify Error");
            alert.setHeaderText("Modify Error");
            alert.setContentText("Please select an item to modify.");
            alert.showAndWait();
        }
    }
    
    public void deletePartClicked(){
         selectedPart = partTable.getSelectionModel().getSelectedItem();
        if(selectedPart != null){
            selectedPartIndex = partTable.getSelectionModel().getSelectedIndex();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete Part?");
            alert.setContentText("Are you sure you want to delete " + selectedPart.getName() + "?");
            Optional<ButtonType> choice = alert.showAndWait();
            if(choice.get() == ButtonType.OK){
                deletePart(selectedPart);
            }
            
            
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Error");
            alert.setHeaderText("Delete Error");
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait();
        }
    }
    
    public void searchPartClicked(){
        String searchString = partSearchTF.getText();
        ObservableList<Part> returnedList = lookupPart(searchString);
        partTable.setItems(returnedList);
    }
    
    @FXML
    public void addProductClicked(ActionEvent e) throws IOException {
        
        Parent addProducts;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("AddProduct.fxml"));
        addProducts = loader.load();

        AddProductController apcontroller = loader.getController();
        apcontroller.transferCurrentParts();
                
                
        Scene addProductScene = new Scene(addProducts);
        Stage addProductStage = new Stage();
        addProductStage.setScene(addProductScene);
        addProductStage.setTitle("Add Product");
        addProductStage.initModality(Modality.WINDOW_MODAL);
        addProductStage.show();
       
    }
    
    @FXML
    public void modifyProductClicked(ActionEvent e) throws IOException {
        try{
            selectedProduct = productTable.getSelectionModel().getSelectedItem();
            selectedProductIndex = productTable.getSelectionModel().getSelectedIndex();
            
            Parent modifyProduct;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ModifyProduct.fxml"));
            modifyProduct = loader.load();

            ModifyProductController mpController = loader.getController();
            mpController.transferCurrentParts();
            mpController.transferSelectedProduct(selectedProduct, selectedProductIndex);


            Scene modifyProductScene = new Scene(modifyProduct);
            Stage modifyProductStage = new Stage();
            modifyProductStage.setScene(modifyProductScene);
            modifyProductStage.setTitle("Modify Productt");
            modifyProductStage.initModality(Modality.WINDOW_MODAL);
            modifyProductStage.show();
        } catch(Exception error){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Modify Error");
            alert.setHeaderText("Modify Error");
            alert.setContentText("Please select an item to modify.");
            alert.showAndWait();
        }
    }
    
    public void deleteProductClicked(){
        selectedProduct = productTable.getSelectionModel().getSelectedItem();
        if(selectedProduct != null){
            selectedProductIndex = productTable.getSelectionModel().getSelectedIndex();
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete Product?");
            alert.setContentText("Are you sure you want to delete " + selectedProduct.getName() + "?");
            Optional<ButtonType> choice = alert.showAndWait();
            if(choice.get() == ButtonType.OK){
                deleteProduct(selectedProduct);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Delete Error");
            alert.setHeaderText("Delete Error");
            alert.setContentText("Please select an item to delete.");
            alert.showAndWait();
        }
    }
    
    public void searchProductClicked(){
        String searchString = productSearchTF.getText();
        ObservableList<Product> returnedList = lookupProduct(searchString);
        productTable.setItems(returnedList);
    }

    //@Override
    @FXML
    public void initialize() {  //URL url, ResourceBundle rb
        if(!entered){
            addPart(new InHouse(1,"Test",50.00,100,1,500,123));
            addPart(new Outsourced(2,"Test 2",25.00,200,1,200,"Brimerica Inc."));
            //addProduct(new Product(1,"Test",20.00,200,1,300));
            entered=true;
        }
        partsID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        partsName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partsInvLevel.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        partsPriceCost.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());

        productsID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        productsName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        productsInvLevel.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        productsPriceCost.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        updateTableView();
    }
    
    
    public void onExit(){
        Platform.exit();
    }
    
    public void updateTableView(){
        partTable.setItems(getAllParts());
        productTable.setItems(getAllProducts());
    }
   
}
