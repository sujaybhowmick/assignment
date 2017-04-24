package com.sentifi.assignment.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

import java.util.Date;
import java.util.List;

/**
 * Created by Sujay on 4/7/17.
 */
@JsonRootName(value = "dataset")
public class DataSet extends Entity {

    private final String datasetCode;

    private final String databaseCode;

    private final String name;

    private final String description;

    private final Date refreshedAt;

    private final Date newestAvailableDate;

    private final Date oldestAvailableDate;

    private final Frequency frequency;

    private final Type type;

    private final boolean premium;

    private final String limit;

    private final String transform;

    private final String columnIndex;

    private final String collapse;

    private final String order;

    private final long databaseId;

    private final Date startDate;

    private final Date endDate;

    private final List<String> columnNames;

    private final List<Object> data;

    @JsonCreator
    public DataSet(@JsonProperty("id") long id, @JsonProperty("dataset_code") String datasetCode,
                   @JsonProperty("database_code") String databaseCode, @JsonProperty("name") String name,
                   @JsonProperty("description") String description, @JsonProperty("refreshed_at") Date refreshedAt,
                   @JsonProperty("newest_available_date") Date newestAvailableDate, @JsonProperty("oldest_available_date") Date oldestAvailableDate,
                   @JsonProperty("frequency") Frequency frequency, @JsonProperty("type") Type type,
                   @JsonProperty("premium") boolean premium, @JsonProperty("limit") String limit,
                   @JsonProperty("transform") String transform, @JsonProperty("column_index") String columnIndex,
                   @JsonProperty("collapse") String collapse, @JsonProperty("order") String order,
                   @JsonProperty("database_id") long databaseId,
                   @JsonProperty("start_date") Date startDate, @JsonProperty("end_date") Date endDate,
                   @JsonProperty("column_names") List<String> columnNames,
                   @JsonProperty("data") List<Object> data) {

        this.datasetCode = datasetCode;
        this.databaseCode = databaseCode;
        this.name = name;
        this.description = description;
        this.refreshedAt = refreshedAt;
        this.newestAvailableDate = newestAvailableDate;
        this.oldestAvailableDate = oldestAvailableDate;
        this.frequency = frequency;
        this.type = type;
        this.premium = premium;
        this.limit = limit;
        this.transform = transform;
        this.collapse = collapse;
        this.order = order;
        this.databaseId = databaseId;
        this.columnIndex = columnIndex;
        this.startDate = startDate;
        this.endDate = endDate;
        this.columnNames = columnNames;
        this.data = data;

    }


    public String getDatasetCode() {
        return datasetCode;
    }

    public String getDatabaseCode() {
        return databaseCode;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Date getRefreshedAt() {
        return refreshedAt;
    }

    public Date getNewestAvailableDate() {
        return newestAvailableDate;
    }

    public Date getOldestAvailableDate() {
        return oldestAvailableDate;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public Type getType() {
        return type;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public List<String> getColumnNames() {
        return columnNames;
    }

    public List<Object> getData() {
        return data;
    }

    public enum Frequency {
        @JsonProperty("daily")
        Daily("daily");
        String frequency;

        Frequency(String frequency){
            this.frequency = frequency;
        }
    }

    public enum Type {
        @JsonProperty("Time Series")
        TimeSeries("Time Series");
        String type;

        Type(String type){
            this.type = type;
        }
    }

}
