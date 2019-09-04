/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482.Model;

import java.util.List;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Product {
    private ListProperty<Part> associatedParts;
    private final IntegerProperty id;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty stock;
    private final IntegerProperty min;
    private final IntegerProperty max;

    public Product(List<Part> parts, int id, String name, double price, int stock, int min, int max){
        ObservableList<Part> list = FXCollections.observableArrayList(parts);
        this.associatedParts = new SimpleListProperty(list);
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
    }
    
    public ObservableList<Part> getAssociatedParts() {
        return this.associatedParts.get();
    }

    public void setAssociatedParts(ObservableList<Part> associatedParts) {
        this.associatedParts.set(associatedParts);
    }
    
    public ListProperty associatedPartsProperty(){
        return associatedParts;
    }

    public int getId() {
        return this.id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }
    
    public IntegerProperty idProperty(){
        return id;
    }

    public String getName() {
        return this.name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }
    
    public StringProperty nameProperty(){
        return name;
    }

    public double getPrice() {
        return this.price.get();
    }

    public void setPrice(double price) {
        this.price.set(price);
    }
    
    public DoubleProperty priceProperty(){
        return price;
    }

    public int getStock() {
        return this.stock.get();
    }

    public void setStock(int stock) {
        this.stock.set(stock);
    }
    
    public IntegerProperty stockProperty(){
        return stock;
    }
    
    public int getMin() {
        return this.min.get();
    }

    public void setMin(int min) {
        this.min.set(min);
    }
    
    public IntegerProperty minProperty(){
        return min;
    }

    public int getMax() {
        return this.max.get();
    }

    public void setMax(int max) {
        this.max.set(max);
    }
    
    public IntegerProperty maxProperty(){
        return max;
    }
    
    public void addAssociatedPart(Part part){
        
    }
    
    public void deleteAssociatedPart(Part associatedPart){
        
    }
    
//    public ObservableList<Part> getAllAssociatedParts(){
//        return partData;
//    }
}
