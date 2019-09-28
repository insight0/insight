package com.apeiron.insight.domain;
import com.apeiron.insight.domain.enumeration.DayOffType;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.Instant;

import com.apeiron.insight.domain.enumeration.DayOffStatus;

/**
 * A DayOff.
 */
@Document(collection = "day_off")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dayoff")
public class DayOff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("start_date")
    private Instant startDate;

    @NotNull
    @Field("end_date")
    private Instant endDate;

    @NotNull
    @Field("day_off_object")
    private String dayOffObject;

    @NotNull
    @Field("status")
    private DayOffStatus status;

    @NotNull
    @Field("type")
    private DayOffType type;

    @Field("generated_approval_file_path")
    private String generatedApprovalFilePath;

    @Field("approval_filepath")
    private String approvalFilePath;

    @Field("medical_certificate_file_path")
    private String medicalCertificateFilePath;

    @Field("note")
    private String note;

    @NotNull
    @Field("forced")
    private Boolean forced;

    @NotNull
    @Field("employe_id")
    private String employeId;

    @NotNull
    @Field("validator_id")
    private String validatorId;

    @NotNull
    @Field("days")
    private Float days;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public DayOff startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public DayOff endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getDayOffObject() {
        return dayOffObject;
    }

    public DayOff dayOffObject(String dayOffObject) {
        this.dayOffObject = dayOffObject;
        return this;
    }

    public void setDayOffObject(String dayOffObject) {
        this.dayOffObject = dayOffObject;
    }

    public DayOffStatus getStatus() {
        return status;
    }

    public DayOff status(DayOffStatus status) {
        this.status = status;
        return this;
    }

    public void setStatus(DayOffStatus status) {
        this.status = status;
    }

    public Boolean isForced() {
        return forced;
    }

    public DayOff forced(Boolean forced) {
        this.forced = forced;
        return this;
    }

    public void setForced(Boolean forced) {
        this.forced = forced;
    }

    public String getEmployeId() {
        return employeId;
    }

    public DayOff employeId(String employeId) {
        this.employeId = employeId;
        return this;
    }

    public void setEmployeId(String employeId) {
        this.employeId = employeId;
    }

    public String getValidatorId() {
        return validatorId;
    }

    public DayOff validatorId(String validatorId) {
        this.validatorId = validatorId;
        return this;
    }

    public void setValidatorId(String validatorId) {
        this.validatorId = validatorId;
    }

    public Float getDays() {
        return days;
    }

    public DayOff days(Float days) {
        this.days = days;
        return this;
    }

    public DayOffType getType() {
        return type;
    }

    public void setType(DayOffType type) {
        this.type = type;
    }

    public String getGeneratedApprovalFilePath() {
        return generatedApprovalFilePath;
    }

    public void setGeneratedApprovalFilePath(String generatedApprovalFilePath) {
        this.generatedApprovalFilePath = generatedApprovalFilePath;
    }

    public String getApprovalFilePath() {
        return approvalFilePath;
    }

    public void setApprovalFilePath(String approvalFilePath) {
        this.approvalFilePath = approvalFilePath;
    }

    public String getMedicalCertificateFilePath() {
        return medicalCertificateFilePath;
    }

    public void setMedicalCertificateFilePath(String medicalCertificateFilePath) {
        this.medicalCertificateFilePath = medicalCertificateFilePath;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Boolean getForced() {
        return forced;
    }

    public void setDays(Float days) {
        this.days = days;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DayOff)) {
            return false;
        }
        return id != null && id.equals(((DayOff) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DayOff{" +
            "id=" + getId() +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", dayOffObject='" + getDayOffObject() + "'" +
            ", status='" + getStatus() + "'" +
            ", forced='" + isForced() + "'" +
            ", employeId='" + getEmployeId() + "'" +
            ", validatorId='" + getValidatorId() + "'" +
            ", days=" + getDays() +
            "}";
    }
}
