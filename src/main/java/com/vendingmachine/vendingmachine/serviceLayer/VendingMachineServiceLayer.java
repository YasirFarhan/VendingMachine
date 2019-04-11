package com.vendingmachine.vendingmachine.serviceLayer;

import com.vendingmachine.vendingmachine.model.Change;
import com.vendingmachine.vendingmachine.model.Item;
import com.vendingmachine.vendingmachine.persistance.ItemsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendingMachineServiceLayer {
    @Autowired
    ItemsDAO dao;


    public List<Item> getAllItems() {
        List item = dao.findAll();
        return item;
    }

    public Change purchaseItem(double amount, Integer selectedItem) {
        Optional<Item> optionalItem = dao.findById(selectedItem);
        Item item = convertOptionalToItem(optionalItem);

        item.setQuantity(item.getQuantity() - 1);
        dao.save(item);


        return calcualteChange(amount - item.getPrice());

    }


    private Item convertOptionalToItem(Optional<Item> optional) {
        Item item = new Item();
        item.setId(optional.get().getId());
        item.setName(optional.get().getName());
        item.setPrice(optional.get().getPrice());
        item.setQuantity(optional.get().getQuantity());
        return item;
    }

    private Change calcualteChange(double amount) {
        Change change = new Change();
        change.calculateChange(amount);
        return change;
    }

}
