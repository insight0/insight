package com.apeiron.insight.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Config.
 */
@Document(collection = "config")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "config")
public class Config implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("holiday_email_send")
    private Integer holidayEmailSend;

    @NotNull
    @Field("new_user_welcomming_email")
    private Boolean newUserWelcommingEmail;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHolidayEmailSend() {
        return holidayEmailSend;
    }

    public Config holidayEmailSend(Integer holidayEmailSend) {
        this.holidayEmailSend = holidayEmailSend;
        return this;
    }

    public void setHolidayEmailSend(Integer holidayEmailSend) {
        this.holidayEmailSend = holidayEmailSend;
    }

    public Boolean isNewUserWelcommingEmail() {
        return newUserWelcommingEmail;
    }

    public Config newUserWelcommingEmail(Boolean newUserWelcommingEmail) {
        this.newUserWelcommingEmail = newUserWelcommingEmail;
        return this;
    }

    public void setNewUserWelcommingEmail(Boolean newUserWelcommingEmail) {
        this.newUserWelcommingEmail = newUserWelcommingEmail;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Config)) {
            return false;
        }
        return id != null && id.equals(((Config) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Config{" +
            "id=" + getId() +
            ", holidayEmailSend=" + getHolidayEmailSend() +
            ", newUserWelcommingEmail='" + isNewUserWelcommingEmail() + "'" +
            "}";
    }
}
