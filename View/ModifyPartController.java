package c482.View;

import c482.Model.InHouse;
import static c482.Model.Inventory.updatePart;
import c482.Model.Outsourced;
import c482.Model.Part;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;

public class ModifyPartController implements Initializable {
    
    private boolean saveClicked = false;
    
    @FXML
    private Button cancel;
    
    @FXML
    private Button save;
    
    @FXML
    private Label partSource;
    
    @FXML
    private ToggleGroup modifyPartToggle;
    
    @FXML
    private RadioButton partInHouse;
    
    @FXML
    private RadioButton partOutsourced;
    
    @FXML
    private TextField partSourceTF;
    
    @FXML
    private TextField partIDTF;
    
    @FXML
    private TextField partNameTF;
    
    @FXML
    private TextField partMaxTF;
    
    @FXML
    private TextField partMinTF;
    
    @FXML
    private TextField partPriceCostTF;
    
    @FXML
    private TextField partInvTF;
    
    Part selectedPart;
    int selectedPartIndex;

    
    @FXML
    public void modifyPartCancel(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Cancel save?");
        alert.setContentText("Are you sure you want to cancel this information?");
        Optional<ButtonType> choice = alert.showAndWait();
        if(choice.get() == ButtonType.OK){
            cancel.getScene().getWindow().hide();
        }
    }
    
    public void transferSelectedPart(Part part, int index){
        selectedPartIndex = index;
        selectedPart = part;
        
        partIDTF.setText(String.valueOf(part.getId()));
        partNameTF.setText(part.getName());
        partInvTF.setText(String.valueOf(part.getStock()));
        partMaxTF.setText(String.valueOf(part.getMax()));
        partMinTF.setText(String.valueOf(part.getMin()));
        partPriceCostTF.setText(String.valueOf(part.getPrice()));
        
        if(part instanceof InHouse){
            InHouse inHouse = (InHouse) part;
            partSourceTF.setText(String.valueOf(inHouse.getMachineId()));
            partInHouse.selectedProperty().set(true);
        } else {
            Outsourced outsourced = (Outsourced) part;
            partSourceTF.setText(outsourced.getCompanyName());
            partOutsourced.selectedProperty().set(true);
        }
        
    }
    
    private void throwError(String title, String header, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
    
    @FXML
    public void modifyPartSave(){
        try{
            String tfSource;
            int tfID = Integer.valueOf(partIDTF.getText());
            String tfName = partNameTF.getText();
            double tfPriceCost = Double.valueOf(partPriceCostTF.getText());
            int tfInv = Integer.valueOf(partInvTF.getText());
            int tfMin = Integer.valueOf(partMinTF.getText());
            int tfMax = Integer.valueOf(partMaxTF.getText());
            if(partInHouse.isSelected()){
                tfSource = String.valueOf(partSourceTF.getText());
            } else {
                tfSource = partSourceTF.getText();
            }

            String errorTitle;
            String errorHeader;
            String errorText;
       
        
            if(tfMax < tfMin) {
                errorTitle = "Min/Max Error";
                errorHeader = "Check Min/Max Values";
                errorText = "The maximum value cannot be lower than the minimun value. Please correct to save.";
                throwError(errorTitle, errorHeader, errorText);
            } else if(tfName.equals("")) {
                errorTitle = "Part Name Error";
                errorHeader = "Part Name blank";
                errorText = "The part name field needs to be filled in. Please correct to save.";
                throwError(errorTitle, errorHeader, errorText);
            } else if((tfInv > tfMax)||(tfInv < tfMin)){
                errorTitle = "Inventory Error";
                errorHeader = "Check Inventory Count";
                errorText = "The Inventory amount is not within the maximum and minimum amounts.  Please correct to save.";
                throwError(errorTitle, errorHeader, errorText);
            } else if(tfSource.equals("")){
                if(partInHouse.isSelected()){
                    errorTitle = "Machine ID Error";
                    errorHeader = "Machine ID blank";
                    errorText = "The machine ID field needs to be filled in. Please correct to save.";
                    throwError(errorTitle, errorHeader, errorText);
                } else {
                    errorTitle = "Company Name Error";
                    errorHeader = "Company Name blank";
                    errorText = "The company name field needs to be filled in. Please correct to save.";
                    throwError(errorTitle, errorHeader, errorText);
                }
            } else {
                if(partInHouse.isSelected()){
                    InHouse inHousePart = new InHouse(tfID, tfName, tfPriceCost, tfInv, tfMin, tfMax, Integer.valueOf(tfSource));
                    partInHouse.setSelected(true);
                    updatePart(selectedPartIndex, (InHouse) inHousePart);
                } else {
                    Outsourced outsourcedPart = new Outsourced(tfID, tfName, tfPriceCost, tfInv, tfMin, tfMax,tfSource);
                    partOutsourced.setSelected(true);
                    updatePart(selectedPartIndex, (Outsourced) outsourcedPart);
                }
                save.getScene().getWindow().hide();
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
        modifyPartToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle){
                if(new_toggle != null) {
                    RadioButton currentSelection = (RadioButton) modifyPartToggle.getSelectedToggle();
                    if(currentSelection.getId().equals("partOutsourced")){
                        partSource.setText("Company Name");
                    }
                    if(currentSelection.getId().equals("partInHouse")){
                        partSource.setText("Machine ID");
                    }
                }
            }
        });
    }
    
   
}
