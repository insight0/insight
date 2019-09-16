package com.apeiron.insight.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * A SalaryPackage.
 */
public class SalaryPackage implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @Field("change_log")
    private List<ChangeLog> changeLogs;

    @NotNull
    @Field("net_salary_per_month")
    private Float netSalaryPerMonth;

    @NotNull
    @Field("national_insurance_per_month")
    private Float nationalInsurancePerMonth;

    @NotNull
    @Field("tax_per_month")
    private Float taxPerMonth;

    @NotNull
    @Field("private_insurance_per_month")
    private Float privateInsurancePerMonth;

    @NotNull
    @Field("meal_voucher_per_month")
    private Float mealVoucherPerMonth;

    @NotNull
    @Field("bonus_per_year")
    private Float bonusPerYear;

    public Float getNetSalaryPerMonth() {
        return netSalaryPerMonth;
    }

    public SalaryPackage netSalaryPerMonth(Float netSalaryPerMonth) {
        this.netSalaryPerMonth = netSalaryPerMonth;
        return this;
    }

    public void setNetSalaryPerMonth(Float netSalaryPerMonth) {
        this.netSalaryPerMonth = netSalaryPerMonth;
    }

    public Float getNationalInsurancePerMonth() {
        return nationalInsurancePerMonth;
    }

    public SalaryPackage nationalInsurancePerMonth(Float nationalInsurancePerMonth) {
        this.nationalInsurancePerMonth = nationalInsurancePerMonth;
        return this;
    }

    public void setNationalInsurancePerMonth(Float nationalInsurancePerMonth) {
        this.nationalInsurancePerMonth = nationalInsurancePerMonth;
    }

    public Float getTaxPerMonth() {
        return taxPerMonth;
    }

    public SalaryPackage taxPerMonth(Float taxPerMonth) {
        this.taxPerMonth = taxPerMonth;
        return this;
    }

    public void setTaxPerMonth(Float taxPerMonth) {
        this.taxPerMonth = taxPerMonth;
    }

    public Float getPrivateInsurancePerMonth() {
        return privateInsurancePerMonth;
    }

    public SalaryPackage privateInsurancePerMonth(Float privateInsurancePerMonth) {
        this.privateInsurancePerMonth = privateInsurancePerMonth;
        return this;
    }

    public void setPrivateInsurancePerMonth(Float privateInsurancePerMonth) {
        this.privateInsurancePerMonth = privateInsurancePerMonth;
    }

    public Float getMealVoucherPerMonth() {
        return mealVoucherPerMonth;
    }

    public SalaryPackage mealVoucherPerMonth(Float mealVoucherPerMonth) {
        this.mealVoucherPerMonth = mealVoucherPerMonth;
        return this;
    }

    public void setMealVoucherPerMonth(Float mealVoucherPerMonth) {
        this.mealVoucherPerMonth = mealVoucherPerMonth;
    }

    public Float getBonusPerYear() {
        return bonusPerYear;
    }

    public SalaryPackage bonusPerYear(Float bonusPerYear) {
        this.bonusPerYear = bonusPerYear;
        return this;
    }


    public List<ChangeLog> getChangeLogs() {
        return changeLogs;
    }

    public void setChangeLogs(List<ChangeLog> changeLogs) {
        this.changeLogs = changeLogs;
    }

    public void setBonusPerYear(Float bonusPerYear) {
        this.bonusPerYear = bonusPerYear;
    }


    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "SalaryPackage{" +
            ", netSalaryPerMonth=" + getNetSalaryPerMonth() +
            ", nationalInsurancePerMonth=" + getNationalInsurancePerMonth() +
            ", taxPerMonth=" + getTaxPerMonth() +
            ", privateInsurancePerMonth=" + getPrivateInsurancePerMonth() +
            ", mealVoucherPerMonth=" + getMealVoucherPerMonth() +
            ", bonusPerYear=" + getBonusPerYear() +
            "}";
    }
}
