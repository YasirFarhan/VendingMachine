package com.vendingmachine.vendingmachine.model;

import lombok.Data;

@Data
public class Change {

    private int quarters;
    private int dimes;
    private int nickels;
    private int pennies;

    public int getQuarters() {
        return quarters;
    }

    public void setQuarters(int quarters) {
        this.quarters = quarters;
    }

    public int getDimes() {
        return dimes;
    }

    public void setDimes(int dimes) {
        this.dimes = dimes;
    }

    public int getNickels() {
        return nickels;
    }

    public void setNickels(int nickels) {
        this.nickels = nickels;
    }

    public int getPennies() {
        return pennies;
    }

    public void setPennies(int pennies) {
        this.pennies = pennies;
    }

    public void calculateChange(double amount) {
        this.quarters = (int) (amount * 4d);
        this.nickels=0;
        this.pennies=0;
        this.dimes=0;
    }
}
