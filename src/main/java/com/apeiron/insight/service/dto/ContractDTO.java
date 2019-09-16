package com.apeiron.insight.service.dto;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.apeiron.insight.domain.enumeration.ContractType;

/**
 * A DTO for the {@link com.apeiron.insight.domain.Contract} entity.
 */
public class ContractDTO implements Serializable {

    private String id;

    /**
     * TODO: private DocumentPlaceholder digitalCopy;
     */
    @NotNull
    @ApiModelProperty(value = "TODO: private DocumentPlaceholder digitalCopy;", required = true)
    private ContractType type;

    @NotNull
    private Instant startDate;

    private Instant endDate;

    private Instant employeeSignatureDate;

    private Instant directionSignatureDate;

    private String description;

    @NotNull
    private Float dayOffNamber;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ContractType getType() {
        return type;
    }

    public void setType(ContractType type) {
        this.type = type;
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

    public Instant getEmployeeSignatureDate() {
        return employeeSignatureDate;
    }

    public void setEmployeeSignatureDate(Instant employeeSignatureDate) {
        this.employeeSignatureDate = employeeSignatureDate;
    }

    public Instant getDirectionSignatureDate() {
        return directionSignatureDate;
    }

    public void setDirectionSignatureDate(Instant directionSignatureDate) {
        this.directionSignatureDate = directionSignatureDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Float getDayOffNamber() {
        return dayOffNamber;
    }

    public void setDayOffNamber(Float dayOffNamber) {
        this.dayOffNamber = dayOffNamber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContractDTO contractDTO = (ContractDTO) o;
        if (contractDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), contractDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ContractDTO{" +
            "id=" + getId() +
            ", type='" + getType() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", employeeSignatureDate='" + getEmployeeSignatureDate() + "'" +
            ", directionSignatureDate='" + getDirectionSignatureDate() + "'" +
            ", description='" + getDescription() + "'" +
            ", dayOffNamber=" + getDayOffNamber() +
            "}";
    }
}
