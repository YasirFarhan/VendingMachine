package com.vendingmachine.vendingmachine.model;

import com.vendingmachine.vendingmachine.constants.ItemsTable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import java.util.Optional;

@Data
@Builder
@AllArgsConstructor
@Table(ItemsTable.TABLE_NAME)
public class Item {
    public Item() {
    }

    public Item(Optional<Item> optional) {
        this.name = optional.get().getName();
        this.price = optional.get().getPrice();
        this.quantity = optional.get().getQuantity();
        this.id = optional.get().getId();
    }
    
    @PrimaryKey
    @Column(ItemsTable.Columns.ID)
    int id;

    @Column(ItemsTable.Columns.NAME)
    private String name;

    @Column(ItemsTable.Columns.PRICE)
    private float price;

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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }


    public float getPrice() {
        return price;
    }

    public void setPrice(float priceFloat) {
        this.price = priceFloat;
    }

}
