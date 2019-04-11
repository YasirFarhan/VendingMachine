package com.vendingmachine.vendingmachine.serviceLayer;

import com.vendingmachine.vendingmachine.model.Change;
import com.vendingmachine.vendingmachine.model.Item;
import com.vendingmachine.vendingmachine.persistance.ItemsDAO;
import jdk.nashorn.internal.runtime.options.Option;
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

import static org.apache.cassandra.utils.concurrent.WaitQueue.any;

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
        item1.setPrice(2);
        item1.setQuantity(10);
        return item1;
    }

    private Optional<Item> convertItemToOptional(Item item) {
        Optional<Item> optionalItem = Optional.of(new Item());
        optionalItem.get().setId(item.getId());
        optionalItem.get().setName(item.getName());
        optionalItem.get().setPrice(item.getPrice());
        optionalItem.get().setQuantity(item.getQuantity());
        return optionalItem;
    }


    @Test
    public void testPurcahseItemReturnsCorrectChangeInQuartersOnly() {
        Item item = getOneItem();
        Mockito.doReturn(convertItemToOptional(item)).when(itemsDAO).findById(5);
        service.purchaseItem(5, 5);
        Change change = new Change();
        change.calculateChange(5.00d);
        double amount = 3d;
        Mockito.verify(changeMock, Mockito.times(1)).calculateChange(amount);

        Assert.assertEquals((5 * 4), change.getQuarters());
    }



}
