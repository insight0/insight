package com.apeiron.insight.service.dto;

import java.util.List;

public class UserDayOffDTO {

    private UserDTO user;
    private List<DayOffDTO> dayOffs;

    private Float annualDayOffAuthorised;
    private Float sicknessDayOffAuthorised;

    private Float annualDayOffConsumed;
    private Float sicknessDayOffConsumed;
    private Float unpaidDayOffConsumed;

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public List<DayOffDTO> getDayOffs() {
        return dayOffs;
    }

    public void setDayOffs(List<DayOffDTO> dayOffs) {
        this.dayOffs = dayOffs;
    }

    public Float getAnnualDayOffAuthorised() {
        return annualDayOffAuthorised;
    }

    public void setAnnualDayOffAuthorised(Float annualDayOffAuthorised) {
        this.annualDayOffAuthorised = annualDayOffAuthorised;
    }

    public Float getSicknessDayOffAuthorised() {
        return sicknessDayOffAuthorised;
    }

    public void setSicknessDayOffAuthorised(Float sicknessDayOffAuthorised) {
        this.sicknessDayOffAuthorised = sicknessDayOffAuthorised;
    }

    public Float getAnnualDayOffConsumed() {
        return annualDayOffConsumed;
    }

    public void setAnnualDayOffConsumed(Float annualDayOffConsumed) {
        this.annualDayOffConsumed = annualDayOffConsumed;
    }

    public Float getSicknessDayOffConsumed() {
        return sicknessDayOffConsumed;
    }

    public void setSicknessDayOffConsumed(Float sicknessDayOffConsumed) {
        this.sicknessDayOffConsumed = sicknessDayOffConsumed;
    }

    public Float getUnpaidDayOffConsumed() {
        return unpaidDayOffConsumed;
    }

    public void setUnpaidDayOffConsumed(Float unpaidDayOffConsumed) {
        this.unpaidDayOffConsumed = unpaidDayOffConsumed;
    }
}
