package c482.View;

import c482.Model.InHouse;
import static c482.Model.Inventory.addPart;
import static c482.Model.Inventory.getAllParts;
import c482.Model.Outsourced;
import java.net.URL;
import java.util.Arrays;
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

public class AddPartController implements Initializable {
    
    private boolean saveClicked = false;
    
    @FXML
    private Button cancel;
    
    @FXML
    private Button save;
    
    @FXML
    private Label partSource;
    
    @FXML
    private ToggleGroup addPartToggle;
    
    @FXML
    private RadioButton partInHouse;
    
    //@FXML
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
   
    InHouse inhouse;
    Outsourced outsourced;
    
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
    public void addPartCancel(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Confirmation");
        alert.setHeaderText("Cancel save?");
        alert.setContentText("Are you sure you want to cancel this information?");
        Optional<ButtonType> choice = alert.showAndWait();
        if(choice.get() == ButtonType.OK){
            cancel.getScene().getWindow().hide();
        }
    }
    
    public int getRecordId(){
        int nextID = 0;
        int listSize = getAllParts().size();
        int[] tempList = new int[listSize];
        for(int i=0; i<listSize; i++){
            int num = getAllParts().get(i).getId();
            tempList[i] = num;
        }
        Arrays.sort(tempList);
        for (int i = 0; i < listSize; i++) {
            if (tempList[i]-1 != i) {
                System.out.println(tempList[i]-1 + " IF " + i);
                nextID = tempList[i]-1;
                break;
            }
            nextID = tempList[i]+1;
            System.out.println(nextID);
        }
        return nextID;
    }
        
    
    private void throwError(String title, String header, String text){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }
    
    @FXML
    public void addPartSave(){
        try{
            String tfSource;
            String tfName = partNameTF.getText();
            double tfPriceCost = Double.valueOf(partPriceCostTF.getText());
            int tfInv = Integer.valueOf(partInvTF.getText());
            int tfMin = Integer.valueOf(partMinTF.getText());
            int tfMax = Integer.valueOf(partMaxTF.getText());
            if(partInHouse.isSelected()){
                tfSource = partSourceTF.getText();
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
                if(addPartToggle.getSelectedToggle().equals(partInHouse)){
                    InHouse inHousePart = new InHouse(getRecordId(), tfName, tfPriceCost, tfInv, tfMin, tfMax, Integer.valueOf(tfSource));
                    addPart(inHousePart);
                } else {
                    Outsourced outsourcedPart = new Outsourced(getRecordId(), tfName, tfPriceCost, tfInv, tfMin, tfMax, tfSource);
                    addPart(outsourcedPart);
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
        addPartToggle.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            @Override
            public void changed(ObservableValue<? extends Toggle> ov, Toggle old_toggle, Toggle new_toggle){
                if(new_toggle != null) {
                    RadioButton currentSelection = (RadioButton) addPartToggle.getSelectedToggle();
                    if(currentSelection.getId().equals("partOutsourced")){
                        partSource.setText("Company Name");
                    }
                    if(currentSelection.getId().equals("partInHouse")){
                        partSource.setText("Machine ID");
                    }
                }
            }
        });
        
        partInHouse.setSelected(true);
        partIDTF.setText(String.valueOf(getRecordId()));
    }
    
   
}
