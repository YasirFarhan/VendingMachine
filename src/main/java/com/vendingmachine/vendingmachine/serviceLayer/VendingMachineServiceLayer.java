package com.vendingmachine.vendingmachine.serviceLayer;

import com.vendingmachine.vendingmachine.exceptions.InsufficientFundsException;
import com.vendingmachine.vendingmachine.exceptions.ItemNotFoundException;
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

    public List<Item> getAllItems() {
        return dao.findAll().stream().filter(c -> c.getQuantity() > 0).collect(Collectors.toList());
    }

    public Change purchaseItem(float amount, Integer selectedItem) throws InsufficientFundsException, ItemNotFoundException {
        Optional<Item> optionalItem = dao.findById(selectedItem);
        if (!optionalItem.isPresent()) {
            throw new ItemNotFoundException("Item not found exception");
        }

        Item item = convertOptionalToItem(optionalItem);

        if (item.getPrice() > amount) {
            throw new InsufficientFundsException("Insufficient funds exception");
        }

        item.setQuantity(item.getQuantity() - 1);
        dao.save(item);
        amount = amount - item.getPrice();
        return calculateChangeFloat(amount);

    }

    private Item convertOptionalToItem(Optional<Item> optional) {
        Item item = new Item();
        item.setId(optional.get().getId());
        item.setName(optional.get().getName());
        item.setPrice(optional.get().getPrice());
        item.setQuantity(optional.get().getQuantity());

        return item;
    }


    private Change calculateChangeFloat(float amount) {
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

