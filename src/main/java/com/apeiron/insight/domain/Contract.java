package com.apeiron.insight.domain;

import com.apeiron.insight.domain.enumeration.ContractType;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Instant;

/**
 * A Contract.
 */
public class Contract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Field("type")
    private ContractType type;

    @NotNull
    @Field("start_date")
    private Instant startDate;

    @Field("end_date")
    private Instant endDate;

    @Field("employee_signature_date")
    private Instant employeeSignatureDate;

    @Field("direction_signature_date")
    private Instant directionSignatureDate;

    @Field("description")
    private String description;

    @NotNull
    @Field("day_off_number")
    private Float dayOffNumber;

    @Field("digital_copy")
    private DocumentPlaceholder digitalCopy;

    public ContractType getType() {
        return type;
    }

    public Contract type(ContractType type) {
        this.type = type;
        return this;
    }

    public void setType(ContractType type) {
        this.type = type;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Contract startDate(Instant startDate) {
        this.startDate = startDate;
        return this;
    }

    public void setStartDate(Instant startDate) {
        this.startDate = startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public Contract endDate(Instant endDate) {
        this.endDate = endDate;
        return this;
    }

    public void setEndDate(Instant endDate) {
        this.endDate = endDate;
    }

    public Instant getEmployeeSignatureDate() {
        return employeeSignatureDate;
    }

    public Contract employeeSignatureDate(Instant employeeSignatureDate) {
        this.employeeSignatureDate = employeeSignatureDate;
        return this;
    }

    public void setEmployeeSignatureDate(Instant employeeSignatureDate) {
        this.employeeSignatureDate = employeeSignatureDate;
    }

    public Instant getDirectionSignatureDate() {
        return directionSignatureDate;
    }

    public Contract directionSignatureDate(Instant directionSignatureDate) {
        this.directionSignatureDate = directionSignatureDate;
        return this;
    }

    public void setDirectionSignatureDate(Instant directionSignatureDate) {
        this.directionSignatureDate = directionSignatureDate;
    }

    public String getDescription() {
        return description;
    }

    public Contract description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getDayOffNumber() {
        return dayOffNumber;
    }

    public Contract dayOffNumber(Float dayOffNamber) {
        this.dayOffNumber = dayOffNamber;
        return this;
    }

    public void setDayOffNamber(Float dayOffNamber) {
        this.dayOffNumber = dayOffNamber;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove


    public DocumentPlaceholder getDigitalCopy() {
        return digitalCopy;
    }

    public void setDigitalCopy(DocumentPlaceholder digitalCopy) {
        this.digitalCopy = digitalCopy;
    }


    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Contract{" +
            ", type='" + getType() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", employeeSignatureDate='" + getEmployeeSignatureDate() + "'" +
            ", directionSignatureDate='" + getDirectionSignatureDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", dayOffNamber=" + getDayOffNumber() +
            "}";
    }
}
