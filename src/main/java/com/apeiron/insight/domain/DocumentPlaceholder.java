package com.apeiron.insight.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * A DocumentPlaceholder.
 */
public class DocumentPlaceholder implements Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    /**
     * TODO: private List<DocumentHistory> versions; TODO: private List<ChangeLog> changeLogs;
     */
    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("version")
    private String version;

    @NotNull
    @Field("url")
    private String url;

    @NotNull
    @Field("type")
    private String type;

    @NotNull
    @Field("size")
    private String size;


    public String getName() {
        return name;
    }

    public DocumentPlaceholder name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public DocumentPlaceholder description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public DocumentPlaceholder version(String version) {
        this.version = version;
        return this;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public DocumentPlaceholder url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public DocumentPlaceholder type(String type) {
        this.type = type;
        return this;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSize() {
        return size;
    }

    public DocumentPlaceholder size(String size) {
        this.size = size;
        return this;
    }

    public void setSize(String size) {
        this.size = size;
    }


    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DocumentPlaceholder{" +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", version='" + getVersion() + "'" +
            ", url='" + getUrl() + "'" +
            ", type='" + getType() + "'" +
            ", size='" + getSize() + "'" +
            "}";
    }
}
