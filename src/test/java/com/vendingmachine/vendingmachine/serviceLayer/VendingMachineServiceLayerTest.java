package com.vendingmachine.vendingmachine.serviceLayer;

import com.vendingmachine.vendingmachine.exceptions.InsufficientFundsException;
import com.vendingmachine.vendingmachine.exceptions.InsufficientInventoryException;
import com.vendingmachine.vendingmachine.exceptions.ItemNotFoundException;
import com.vendingmachine.vendingmachine.model.Change;
import com.vendingmachine.vendingmachine.model.Item;
import com.vendingmachine.vendingmachine.persistance.ItemsDAO;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class VendingMachineServiceLayerTest {
    @InjectMocks
    VendingMachineServiceLayer service;

    @Mock
    ItemsDAO itemsDAO;

    @Test
    public void testFindAllItems() {
        Mockito.doReturn(listOfItems()).when(itemsDAO).findAll();
        List itemsList = service.getAllItems();
        Assert.assertEquals(itemsList.size(), 2);
        Mockito.verify(itemsDAO, Mockito.times(1)).findAll();
    }
    @Test(expected = InsufficientInventoryException.class)
    public void testInsufficientInventoryException() throws InsufficientFundsException, ItemNotFoundException {
//        Item item = getOneItem();
//        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
//        service.purchaseItem(1f, 5);
//        Mockito.verify(itemsDAO, Mockito.times(1)).findById(5);
//        Mockito.verify(itemsDAO, Mockito.times(0)).save(item);
    }

    @Test(expected = InsufficientFundsException.class)
    public void testInsufficientFundsException() throws InsufficientFundsException, ItemNotFoundException {
        Item item = getOneItem();
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
        service.purchaseItem(1f, 5);
        Mockito.verify(itemsDAO, Mockito.times(1)).findById(5);
        Mockito.verify(itemsDAO, Mockito.times(0)).save(item);
    }

    @Test(expected = ItemNotFoundException.class)
    public void testItemNotFoundException() throws InsufficientFundsException, ItemNotFoundException {
        Mockito.doReturn(Optional.ofNullable(null)).when(itemsDAO).findById(5);
        service.purchaseItem(1f, 5);
        Mockito.verify(itemsDAO, Mockito.times(1)).findById(5);
        Mockito.verify(itemsDAO, Mockito.times(0)).save(Mockito.any(Item.class));
    }

    @Test
    public void testPurchaseItemCallFindMethodFromDAOAndSaveMethodWithReducedInventory() throws InsufficientFundsException, ItemNotFoundException {
        Item item = getOneItem();
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
        service.purchaseItem(5, 5);
        item.setQuantity(item.getQuantity() - 1);
        Mockito.verify(itemsDAO, Mockito.times(1)).findById(5);
        Mockito.verify(itemsDAO, Mockito.times(1)).save(item);
    }

    @Test
    public void testChangeInQuarters() throws InsufficientFundsException, ItemNotFoundException {
        Item item = getOneItem();
        float[] amount = {5f, 4.5f, 5.53f, 4.7f, 4.99f};
        int[] changeInQuarters = {12, 10, 14, 10, 11};

        for (int i = 0; i < amount.length; i++) {
            Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
            Change change1 = service.purchaseItem(amount[i], 5);
            Assert.assertEquals(change1.getQuarters(), changeInQuarters[i]);
        }
    }

    @Test
    public void testChangeInDime() throws InsufficientFundsException, ItemNotFoundException {
        Item item = getOneItem();
        float[] amount = {5f, 4.5f, 5.60f, 4.80f, 4.99f};
        int[] changeInDimes = {0, 0, 1, 0, 2};

        for (int i = 0; i < amount.length; i++) {
            Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
            Change change1 = service.purchaseItem(amount[i], 5);
            Assert.assertEquals(change1.getDimes(), changeInDimes[i]);
        }
    }

    @Test
    public void testChangeInNickels() throws InsufficientFundsException, ItemNotFoundException {
        Item item = getOneItem();
        float[] amount = {5.00f, 5.05f, 4.55f, 5.65f, 4.80f};
        int[] changeInNickels = {0, 1, 1, 1, 1};

        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);

        for (int i = 0; i < amount.length; i++) {
            Change change1 = service.purchaseItem(amount[i], 5);
            Assert.assertEquals(change1.getNickels(), changeInNickels[i]);
        }
    }

    @Test
    public void testChangeInPennies() throws InsufficientFundsException, ItemNotFoundException {
        Item item = getOneItem();
        float[] amount = {5.00f, 5.06f, 4.53f, 5.64f, 4.82f};
        int[] changeInPennies = {0, 1, 3, 4, 2};
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);

        for (int i = 0; i < amount.length; i++) {
            Change change1 = service.purchaseItem(amount[i], 5);
            Assert.assertEquals(change1.getPennies(), changeInPennies[i]);
        }
    }

    @Test
    public void testCompleteChangeFor5DollarsAnd66CentsInput() throws InsufficientFundsException, ItemNotFoundException {
        Item item = getOneItem();
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
        Change change1 = service.purchaseItem(5.66f, 5);
        Assert.assertEquals(change1.getQuarters(), 14);
        Assert.assertEquals(change1.getDimes(), 1);
        Assert.assertEquals(change1.getNickels(), 1);
        Assert.assertEquals(change1.getPennies(), 1);
    }

    @Test
    public void testNoChangeIsGivenWhenTheAmountIsEqualToThePrice() throws InsufficientFundsException, ItemNotFoundException {
        Item item = getOneItem();
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
        Change change1 = service.purchaseItem(2f, 5);
        Assert.assertEquals(0, change1.getQuarters());
        Assert.assertEquals(0, change1.getDimes());
        Assert.assertEquals(0, change1.getNickels());
        Assert.assertEquals(0, change1.getPennies());
    }

    private List listOfItems() {
        List listOfItems = new ArrayList<Item>();
        listOfItems.add(getOneItem());
        listOfItems.add(getSecondItem());
        listOfItems.add(getItemWithNoInventory());
        return listOfItems;
    }

    private Item getOneItem() {
        Item item1 = new Item();
        item1.setId(5);
        item1.setName("Coke");
        item1.setQuantity(10);
        item1.setPrice(2.00f);
        return item1;
    }

    private Item getSecondItem() {
        Item item1 = new Item();
        item1.setId(4);
        item1.setName("Orange");
        item1.setQuantity(10);
        item1.setPrice(2.00f);
        return item1;
    }

    private Item getItemWithNoInventory() {
        Item item1 = new Item();
        item1.setId(3);
        item1.setName("Pepsi");
        item1.setQuantity(0);
        item1.setPrice(2.00f);
        return item1;
    }

}
