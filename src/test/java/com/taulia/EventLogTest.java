package com.taulia;

/**
 *
 * @author ahmed-explorer
 */

import java.io.IOException;
import java.time.LocalDateTime;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import com.taulia.service.EventLogService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = {EventLogTest.class})
public class EventLogTest {

    EventLogService eventlogService = new EventLogService();
    
    @Test
    public void testConvertToDate() throws IOException {
       LocalDateTime date = eventlogService.convertToDate("2018-07-23T11:09:34Z");
        assertEquals(2018, date.getYear(),  "testConvertToDate PASS"); 
     
    }

    @Test
    public void testReadCsvlogFile() throws IOException { 
       eventlogService.readEvents();
       assertNotEquals(0, eventlogService.eventSortedMap.size(),  
                "testConvertToDate >>    Confirm populate eventSortedMap PASS"); 
    }

    @Test
    public void testWriteCsvEventlog() throws IOException {
        try {
            eventlogService.readEvents();
            eventlogService.writeCsvEventlog();
        } catch (IOException e) {
            fail("Error writing event log CSV report");
        }
       
    }
    
    @Test
    public void testWriteJsonEventlog() throws IOException {
        try {
            eventlogService.readEvents();
            eventlogService.writeJsonEventlog();
        } catch (IOException e) {
            fail("Error writing event log JSON report for each invoice group");
        }
       
    }
    


}
