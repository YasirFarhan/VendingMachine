package com.vendingmachine.vendingmachine.serviceLayer;

import com.vendingmachine.vendingmachine.model.Item;
import com.vendingmachine.vendingmachine.persistance.ItemsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VendingMachineServiceLayer {
    @Autowired
    ItemsDAO dao;


    public List<Item> getAllItems() {
//        return dao.findAll();

        List item = dao.findAll();

        return item;
    }
}
