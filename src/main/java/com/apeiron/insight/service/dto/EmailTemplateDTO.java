package com.apeiron.insight.service.dto;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apeiron.insight.domain.EmailTemplate} entity.
 */
public class EmailTemplateDTO implements Serializable {

    private String id;

    private String holidayEmailTemplate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolidayEmailTemplate() {
        return holidayEmailTemplate;
    }

    public void setHolidayEmailTemplate(String holidayEmailTemplate) {
        this.holidayEmailTemplate = holidayEmailTemplate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmailTemplateDTO emailTemplateDTO = (EmailTemplateDTO) o;
        if (emailTemplateDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), emailTemplateDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmailTemplateDTO{" +
            "id=" + getId() +
            ", holidayEmailTemplate='" + getHolidayEmailTemplate() + "'" +
            "}";
    }
}
