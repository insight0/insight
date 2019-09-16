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

    @Field("holiday_email_send_date")
    private Integer holidayEmailSendDate;

    @NotNull
    @Field("holiday_email_notification")
    private Boolean holidayEmailNotification;

    @NotNull
    @Field("welcoming_email_notification")
    private Boolean welcomingEmailNotification;

    @NotNull
    @Field("birthday_email_notification")
    private Boolean birthdayEmailNotification;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHolidayEmailSendDate() {
        return holidayEmailSendDate;
    }

    public Config holidayEmailSendDate(Integer holidayEmailSendDate) {
        this.holidayEmailSendDate = holidayEmailSendDate;
        return this;
    }

    public void setHolidayEmailSendDate(Integer holidayEmailSendDate) {
        this.holidayEmailSendDate = holidayEmailSendDate;
    }

    public Boolean isHolidayEmailNotification() {
        return holidayEmailNotification;
    }

    public Config holidayEmailNotification(Boolean holidayEmailNotification) {
        this.holidayEmailNotification = holidayEmailNotification;
        return this;
    }

    public void setHolidayEmailNotification(Boolean holidayEmailNotification) {
        this.holidayEmailNotification = holidayEmailNotification;
    }

    public Boolean isWelcomingEmailNotification() {
        return welcomingEmailNotification;
    }

    public Config welcomingEmailNotification(Boolean welcomingEmailNotification) {
        this.welcomingEmailNotification = welcomingEmailNotification;
        return this;
    }

    public void setWelcomingEmailNotification(Boolean welcomingEmailNotification) {
        this.welcomingEmailNotification = welcomingEmailNotification;
    }

    public Boolean isBirthdayEmailNotification() {
        return birthdayEmailNotification;
    }

    public Config birthdayEmailNotification(Boolean birthdayEmailNotification) {
        this.birthdayEmailNotification = birthdayEmailNotification;
        return this;
    }

    public void setBirthdayEmailNotification(Boolean birthdayEmailNotification) {
        this.birthdayEmailNotification = birthdayEmailNotification;
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
            ", holidayEmailSendDate=" + getHolidayEmailSendDate() +
            ", holidayEmailNotification='" + isHolidayEmailNotification() + "'" +
            ", welcomingEmailNotification='" + isWelcomingEmailNotification() + "'" +
            ", birthdayEmailNotification='" + isBirthdayEmailNotification() + "'" +
            "}";
    }
}
