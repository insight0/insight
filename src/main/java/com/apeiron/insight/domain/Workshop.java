package com.apeiron.insight.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "workshop")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "workshop")
public class Workshop {

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("presenter")
    private String presenter;

    @NotNull
    @Field("location")
    private String location;

    @NotNull
    @Field("concerned")
    private String concerned;

    @NotNull
    @Field("date")
    private String date;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPresenter() {
        return presenter;
    }

    public void setPresenter(String presenter) {
        this.presenter = presenter;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getConcerned() {
        return concerned;
    }

    public void setConcerned(String concerned) {
        this.concerned = concerned;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
