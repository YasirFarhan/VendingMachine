package com.vendingmachine.vendingmachine.controller;

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
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }


    @Test
    public void testGetAllItems() throws Exception {
/*

        Item item = new Item();
        item.setId(1);
        item.setName("Coke");
        item.setPrice(1.50);
        item.setQuantity(10);

*/

//        JSONObject jsonObject = new JSONObject();

//        jsonObject.put("1", item);


        mockMvc.perform(get("/items").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());

        Mockito.verify(service, Mockito.times(1)).getAllItems();
    }

}
