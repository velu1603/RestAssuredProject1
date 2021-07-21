package com.rest.pojo.collection;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import sun.font.TrueTypeFont;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Info {
    private String name;
    private String description;
    private String schema;

    public Info(){} // default constructor

    public Info(String name, String description, String schema){
        this.name = name;
        this.description = description;
        this.schema = schema;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }


}