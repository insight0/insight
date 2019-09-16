package com.apeiron.insight.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.apeiron.insight.domain.enumeration.ChangeTrigger;
import com.apeiron.insight.domain.enumeration.ChangeType;

/**
 * A DTO for the {@link com.apeiron.insight.domain.ChangeLog} entity.
 */
public class ChangeLogDTO implements Serializable {

    private String id;

    @NotNull
    private String userId;

    @NotNull
    private Instant date;

    @NotNull
    private ChangeTrigger changeTrigger;

    @NotNull
    private ChangeType changeType;

    private String description;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public ChangeTrigger getChangeTrigger() {
        return changeTrigger;
    }

    public void setChangeTrigger(ChangeTrigger changeTrigger) {
        this.changeTrigger = changeTrigger;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChangeLogDTO changeLogDTO = (ChangeLogDTO) o;
        if (changeLogDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), changeLogDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChangeLogDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", date='" + getDate() + "'" +
            ", changeTrigger='" + getChangeTrigger() + "'" +
            ", changeType='" + getChangeType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
