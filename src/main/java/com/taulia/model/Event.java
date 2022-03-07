package com.taulia.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author ahmed-explorer
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "Id",
    "operation",
    "timestamp",
    "content",
})
public class Event {

    
     @JsonProperty("operation")
    private String operation;
     
    @JsonProperty("timestamp")
    private String timestamp;
    
    @JsonProperty("type")
    private String type;
    
    @JsonProperty("id")
    private String id;
    
    @JsonProperty("content")
    private String content;

    @JsonProperty("dtTimestamp")
    @JsonIgnore
    private LocalDateTime dtTimestamp;
    
    
    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @JsonIgnore
    public LocalDateTime getDtTimestamp() {
        return dtTimestamp;
    }

    public void setDtTimestamp(LocalDateTime dtTimestamp) {
        this.dtTimestamp = dtTimestamp;
    }



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.operation);
        hash = 97 * hash + Objects.hashCode(this.timestamp);
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.content);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        return true;
    }

    @Override
    public String toString() {
        return "Event{" + "operation=" + operation + ", timestamp=" + timestamp + ", type=" + type + ", id=" + id + ", content=" + content + '}';
    }

}
