package com.vendingmachine.vendingmachine.controller;

import com.vendingmachine.vendingmachine.exceptions.InsufficientFundsException;
import com.vendingmachine.vendingmachine.exceptions.ItemNotFoundException;
import com.vendingmachine.vendingmachine.model.Change;
import com.vendingmachine.vendingmachine.model.Item;
import com.vendingmachine.vendingmachine.serviceLayer.VendingMachineServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class Controller {
    @Autowired
    VendingMachineServiceLayer serviceLayer;


    @GetMapping(path = "/items")
    public List<Item> getItems() {
        List<Item> item = serviceLayer.getAllItems();
        return item;
    }

    @GetMapping(path = "/money/{amount}/item/{selectedItem}")
    public Change purchaseItem(@PathVariable float amount, @PathVariable Integer selectedItem) throws InsufficientFundsException, ItemNotFoundException {
        return  serviceLayer.purchaseItem(amount, selectedItem);
    }
}
