package com.sentifi.assignment.controllers;

import com.sentifi.assignment.services.TickerService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Sujay on 4/23/17.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(controllers = TicketSymbolController.class)
public class TicketSymbolControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TickerService tickerService;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void closePrice() throws Exception {

        mockMvc.perform(get("/v2/{symbol}/closePrice", "GE")
                .param("startDate", "2017-04-05")
                .param("endDate", "2017-04-04"))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    public void dma200() throws Exception {
        mockMvc.perform(get("/v2/{symbol}/dma200", "GE")
                .param("startDate", "2017-04-05"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void dma200bulk() throws Exception {
        mockMvc.perform(get("/v2/{symbols}/dma200bulk", "GE,AAPL")
                .param("startDate", "2017-04-05"))
                .andDo(print())
                .andExpect(status().isOk());
    }

}