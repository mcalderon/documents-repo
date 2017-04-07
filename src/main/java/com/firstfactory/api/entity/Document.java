package com.firstfactory.api.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Document {

    private String type;
    private String name;
    private String notes;
}
