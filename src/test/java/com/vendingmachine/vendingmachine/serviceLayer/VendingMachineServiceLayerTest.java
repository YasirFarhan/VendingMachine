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

@RunWith(SpringRunner.class)
public class VendingMachineServiceLayerTest {
    @InjectMocks
    VendingMachineServiceLayer service;

    @Mock
    ItemsDAO itemsDAO;

    @Mock
    Change changeMock;


    @Test
    public void testPurcahseItemCallFindMethodFromDAOAndSaveMethodWithReducedInventory() {
        Item item = getOneItem();
        Mockito.doReturn(convertItemToOptional(item)).when(itemsDAO).findById(5);
        service.purchaseItem(5, 5);
        item.setQuantity(item.getQuantity() - 1);
        Mockito.verify(itemsDAO, Mockito.times(1)).findById(5);
        Mockito.verify(itemsDAO, Mockito.times(1)).save(item);
    }


    @Test
    public void testFindAllItems() {
        Mockito.doReturn(listOfItems()).when(itemsDAO).findAll();
        List itemsList = service.getAllItems();
        Assert.assertEquals(itemsList.size(), 2);
        Mockito.verify(itemsDAO, Mockito.times(1)).findAll();
    }

    private List listOfItems() {
        List listOfITems = new ArrayList<Item>();
        Item item1 = getOneItem();
        listOfITems.add(item1);
        Item item2 = getOneItem();
        listOfITems.add(item2);
        return listOfITems;
    }

    private Item getOneItem() {
        Item item1 = new Item();
        item1.setId(5);
        item1.setName("Coke");
        item1.setQuantity(10);
        item1.setPrice(2d);
        //float
        item1.setPriceFloat(2.00f);
        return item1;
    }

    private Optional<Item> convertItemToOptional(Item item) {
        Optional<Item> optionalItem = Optional.of(new Item());
        optionalItem.get().setId(item.getId());
        optionalItem.get().setName(item.getName());
        optionalItem.get().setPrice(item.getPrice());
        optionalItem.get().setQuantity(item.getQuantity());
        // float
        optionalItem.get().setPriceFloat(item.getPriceFloat());
        return optionalItem;
    }


    /*  @Test
      public void testChangeInQuarters() {
          Item item = getOneItem();
          Mockito.doReturn(convertItemToOptional(item)).when(itemsDAO).findById(5);
          Change change1 = service.purchaseItem(5d, 5);
          Assert.assertEquals(change1.getQuarters(), 12);

          Change change2 = service.purchaseItem(4.5d, 5);
          Assert.assertEquals(change2.getQuarters(), 10);

          Change change3 = service.purchaseItem(4.53d, 5);
          Assert.assertEquals(change3.getQuarters(), 10);

          Change change4 = service.purchaseItem(4.70d, 5);
          Assert.assertEquals(change4.getQuarters(), 10);

          Change change5 = service.purchaseItem(4.99d, 5);
          Assert.assertEquals(change5.getQuarters(), 11);
      }*/
// float senarios
    @Test
    public void testChangeInQuarters() {
        Item item = getOneItem();
        float[] amount = {5f, 4.5f, 5.53f, 4.7f, 4.99f};
        int[] changeInQuarters = {12, 10, 14, 10, 11};

        for (int i = 0; i < amount.length; i++) {
            Mockito.doReturn(convertItemToOptional(item)).when(itemsDAO).findById(5);
            System.out.println("amount: " + amount[i] + " qtrs: " + changeInQuarters[i]);

            Change change1 = service.purchaseItemFloat(amount[i], 5);
            Assert.assertEquals(change1.getQuarters(), changeInQuarters[i]);
        }


    }

    @Test
    public void testChangeInDime() {
        Item item = getOneItem();
        float[] amount = {5f, 4.5f, 5.60f, 4.80f, 4.99f};
        int[] changeInDimes = {0, 0, 1, 0, 2};

        for (int i = 0; i < amount.length; i++) {
            Mockito.doReturn(convertItemToOptional(item)).when(itemsDAO).findById(5);
            System.out.println("amount: " + amount[i] + " dimes: " + changeInDimes[i]);

            Change change1 = service.purchaseItemFloat(amount[i], 5);
            Assert.assertEquals(change1.getDimes(), changeInDimes[i]);
        }

    }


    @Test
    public void testChangeInNickels() {
        Item item = getOneItem();
        float[] amount = {5.00f, 5.05f, 4.55f, 5.65f, 4.80f};
        int[] changeInNickels = {0, 1, 1, 1, 1};

        Mockito.doReturn(convertItemToOptional(item)).when(itemsDAO).findById(5);

        for (int i = 0; i < amount.length; i++) {
            System.out.println("amount: " + amount[i] + " Nickels: " + changeInNickels[i]);

            Change change1 = service.purchaseItemFloat(amount[i], 5);
            Assert.assertEquals(change1.getNickels(), changeInNickels[i]);
        }
    }


    @Test
    public void testChangeInPennies() {
        Item item = getOneItem();
        float[] amount = {5.00f, 5.06f, 4.53f, 5.64f, 4.82f};
        int[] changeInPenies = {0, 1, 3, 4, 2};
        Mockito.doReturn(convertItemToOptional(item)).when(itemsDAO).findById(5);

        for (int i = 0; i < amount.length; i++) {
            System.out.println("amount: " + amount[i] + " Pennies: " + changeInPenies[i]);

            Change change1 = service.purchaseItemFloat(amount[i], 5);
            Assert.assertEquals(change1.getPennies(), changeInPenies[i]);
        }
    }

    @Test
    public void testCompleteChangeFor5DollarsAnd66CentsInput() {
        Item item = getOneItem();

        Mockito.doReturn(convertItemToOptional(item)).when(itemsDAO).findById(5);

        Change change1 = service.purchaseItemFloat(5.66f, 5);
        Assert.assertEquals(change1.getQuarters(), 14);
        Assert.assertEquals(change1.getDimes(), 1);
        Assert.assertEquals(change1.getNickels(), 1);
        Assert.assertEquals(change1.getPennies(), 1);
    }

    @Test
    public void testNoChangeIsGivenWhenTheAmountIsEqualToThePrice() {
        Item item = getOneItem();

        Mockito.doReturn(convertItemToOptional(item)).when(itemsDAO).findById(5);

        Change change1 = service.purchaseItemFloat(2f, 5);
        Assert.assertEquals(0, change1.getQuarters());
        Assert.assertEquals(0, change1.getDimes());
        Assert.assertEquals(0, change1.getNickels());
        Assert.assertEquals(0, change1.getPennies());
    }




}
