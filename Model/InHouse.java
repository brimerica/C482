/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package c482.Model;

import javafx.beans.property.*;

/**
 *
 * @author brime
 */
public class InHouse extends Part{
    private SimpleIntegerProperty machineId = new SimpleIntegerProperty();

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        setMachineId(machineId);
    }

    public int getMachineId() {
        return this.machineId.get();
    }

    public void setMachineId(int machineId) {
        this.machineId.set(machineId);
    }
    
    public IntegerProperty machineIdProperty(){
        return machineId;
    }
}
