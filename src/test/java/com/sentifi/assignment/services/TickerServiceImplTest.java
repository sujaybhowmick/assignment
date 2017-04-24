package com.sentifi.assignment.services;

import com.sentifi.assignment.exceptions.ResourceNotFoundException;
import com.sentifi.assignment.repository.JsonFileRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Created by Sujay on 4/17/17.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TickerServiceImplTest {

    @Autowired
    JsonFileRepository repository;

    TickerService tickerService;

    @Before
    public void setUp() {
        this.tickerService = new TickerServiceImpl(this.repository);
    }

    @After
    public void tearDown() {

    }

    @Test
    public void closePrice() throws Exception {
        String symbol1 = "GE";

        String jsonResponse = this.tickerService.closePrice(symbol1, "2017-04-05", "2017-04-03");
        assertThat(jsonResponse)
                .contains("GE")
                .contains("2017-04-05")
                .contains("2017-04-04")
                .contains("2017-04-03");

        String symbol2 = "ABC123";
        assertThatThrownBy(() -> this.tickerService.closePrice(symbol2, "2017-04-05", "2017-04-03"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("com.sentifi.assignment.exceptions.ResourceNotFoundException: ABC123 Symbol not found");


        assertThatThrownBy(() -> this.tickerService.closePrice(symbol1, "2017-24-05", "2017-04-03"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("java.time.format.DateTimeParseException: Text '2017-24-05' could not be parsed: Invalid value for MonthOfYear (valid values 1 - 12): 24");
    }

    @Test
    public void dma200() throws Exception {
        String symbol = "GE";
        String jsonResponse = this.tickerService.dma200(symbol, "2017-04-05");
        assertThat(jsonResponse)
                .contains("GE")
                .containsPattern("[0-9]+\\.[0-9]{4}");

        String symbol2 = "ABC123";
        assertThatThrownBy(() -> this.tickerService.dma200(symbol2, "2017-04-05"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ABC123 Symbol not found");
    }

    @Test
    public void dma200Bulk() throws Exception {
        String[] symbols = new String[]{"GE", "AAPL"};
        String jsonResponse = this.tickerService.dma200Bulk(symbols, "2017-04-05");
        assertThat(jsonResponse)
                .contains("GE")
                .containsPattern("[0-9]+\\.[0-9]{4}")
                .contains("30.2956");

        assertThat(jsonResponse)
                .contains("AAPL")
                .containsPattern("[0-9]+\\.[0-9]{4}")
                .contains("128.0859");

        final String[] symbols2 = new String[]{"GE", "AAPL", "ABC123"};
        assertThatThrownBy(() -> this.tickerService.dma200Bulk(symbols2, "2017-04-05"))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("ABC123 Symbol not found");


    }

}