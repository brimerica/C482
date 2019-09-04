/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    public static void addPart(Part newPart){
        allParts.add(newPart);
    }
    
    public static void addProduct(Product newProduct){
        allProducts.add(newProduct);
    }
    
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> foundParts = FXCollections.observableArrayList();
        if(partName.length() == 0){
            foundParts = allParts;
        } else {
            for(int i=0; i<allParts.size(); i++){
                if(allParts.get(i).getName().toLowerCase().contains(partName.toLowerCase())){
                    foundParts.add(allParts.get(i));
                }
            }
        }
        return foundParts;
    }
    
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> foundProducts = FXCollections.observableArrayList();
        if(productName.length() == 0){
            foundProducts = allProducts;
        } else {
            for(int i=0; i<allProducts.size(); i++){
                if(allProducts.get(i).getName().toLowerCase().contains(productName.toLowerCase())){
                    foundProducts.add(allProducts.get(i));
                }
            }
        }
        return foundProducts;
    }
    
    public static void updatePart(int index, Part selectedPart){
        if(selectedPart instanceof InHouse){
            allParts.remove(index);
            allParts.add(index, (InHouse) selectedPart);
        }
        if(selectedPart instanceof Outsourced){
            allParts.remove(index);
            allParts.add(index, (Outsourced) selectedPart);
        }
    }
    
    public static void updateProduct(int index, Product selectedProduct){
        allProducts.remove(index);
        allProducts.add(index, selectedProduct);
    }
    
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
    }
    
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    }
    
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }

}

