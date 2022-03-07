package com.taulia.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;
import com.opencsv.bean.ColumnPositionMappingStrategy;
import com.opencsv.bean.CsvToBean;
import com.taulia.model.Event;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import com.taulia.util.Constants;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 *
 * @author ahmed-explorer
 */
@Service
public class EventLogService {

    
     public  Map<LocalDateTime, Event> eventSortedMap = new TreeMap<>();
     Logger logger = LoggerFactory.getLogger(EventLogService.class);

  
  
    /**
     * Reading event log CSV file and returning list of rows using opencsv.
     *
     * @throws java.io.FileNotFoundException
     */
    public void readEvents() throws FileNotFoundException {
        CsvToBean csv = new CsvToBean();
        String csvFilename = Constants.INPUT_FILE;
        logger.info("Reading events from CSV  file " + csvFilename);
        
        ClassLoader classLoader = getClass().getClassLoader();
        FileReader inputCsvFile = new FileReader(classLoader.getResource(csvFilename).getFile());
        CSVReader csvReader = new CSVReader(inputCsvFile);
        List list = csv.parse(mapCsvColumns(), csvReader);
        mapSortedEvents(list);

    }

    /**
     * Reading event log CSV file and returning list of rows using opencsv.
     */
    public  void mapSortedEvents(List eventList) throws FileNotFoundException {
        for (Object object : eventList) {
            Event event = (Event) object;
            // skipe the header row
            if (!event.getOperation().equals(Constants.HEADER_SKIP_VALUE)) {
                if (event.getTimestamp() != null) {
                    event.setDtTimestamp(convertToDate(event.getTimestamp()));
                    sortEvents(event);
                }
            }

        }
    }

    
    /**
     * Generic sorting to Events by Timestamp.
    */
    public  <K, V> Map<K, V> sortEvents(Event event) {
        eventSortedMap.put(event.getDtTimestamp(), (Event) event);
        return (Map<K, V>) eventSortedMap;
    }

    /**
     * Convert date String to LocalDateTime value.
     * @param timestamp
     * @return 
     */
    public  LocalDateTime convertToDate(String timestamp) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(Constants.dateFormat);
        LocalDateTime eventDate = LocalDateTime.parse(timestamp, formatter);
        return eventDate;
    }

    
    /**
     * Set CSV file column headers to be used for read/write event logs.
     */
    public String[] setFileColumnsHeaders() {
        String[] columns = new String[]{
            Constants.OPERATION_COL_NAME,
            Constants.TIMESTAMP_COL_NAME,
            Constants.TYPE_COL_NAME,
            Constants.ID_COL_NAME,
            Constants.CONTENT_COL_NAME
        };

        return columns;

    }

    
    /**
     * To map CSV columns into position strategy to be used to populate Event
     * POJO.
     */
    public  ColumnPositionMappingStrategy mapCsvColumns() {
        ColumnPositionMappingStrategy strategy = new ColumnPositionMappingStrategy();
        strategy.setType(Event.class);
        strategy.setColumnMapping(setFileColumnsHeaders());
        return strategy;
    }

    
   
    
    /**
     * Writing sorted event logs CSV report.
     * @throws java.io.IOException
     */
    public  void writeCsvEventlog() throws IOException {
        CSVWriter csvWriter = null;
        // write event logs headers
         csvWriter = new CSVWriter(new FileWriter(Constants.OUTPUT_CSV_FILE));
         logger.info("Writing events to CSV  file " + Constants.OUTPUT_CSV_FILE);
            // write event logs headers
            csvWriter.writeNext(setFileColumnsHeaders());
            // write event logs rows
            for (Map.Entry<LocalDateTime, Event> event : eventSortedMap.entrySet()) {
                csvWriter.writeNext(new String[]{
                    event.getValue().getOperation(),
                    event.getValue().getTimestamp(),
                    event.getValue().getType(),
                    event.getValue().getId(),
                    event.getValue().getContent()
                }
                );
            }
        
        csvWriter.close();
    }

    
     public  void writeJsonEventlog() throws IOException {
         groupEventById();
     }
    
    /**
     * Group events sorted map by invoice id.
     * @return 
     * @throws java.io.IOException
     */
    public  Map groupEventById() throws IOException {
       logger.info("Grouping events by invoice id ");
        Map<String, List<Entry<LocalDateTime, Event>>> eventsbyIdMap
                = eventSortedMap.entrySet().stream()
                        .collect(Collectors.groupingBy(map -> map.getValue().getId()));
        
        generateJsonEventById(eventsbyIdMap);

        return eventsbyIdMap;
    }

    
    /**
     * Prepare the grouped map data to generate json reports.
     */
    public  void generateJsonEventById(Map<String, 
            List<Entry<LocalDateTime, Event>>> eventsbyIdMap) throws IOException {

        Map<String, List<Event>> tempEventsbyIdMap = new HashMap<>();
        for (Entry<String, List<Entry<LocalDateTime, Event>>> event : eventsbyIdMap.entrySet()) {
            String jsonStrEventId = new String();
            tempEventsbyIdMap.clear();
            List<Event> eventsList = new ArrayList<>();
            for (Entry groupedEvents : event.getValue()) {
                eventsList.add((Event) groupedEvents.getValue());
            }
            tempEventsbyIdMap.put(event.getKey(), eventsList);
            writeJsonEventlog(event.getKey(), tempEventsbyIdMap);

        }
    }

    
    /**
     * Generate json reports.
     */
    public  void writeJsonEventlog(String grpId, Map tempEventsbyIdMap)
            throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonOutFileName = Constants.OUTPUT_JSON_FILE + 
                grpId + Constants.JSON_FILE_TYPE;
        objectMapper.writeValue(new File(jsonOutFileName), tempEventsbyIdMap);
          logger.info("Writing JSON reports for each invoice  " + jsonOutFileName);
    }
    
}
