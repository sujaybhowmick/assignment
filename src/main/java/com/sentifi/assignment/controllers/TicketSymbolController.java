package com.sentifi.assignment.controllers;

import com.sentifi.assignment.services.TickerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Sujay on 4/11/17.
 */
@RestController
@RequestMapping(value = "/v2")
public class TicketSymbolController extends AbstractController {

    @Autowired
    TickerService tickerService;

    @RequestMapping(value = "/{tickerSymbol}/closePrice", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> closePrice(@PathVariable("tickerSymbol") String tickerSymbol,
                                                 @RequestParam("startDate") String startDate,
                                                 @RequestParam("endDate") String endDate) {
        return ResponseEntity.ok(this.tickerService.closePrice(getUpperCaseTicker(tickerSymbol), startDate, endDate));
    }

    @RequestMapping(value = "/{tickerSymbol}/dma200", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> dma200(@PathVariable("tickerSymbol") String tickerSymbol,
                                         @RequestParam("startDate") String startDate) {
        return ResponseEntity.ok(this.tickerService.dma200(getUpperCaseTicker(tickerSymbol), startDate));
    }

    @RequestMapping(value = "/{tickerSymbols}/dma200bulk", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> dma200bulk(@PathVariable("tickerSymbols") String[] tickerSymbols,
                                         @RequestParam("startDate") String startDate) {
        return ResponseEntity.ok(this.tickerService.dma200Bulk(tickerSymbols, startDate));
    }

    public String getUpperCaseTicker(String tickerSymbol){
        return tickerSymbol.toUpperCase();
    }



}
