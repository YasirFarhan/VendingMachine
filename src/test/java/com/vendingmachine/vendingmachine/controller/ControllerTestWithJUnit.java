package com.vendingmachine.vendingmachine.controller;

import com.vendingmachine.vendingmachine.model.Change;
import com.vendingmachine.vendingmachine.model.Item;
import com.vendingmachine.vendingmachine.serviceLayer.VendingMachineServiceLayer;
import org.json.simple.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class ControllerTestWithJUnit {
    private MockMvc mockMvc;
    @Mock
    VendingMachineServiceLayer service;

    @InjectMocks
    private Controller controller;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void testGetAllItems() throws Exception {
        mockMvc.perform(get("/items").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        Mockito.verify(service, Mockito.times(1)).getAllItems();
    }

    @Test
    public void testPurchaseItem() throws Exception {
        mockMvc.perform(get("/money/10/item/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        Mockito.verify(service, Mockito.times(1)).purchaseItem(10f, 5);
    }

    @Test
    public void testNonNullParameters() throws Exception {
        mockMvc.perform(get("/money/10/item/5").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
        Mockito.verify(service, Mockito.times(1)).purchaseItem(10f, 5);
    }

}
