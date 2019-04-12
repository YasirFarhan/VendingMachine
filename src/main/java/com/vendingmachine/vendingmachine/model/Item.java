package com.vendingmachine.vendingmachine.model;

import com.vendingmachine.vendingmachine.constants.ItemsTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(ItemsTable.TABLE_NAME)
public class Item {

    @PrimaryKey
    @Column(ItemsTable.Columns.ID)
    int id;

    @Column(ItemsTable.Columns.NAME)
    private String name;


    @Column(ItemsTable.Columns.PRICE)
    private double price;


    @Column(ItemsTable.Columns.QUANTITY)
    private int quantity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    //////////

    @Column(ItemsTable.Columns.PRICE)
    private Float priceFloat;

    public Float getPriceFloat() {
        return priceFloat;
    }

    public void setPriceFloat(Float priceFloat) {
        this.priceFloat = priceFloat;
    }
}
