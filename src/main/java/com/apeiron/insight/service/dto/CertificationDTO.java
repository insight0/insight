package com.apeiron.insight.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apeiron.insight.domain.Certification} entity.
 */
public class CertificationDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private Integer year;

    @NotNull
    private String organization;

    private String filePath;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CertificationDTO certificationDTO = (CertificationDTO) o;
        if (certificationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), certificationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CertificationDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", year=" + getYear() +
            ", organization='" + getOrganization() + "'" +
            ", filePath='" + getFilePath() + "'" +
            "}";
    }
}
