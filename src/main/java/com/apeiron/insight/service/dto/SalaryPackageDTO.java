package com.apeiron.insight.service.dto;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.apeiron.insight.domain.SalaryPackage} entity.
 */
public class SalaryPackageDTO implements Serializable {

    private String id;

    /**
     * TODO: private List<ChangeLog> changeLogs;
     */
    @NotNull
    @ApiModelProperty(value = "TODO: private List<ChangeLog> changeLogs;", required = true)
    private Float netSalaryPerMonth;

    @NotNull
    private Float nationalInsurancePerMonth;

    @NotNull
    private Float taxPerMonth;

    @NotNull
    private Float privateInsurancePerMonth;

    @NotNull
    private Float mealVoucherPerMonth;

    @NotNull
    private Float bonusPerYear;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Float getNetSalaryPerMonth() {
        return netSalaryPerMonth;
    }

    public void setNetSalaryPerMonth(Float netSalaryPerMonth) {
        this.netSalaryPerMonth = netSalaryPerMonth;
    }

    public Float getNationalInsurancePerMonth() {
        return nationalInsurancePerMonth;
    }

    public void setNationalInsurancePerMonth(Float nationalInsurancePerMonth) {
        this.nationalInsurancePerMonth = nationalInsurancePerMonth;
    }

    public Float getTaxPerMonth() {
        return taxPerMonth;
    }

    public void setTaxPerMonth(Float taxPerMonth) {
        this.taxPerMonth = taxPerMonth;
    }

    public Float getPrivateInsurancePerMonth() {
        return privateInsurancePerMonth;
    }

    public void setPrivateInsurancePerMonth(Float privateInsurancePerMonth) {
        this.privateInsurancePerMonth = privateInsurancePerMonth;
    }

    public Float getMealVoucherPerMonth() {
        return mealVoucherPerMonth;
    }

    public void setMealVoucherPerMonth(Float mealVoucherPerMonth) {
        this.mealVoucherPerMonth = mealVoucherPerMonth;
    }

    public Float getBonusPerYear() {
        return bonusPerYear;
    }

    public void setBonusPerYear(Float bonusPerYear) {
        this.bonusPerYear = bonusPerYear;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        SalaryPackageDTO salaryPackageDTO = (SalaryPackageDTO) o;
        if (salaryPackageDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), salaryPackageDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "SalaryPackageDTO{" +
            "id=" + getId() +
            ", netSalaryPerMonth=" + getNetSalaryPerMonth() +
            ", nationalInsurancePerMonth=" + getNationalInsurancePerMonth() +
            ", taxPerMonth=" + getTaxPerMonth() +
            ", privateInsurancePerMonth=" + getPrivateInsurancePerMonth() +
            ", mealVoucherPerMonth=" + getMealVoucherPerMonth() +
            ", bonusPerYear=" + getBonusPerYear() +
            "}";
    }
}
