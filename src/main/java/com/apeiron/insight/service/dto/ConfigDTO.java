package com.apeiron.insight.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apeiron.insight.domain.Config} entity.
 */
public class ConfigDTO implements Serializable {

    private String id;

    private Integer holidayEmailSend;

    @NotNull
    private Boolean newUserWelcommingEmail;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getHolidayEmailSend() {
        return holidayEmailSend;
    }

    public void setHolidayEmailSend(Integer holidayEmailSend) {
        this.holidayEmailSend = holidayEmailSend;
    }

    public Boolean isNewUserWelcommingEmail() {
        return newUserWelcommingEmail;
    }

    public void setNewUserWelcommingEmail(Boolean newUserWelcommingEmail) {
        this.newUserWelcommingEmail = newUserWelcommingEmail;
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
            ", holidayEmailSend=" + getHolidayEmailSend() +
            ", newUserWelcommingEmail='" + isNewUserWelcommingEmail() + "'" +
            "}";
    }
}
