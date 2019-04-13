package com.vendingmachine.vendingmachine.serviceLayer;

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

import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class VendingMachineServiceLayerTest {
    @InjectMocks
    VendingMachineServiceLayer service;

    @Mock
    ItemsDAO itemsDAO;

    @Test
    public void testFindAllItemsFiltersItemWithNoInventory() {
        Mockito.doReturn(listOfItems()).when(itemsDAO).findAll();
        List itemsList = service.getAllItems();
        Assert.assertEquals(itemsList.size(), 2);
        Mockito.verify(itemsDAO, Mockito.times(1)).findAll();
    }

    @Test
    public void testPurchaseItemCallFindMethodFromDAOAndSaveMethodWithReducedInventoryx() {
        Item item = getOneItem();
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
        service.purchaseItem(5, 5);
        item.setQuantity(item.getQuantity() - 1);
        Mockito.verify(itemsDAO, Mockito.times(1)).findById(5);
        Mockito.verify(itemsDAO, Mockito.times(1)).save(item);
    }


    @Test
    public void testPurchaseItemCallFindMethodFromDAOAndSaveMethodWithReducedInventory() {
        Item item = getOneItem();
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
        service.purchaseItem(5, 5);
        item.setQuantity(item.getQuantity() - 1);
        Mockito.verify(itemsDAO, Mockito.times(1)).findById(5);
        Mockito.verify(itemsDAO, Mockito.times(1)).save(item);
    }

    @Test
    public void testChangeInQuarters() {
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
    public void testChangeInDime() {
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
    public void testChangeInNickels() {
        Item item = getOneItem();
        float[] amount = {5.00f, 5.05f, 4.55f, 5.65f, 4.80f};
        int[] changeInNickels = {0, 1, 1, 1, 1};
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
        for (int i = 0; i < amount.length; i++) {
            Change change = service.purchaseItem(amount[i], 5);
            Assert.assertEquals(changeInNickels[i], change.getNickels());
        }
    }


    @Test
    public void testChangeInPennies() {
        Item item = getOneItem();
        float[] amount = {5.00f, 5.06f, 4.53f, 5.64f, 4.82f};
        int[] changeInPennies = {0, 1, 3, 4, 2};
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
        for (int i = 0; i < amount.length; i++) {
            Change change = service.purchaseItem(amount[i], 5);
            Assert.assertEquals(changeInPennies[i], change.getPennies());
        }
    }

    @Test
    public void testCompleteChangeFor5DollarsAnd66CentsInput() {
        Item item = getOneItem();
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
        Change change = service.purchaseItem(5.66f, 5);
        Assert.assertEquals(14, change.getQuarters());
        Assert.assertEquals(1, change.getDimes());
        Assert.assertEquals(1, change.getNickels());
        Assert.assertEquals(1, change.getPennies());
    }

    @Test
    public void testNoChangeIsGivenWhenTheAmountIsEqualToThePrice() {
        Item item = getOneItem();
        Mockito.doReturn(Optional.of(item)).when(itemsDAO).findById(5);
        Change change = service.purchaseItem(2f, 5);
        Assert.assertEquals(0, change.getQuarters());
        Assert.assertEquals(0, change.getDimes());
        Assert.assertEquals(0, change.getNickels());
        Assert.assertEquals(0, change.getPennies());
    }

    private List listOfItems() {
        List listOfItems = new ArrayList<Item>();
        listOfItems.add(getOneItem());
        listOfItems.add(getOneItem());
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

    private Item getItemWithNoInventory() {
        Item item1 = new Item();
        item1.setId(6);
        item1.setName("Pepsi");
        item1.setQuantity(0);
        item1.setPrice(2.00f);
        return item1;
    }

}
