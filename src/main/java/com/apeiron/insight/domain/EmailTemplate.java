package com.apeiron.insight.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A EmailTemplate.
 */
@Document(collection = "email_template")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "emailtemplate")
public class EmailTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("holiday_email_template")
    private String holidayEmailTemplate;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolidayEmailTemplate() {
        return holidayEmailTemplate;
    }

    public EmailTemplate holidayEmailTemplate(String holidayEmailTemplate) {
        this.holidayEmailTemplate = holidayEmailTemplate;
        return this;
    }

    public void setHolidayEmailTemplate(String holidayEmailTemplate) {
        this.holidayEmailTemplate = holidayEmailTemplate;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EmailTemplate)) {
            return false;
        }
        return id != null && id.equals(((EmailTemplate) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "EmailTemplate{" +
            "id=" + getId() +
            ", holidayEmailTemplate='" + getHolidayEmailTemplate() + "'" +
            "}";
    }
}
