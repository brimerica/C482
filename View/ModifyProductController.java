package c482.View;

import c482.Model.InHouse;
import static c482.Model.Inventory.getAllParts;
import static c482.Model.Inventory.getAllProducts;
import static c482.Model.Inventory.lookupPart;
import static c482.Model.Inventory.updateProduct;
import c482.Model.Outsourced;
import c482.Model.Part;
import c482.Model.Product;
import java.net.URL;
import java.util.Arrays;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class ModifyProductController implements Initializable {
    
    private boolean saveClicked = false;
    
    @FXML
    private Button cancel;
    
    @FXML
    private Button save;
    
    @FXML
    private Button add;
    
    @FXML
    private Button delete;
    
    @FXML
    private Button search;
    
    @FXML
    private TextField searchTF;
     
    @FXML
    private TextField productIDTF;
    
    @FXML
    private TextField productNameTF;
    
    @FXML
    private TextField productMaxTF;
    
    @FXML
    private TextField productMinTF;
    
    @FXML
    private TextField productPriceCostTF;
    
    @FXML
    private TextField productInvTF;
    
    @FXML
    private TableView<Part> partsTableModifyProducts;
    
    @FXML
    private TableView<Part> partsTableForProducts;
   
    @FXML
    private TableColumn<Part, Integer> partsTableID;
    
    @FXML
    private TableColumn<Part, String> partsTableName; 
    
    @FXML
    private TableColumn<Part, Double> partsTablePriceCost;
    
    @FXML
    private TableColumn<Part, Integer> partsTableInvLevel;

    @FXML
    private TableColumn<Part, Integer> partsForProductTableID;
    
    @FXML
    private TableColumn<Part, String> partsForProductTableName; 
    
    @FXML
    private TableColumn<Part, Double> partsForProductTablePriceCost;
    
    @FXML
    private TableColumn<Part, Integer> partsForProductTableInvLevel;
    
    private InHouse inhouse;
    private Outsourced outsourced;
    private ObservableList<Part> partsOfProduct = FXCollections.observableArrayList();
    
    Part selectedPart;
    Product selectProduct;
    int selectedProductIndex;
    
    public void setInHouse(InHouse ih){
        this.inhouse = ih;
    }
    
    public void setOutsourced(Outsourced o){
        this.outsourced = o;
    }
    
    public boolean isOkClicked() {
        return saveClicked;
    }
    
    @FXML
    public void modifyProductCancel(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Cancel save?");
        alert.setContentText("Are you sure you want to cancel this information?");
        Optional<ButtonType> choice = alert.showAndWait();
        if(choice.get() == ButtonType.OK){
            cancel.getScene().getWindow().hide();
        }
    }
    
    public void transferCurrentParts(){
        partsTableModifyProducts.setItems(getAllParts());
        partsTableID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        partsTableName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partsTableInvLevel.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        partsTablePriceCost.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
    }
    
    public void transferSelectedProduct(Product product, int index){
        partsTableForProducts.setItems(product.getAssociatedParts());
        partsOfProduct = partsTableForProducts.getItems();
        partsForProductTableID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        partsForProductTableName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        partsForProductTableInvLevel.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
        partsForProductTablePriceCost.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        
        selectedProductIndex = index;
        
        productIDTF.setText(Integer.toString(getAllProducts().get(index).getId()));
        productNameTF.setText(getAllProducts().get(index).getName());
        productPriceCostTF.setText(Double.toString(getAllProducts().get(index).getPrice()));
        productInvTF.setText(Integer.toString(getAllProducts().get(index).getStock()));
        productMaxTF.setText(Integer.toString(getAllProducts().get(index).getMax()));
        productMinTF.setText(Integer.toString(getAllProducts().get(index).getMin()));
    }
    
    @FXML
    public void addPartToProduct(){
        selectedPart = partsTableModifyProducts.getSelectionModel().getSelectedItem();
        if(selectedPart != null){
            partsOfProduct.add(selectedPart);
            partsTableForProducts.setItems(partsOfProduct);

            partsForProductTableID.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
            partsForProductTableName.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
            partsForProductTableInvLevel.setCellValueFactory(cellData -> cellData.getValue().stockProperty().asObject());
            partsForProductTablePriceCost.setCellValueFactory(cellData -> cellData.getValue().priceProperty().asObject());
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part add Error");
            alert.setHeaderText("Part add Error");
            alert.setContentText("Please select a part to add to the product.");
            alert.showAndWait();
        }
    }
        
    @FXML
    public void deleteFromProduct(){
        selectedPart = partsTableForProducts.getSelectionModel().getSelectedItem();
        if(selectedPart != null){
            
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete Confirmation");
            alert.setHeaderText("Delete Part from Product?");
            alert.setContentText("Are you sure you want to delete " + selectedPart.getName() + " from this product?");
            Optional<ButtonType> choice = alert.showAndWait();
            if(choice.get() == ButtonType.OK){
                partsOfProduct.remove(selectedPart);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Part delete Error");
            alert.setHeaderText("Part delete Error");
            alert.setContentText("Please select a part to delete from the product.");
            alert.showAndWait();
        }
    }
    
    @FXML
    public void searchClicked(){
        String searchString = searchTF.getText();
        ObservableList<Part> returnedList = lookupPart(searchString);
        partsTableModifyProducts.setItems(returnedList);
    }
    
    private void throwError(String title, String header, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
    
    @FXML
    public void modifyProductSave(){
        try{
            int tfID = Integer.valueOf(productIDTF.getText());
            String tfName = productNameTF.getText();
            double tfPriceCost = Double.valueOf(productPriceCostTF.getText());
            int tfInv = Integer.valueOf(productInvTF.getText());
            int tfMin = Integer.valueOf(productMinTF.getText());
            int tfMax = Integer.valueOf(productMaxTF.getText());

            String errorTitle;
            String errorHeader;
            String errorText;
       
        
            if(tfMax < tfMin) {
                errorTitle = "Min/Max Error";
                errorHeader = "Check Min/Max Values";
                errorText = "The maximum value cannot be lower than the minimun value. Please correct to save.";
                throwError(errorTitle, errorHeader, errorText);
            } else if(tfName.equals("")) {
                errorTitle = "Product Name Error";
                errorHeader = "Product Name blank";
                errorText = "The product name field needs to be filled in. Please correct to save.";
                throwError(errorTitle, errorHeader, errorText);
            } else if((tfInv > tfMax)||(tfInv < tfMin)){
                errorTitle = "Inventory Error";
                errorHeader = "Check Inventory Count";
                errorText = "The Inventory amount is not within the maximum and minimum amounts.  Please correct to save.";
                throwError(errorTitle, errorHeader, errorText);
            } else {
                if(!partsOfProduct.isEmpty()){
                    Product product = new Product(partsOfProduct, tfID, tfName, tfPriceCost, tfInv, tfMin, tfMax);
                    updateProduct(selectedProductIndex,product);
                    save.getScene().getWindow().hide();
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Empty Parts List");
                    alert.setHeaderText("Empty Parts List");
                    alert.setContentText("There are no parts in the product list to create this product. Please correct to save.");
                    alert.showAndWait();
                }
                
            }
        } catch(NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Form Error");
            alert.setHeaderText("Form Error");
            alert.setContentText("There is a field missing a value. Please correct to save.");
            alert.showAndWait();
        }
        
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }
    
   
}
