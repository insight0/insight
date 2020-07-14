package com.apeiron.insight.service.dto;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.apeiron.insight.domain.enumeration.DayOffStatus;
import com.apeiron.insight.domain.enumeration.DayOffType;

/**
 * A DTO for the {@link com.apeiron.insight.domain.DayOff} entity.
 */
public class DayOffDTO implements Serializable {

    private String id;

    @NotNull
    private Instant startDate;

    @NotNull
    private Instant endDate;

    @NotNull
    private String dayOffObject;

    private DayOffStatus status;

    //@NotNull
    private Boolean forced;

    //@NotNull
    private String employeId;

    //@NotNull
    private String validatorId;

    //@NotNull
    private Float days;

    private UserDTO user;
    private UserDTO validator;

    private DayOffType type;
    private String generatedApprovalFilePath;
    private String approvalFilePath;
    private String medicalCertificateFilePath;
    private String note;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public String getDayOffObject() {
        return dayOffObject;
    }

    public void setDayOffObject(String dayOffObject) {
        this.dayOffObject = dayOffObject;
    }

    public DayOffStatus getStatus() {
        return status;
    }

    public void setStatus(DayOffStatus status) {
        this.status = status;
    }

    public Boolean isForced() {
        return forced;
    }

    public void setForced(Boolean forced) {
        this.forced = forced;
    }

    public String getEmployeId() {
        return employeId;
    }

    public void setEmployeId(String employeId) {
        this.employeId = employeId;
    }

    public String getValidatorId() {
        return validatorId;
    }

    public void setValidatorId(String validatorId) {
        this.validatorId = validatorId;
    }

    public Float getDays() {
        return days;
    }

    public void setDays(Float days) {
        this.days = days;
    }

    public Boolean getForced() {
        return forced;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
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

    public UserDTO getValidator() {
        return validator;
    }

    public void setValidator(UserDTO validator) {
        this.validator = validator;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DayOffDTO dayOffDTO = (DayOffDTO) o;
        if (dayOffDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dayOffDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DayOffDTO{" +
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
