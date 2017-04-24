package com.sentifi.assignment.repository;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sentifi.assignment.domain.DataSet;
import com.sentifi.assignment.domain.Ticker;
import com.sentifi.assignment.exceptions.DataAccessException;
import com.sentifi.assignment.exceptions.ResourceNotFoundException;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;

/**
 * Created by Sujay on 4/7/17.
 */

@org.springframework.stereotype.Repository(value = "JsonFileRepository")
public class JsonFileRepository extends AbstractRepository {

    public JsonFileRepository() {
    }


    private DataSet load(String symbol) {
        if(symbol == null){
            throw new DataAccessException("Missing Json file Symbol");
        }

        final String filePath = symbol.toUpperCase() + ".json";
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.UNWRAP_ROOT_VALUE, true)
                    .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));

            return mapper.readValue(readJsonFile(filePath), DataSet.class);
        } catch (DataAccessException | IOException e) {
            throw new ResourceNotFoundException(String.format("%s Symbol not found", symbol), e);
        }
    }


    protected File readJsonFile(String jsonFilePath) {
        try {
            ClassLoader classLoader = getClass().getClassLoader();
            URL url = classLoader.getResource(jsonFilePath);
            if(url == null) {
                throw new DataAccessException("File not found");
            }
            return new File(url.toURI());
        } catch (URISyntaxException e) {
            throw new DataAccessException(e);
        }
    }


    @Override
    public Ticker findBySymbol(String symbol) {
        DataSet dataSet = load(symbol);
        return getTicketData(symbol, dataSet);
    }


}
