package com.apeiron.insight.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apeiron.insight.domain.Skill} entity.
 */
public class SkillDTO implements Serializable {

    private String id;

    @NotNull
    private String name;


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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SkillDTO skillDTO = (SkillDTO) o;
        if (skillDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), skillDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SkillDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            "}";
    }
}
