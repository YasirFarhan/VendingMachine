package com.vendingmachine.vendingmachine.controller;

import com.vendingmachine.vendingmachine.model.Item;
import com.vendingmachine.vendingmachine.persistance.ItemsDAO;
import com.vendingmachine.vendingmachine.serviceLayer.VendingMachineServiceLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
