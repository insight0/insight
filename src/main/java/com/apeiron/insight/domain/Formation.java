package com.apeiron.insight.domain;


import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;



@Document(collection = "formation")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "formation")
public class Formation {

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("title")
    private String title;

    @NotNull
    @Field("Society")
    private String society;

    @Field("concerned")
    private String concerned;

    @NotNull
    @Field("location")
    private String location;

    @Field("description")
    private String description;

    @Field("date")
    private String date;

    @Field("candidate")
    private String candidate;

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

    public String getSociety() {
        return society;
    }

    public void setSociety(String society) {
        this.society = society;
    }

    public String getConcerned() {
        return concerned;
    }

    public void setConcerned(String concerned) {
        this.concerned = concerned;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCandidate() {
        return candidate;
    }

    public void setCandidate(String candidate) {
        this.candidate = candidate;
    }

    @Override
    public String toString() {
        return "Formation{" +
            "id='" + id + '\'' +
            ", title='" + title + '\'' +
            ", society='" + society + '\'' +
            ", concerned='" + concerned + '\'' +
            ", location='" + location + '\'' +
            ", description='" + description + '\'' +
            ", date='" + date + '\'' +
            ", candidate='" + candidate + '\'' +
            '}';
    }
}
