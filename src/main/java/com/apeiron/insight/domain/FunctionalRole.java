package com.apeiron.insight.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A FunctionalRole.
 */
@Document(collection = "functional_role")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "functionalrole")
public class FunctionalRole implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("name")
    private String name;

    @NotNull
    @Field("description")
    private String description;

    @NotNull
    @Field("max_holidays")
    private Integer maxHolidays;

    @NotNull
    @Field("working_hours")
    private Integer workingHours;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public FunctionalRole name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public FunctionalRole description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxHolidays() {
        return maxHolidays;
    }

    public FunctionalRole maxHolidays(Integer maxHolidays) {
        this.maxHolidays = maxHolidays;
        return this;
    }

    public void setMaxHolidays(Integer maxHolidays) {
        this.maxHolidays = maxHolidays;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public FunctionalRole workingHours(Integer workingHours) {
        this.workingHours = workingHours;
        return this;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FunctionalRole)) {
            return false;
        }
        return id != null && id.equals(((FunctionalRole) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FunctionalRole{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", maxHolidays=" + getMaxHolidays() +
            ", workingHours=" + getWorkingHours() +
            "}";
    }
}
