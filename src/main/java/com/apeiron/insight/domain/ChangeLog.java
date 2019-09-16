package com.apeiron.insight.domain;

import com.apeiron.insight.domain.enumeration.ChangeTrigger;
import com.apeiron.insight.domain.enumeration.ChangeType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A ChangeLog.
 */
public class ChangeLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("user_id")
    private String userId;

    @NotNull
    @Field("date")
    private Instant date;

    @NotNull
    @Field("change_trigger")
    private ChangeTrigger changeTrigger;

    @NotNull
    @Field("change_type")
    private ChangeType changeType;

    @Field("description")
    private String description;

    public String getUserId() {
        return userId;
    }

    public ChangeLog userId(String userId) {
        this.userId = userId;
        return this;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Instant getDate() {
        return date;
    }

    public ChangeLog date(Instant date) {
        this.date = date;
        return this;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public ChangeTrigger getChangeTrigger() {
        return changeTrigger;
    }

    public ChangeLog changeTrigger(ChangeTrigger changeTrigger) {
        this.changeTrigger = changeTrigger;
        return this;
    }

    public void setChangeTrigger(ChangeTrigger changeTrigger) {
        this.changeTrigger = changeTrigger;
    }

    public ChangeType getChangeType() {
        return changeType;
    }

    public ChangeLog changeType(ChangeType changeType) {
        this.changeType = changeType;
        return this;
    }

    public void setChangeType(ChangeType changeType) {
        this.changeType = changeType;
    }

    public String getDescription() {
        return description;
    }

    public ChangeLog description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ChangeLog{" +
            ", userId='" + getUserId() + "'" +
            ", date='" + getDate() + "'" +
            ", changeTrigger='" + getChangeTrigger() + "'" +
            ", changeType='" + getChangeType() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
