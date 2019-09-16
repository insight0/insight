package com.apeiron.insight.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apeiron.insight.domain.Config} entity.
 */
public class ConfigDTO implements Serializable {

    private String id;

    private Integer holidayEmailSendDate;

    @NotNull
    private Boolean holidayEmailNotification;

    @NotNull
    private Boolean welcomingEmailNotification;

    @NotNull
    private Boolean birthdayEmailNotification;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHolidayEmailSendDate() {
        return holidayEmailSendDate;
    }

    public void setHolidayEmailSendDate(Integer holidayEmailSendDate) {
        this.holidayEmailSendDate = holidayEmailSendDate;
    }

    public Boolean isHolidayEmailNotification() {
        return holidayEmailNotification;
    }

    public void setHolidayEmailNotification(Boolean holidayEmailNotification) {
        this.holidayEmailNotification = holidayEmailNotification;
    }

    public Boolean isWelcomingEmailNotification() {
        return welcomingEmailNotification;
    }

    public void setWelcomingEmailNotification(Boolean welcomingEmailNotification) {
        this.welcomingEmailNotification = welcomingEmailNotification;
    }

    public Boolean isBirthdayEmailNotification() {
        return birthdayEmailNotification;
    }

    public void setBirthdayEmailNotification(Boolean birthdayEmailNotification) {
        this.birthdayEmailNotification = birthdayEmailNotification;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ConfigDTO configDTO = (ConfigDTO) o;
        if (configDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), configDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ConfigDTO{" +
            "id=" + getId() +
            ", holidayEmailSendDate=" + getHolidayEmailSendDate() +
            ", holidayEmailNotification='" + isHolidayEmailNotification() + "'" +
            ", welcomingEmailNotification='" + isWelcomingEmailNotification() + "'" +
            ", birthdayEmailNotification='" + isBirthdayEmailNotification() + "'" +
            "}";
    }
}
