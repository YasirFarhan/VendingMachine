package com.vendingmachine.vendingmachine.serviceLayer;

import com.vendingmachine.vendingmachine.model.Change;
import com.vendingmachine.vendingmachine.model.Item;
import com.vendingmachine.vendingmachine.persistance.ItemsDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class VendingMachineServiceLayer {
    @Autowired
    ItemsDAO dao;
    double qtr = 4.00;

    public List<Item> getAllItems() {
        return dao.findAll().stream().filter(c -> c.getQuantity() > 0).collect(Collectors.toList());
    }

    public Change purchaseItem(float amount, Integer selectedItem) {
        Optional<Item> optionalItem = dao.findById(selectedItem);
        Item item = convertOptionalToItem(optionalItem);

        item.setQuantity(item.getQuantity() - 1);
        dao.save(item);
        amount = amount - item.getPrice();
        return calcualteChangeFloat(amount);

    }

    private Item convertOptionalToItem(Optional<Item> optional) {
        Item item = new Item();
        item.setId(optional.get().getId());
        item.setName(optional.get().getName());
        item.setPrice(optional.get().getPrice());
        item.setQuantity(optional.get().getQuantity());

        return item;
    }


    private Change calcualteChangeFloat(float amount) {
        Change change = new Change();
        int amountInPennies = (int) (amount * 100f);
        int quarters = amountInPennies / 25;
        change.setQuarters(quarters);

        int remainingPennies = amountInPennies % 25;
        if (remainingPennies / 10 > 0) {
            change.setDimes(remainingPennies / 10);
        }

        remainingPennies = remainingPennies % 10;

        if (remainingPennies / 5 > 0) {
            change.setNickels(remainingPennies / 5);
        }
        remainingPennies = remainingPennies % 5;

        if (remainingPennies / 1 > 0) {
            change.setPennies(Math.round(remainingPennies));
        }
        return change;
    }
}