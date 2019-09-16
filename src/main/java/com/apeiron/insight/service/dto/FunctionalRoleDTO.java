package com.apeiron.insight.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apeiron.insight.domain.FunctionalRole} entity.
 */
public class FunctionalRoleDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String description;

    @NotNull
    private Integer maxHolidays;

    @NotNull
    private Integer workingHours;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getMaxHolidays() {
        return maxHolidays;
    }

    public void setMaxHolidays(Integer maxHolidays) {
        this.maxHolidays = maxHolidays;
    }

    public Integer getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(Integer workingHours) {
        this.workingHours = workingHours;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FunctionalRoleDTO functionalRoleDTO = (FunctionalRoleDTO) o;
        if (functionalRoleDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), functionalRoleDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FunctionalRoleDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", maxHolidays=" + getMaxHolidays() +
            ", workingHours=" + getWorkingHours() +
            "}";
    }
}
