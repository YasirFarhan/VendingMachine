package com.vendingmachine.vendingmachine.serviceLayer;

import com.vendingmachine.vendingmachine.model.Change;
import com.vendingmachine.vendingmachine.model.Item;
import com.vendingmachine.vendingmachine.persistance.ItemsDAO;
import groovyjarjarantlr.StringUtils;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VendingMachineServiceLayer {
    @Autowired
    ItemsDAO dao;
    double qtr = 4.00;

    public List<Item> getAllItems() {
        List item = dao.findAll();
        return item;
    }

    public Change purchaseItem(double amount, Integer selectedItem) {
        Optional<Item> optionalItem = dao.findById(selectedItem);
        Item item = convertOptionalToItem(optionalItem);

        item.setQuantity(item.getQuantity() - 1);
        dao.save(item);
        amount = amount - item.getPrice();
        return calcualteChange(amount);

    }

    private Item convertOptionalToItem(Optional<Item> optional) {
        Item item = new Item();
        item.setId(optional.get().getId());
        item.setName(optional.get().getName());
        item.setPrice(optional.get().getPrice());
        item.setQuantity(optional.get().getQuantity());
        /// float temp
        item.setPriceFloat(optional.get().getPriceFloat());

        return item;
    }

    private Change calcualteChange(double amount) {
        Change change = new Change();

        int amountInPennies = (int) (amount * 100d);

        int quarters = amountInPennies / 25;

        change.setQuarters(quarters);

        return change;
    }

    // float purchase methods
    public Change purchaseItemFloat(float amount, Integer selectedItem) {
        Optional<Item> optionalItem = dao.findById(selectedItem);
        Item item = convertOptionalToItem(optionalItem);

        item.setQuantity(item.getQuantity() - 1);
        dao.save(item);
        amount = amount - item.getPriceFloat();
        return calcualteChangeFloat(amount);
    }

    private Change calcualteChangeFloat(Float amount) {
        Change change = new Change();
        int amountInPennies = (int) (amount * 100f);
        int quarters = amountInPennies / 25;
        change.setQuarters(quarters);
        // Dimes
        int remainingPennies = amountInPennies - quarters * 25;
        if (remainingPennies / 10 > 0) {
            change.setDimes(remainingPennies / 10);
        }
        // Nickels
        if (change.getDimes() > 0) {
            remainingPennies = remainingPennies - change.getDimes() * 10;
        }
        if (remainingPennies / 5 > 0) {
            change.setNickels(remainingPennies / 5);
        }




        return change;
    }
}
