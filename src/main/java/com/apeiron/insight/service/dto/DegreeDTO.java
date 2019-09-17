package com.apeiron.insight.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apeiron.insight.domain.Degree} entity.
 */
public class DegreeDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private Integer year;

    private Integer duration;

    @NotNull
    private String school;

    @NotNull
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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
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

        DegreeDTO degreeDTO = (DegreeDTO) o;
        if (degreeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), degreeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DegreeDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", year=" + getYear() +
            ", duration=" + getDuration() +
            ", school='" + getSchool() + "'" +
            ", filePath='" + getFilePath() + "'" +
            "}";
    }
}
