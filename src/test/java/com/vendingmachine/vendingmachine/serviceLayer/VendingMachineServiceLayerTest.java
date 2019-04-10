package com.vendingmachine.vendingmachine.serviceLayer;

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

    private List listOfItems() {
        List listOfITems = new ArrayList<Item>();

        Item item1 = new Item();
        item1.setId(1);
        item1.setName("Coke");
        item1.setPrice(1.50);
        item1.setQuantity(10);
        listOfITems.add(item1);
        Item item2 = new Item();
        item2.setId(1);
        item2.setName("Coke");
        item2.setPrice(1.50);
        item2.setQuantity(10);
        listOfITems.add(item2);
        return listOfITems;
    }

}
