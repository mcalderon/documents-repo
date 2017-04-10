package com.firstfactory.api.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DocumentList {

    private int count;
    private List<Document> documents;

    public DocumentList(List<Document> documents) {
        this.count = documents.size();
        this.documents = documents;
    }
}
