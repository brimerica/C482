package c482.Model;

import javafx.beans.property.*;

public abstract class Part{
    private final IntegerProperty id;
    private final StringProperty name;
    private final DoubleProperty price;
    private final IntegerProperty stock;
    private final IntegerProperty min;
    private final IntegerProperty max;
   
    public Part(int id, String name, double price, int stock, int min, int max) {       
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.price = new SimpleDoubleProperty(price);
        this.stock = new SimpleIntegerProperty(stock);
        this.min = new SimpleIntegerProperty(min);
        this.max = new SimpleIntegerProperty(max);
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
    
}


