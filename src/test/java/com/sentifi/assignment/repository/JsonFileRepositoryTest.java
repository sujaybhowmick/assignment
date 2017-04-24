package com.sentifi.assignment.repository;

import com.sentifi.assignment.domain.Ticker;
import com.sentifi.assignment.exceptions.DataAccessException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Sujay on 4/7/17.
 */
public class JsonFileRepositoryTest {

    protected Repository repository;

    @Before
    public void setUp() throws Exception {
        this.repository = new JsonFileRepository();
    }

    @After
    public void tearDown() throws Exception {
        this.repository = null;
    }

    @Test
    public void findBySymbolGE() throws Exception {
        Ticker ticker = repository.findBySymbol("GE");

    }

    @Test
    public void findBySymbolNull() throws Exception {
        try {
            Ticker ticker = repository.findBySymbol(null);
            fail("Expected Null, returned a Symbol data");
        }catch(DataAccessException e){
            assertTrue(true);
        }

    }



}