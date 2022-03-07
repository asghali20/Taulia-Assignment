package com.taulia.util;

import com.taulia.model.Event;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author ahmed-explorer
 */
public class Constants {
    
    public  static Map<LocalDateTime, Event> eventSortedMap = new TreeMap<>();
    public static final String dateFormat = "yyyy-MM-dd'T'HH:mm:ssz";
    public static final String FILE_PATH = "/Users/ahmed-explorer/Downloads/";
    public static final String FILE_NAME = "Events.csv";
    public static final Long REPORT_TIME_STAMP = System.currentTimeMillis();
    public static final String OUT_CSV_FILE_NAME = "/SortedEvents_"+REPORT_TIME_STAMP+".csv";
    public static final String OUT_JSON_FILE_NAME = "/SortedEvents_"+REPORT_TIME_STAMP;
    public static final String JSON_FILE_TYPE = ".json";
    
    public static final String INPUT_FILE = FILE_NAME;
    public static final String OUTPUT_CSV_FILE = FILE_PATH+OUT_CSV_FILE_NAME; 
    public static final String OUTPUT_JSON_FILE = FILE_PATH+OUT_JSON_FILE_NAME+"_"; 
    public static final String HEADER_SKIP_VALUE = "operation";
    public static final String OPERATION_COL_NAME = "operation";
    public static final String TIMESTAMP_COL_NAME = "timestamp";
    public static final String TYPE_COL_NAME = "type";
    public static final String ID_COL_NAME = "id";
    public static final String CONTENT_COL_NAME = "Content";
}
