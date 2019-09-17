package com.apeiron.insight.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;
import java.util.Objects;

/**
 * A DTO for the {@link com.apeiron.insight.domain.Holiday} entity.
 */
public class HolidayDTO implements Serializable {

    private String id;

    @NotNull
    private String name;

    @NotNull
    private String description;


    private Integer year;


    private Integer dayOfYear;


    private Integer weekOfYear;


    private Integer dayOfWeek;

    @NotNull
    private Boolean paid;


    @NotNull
    private Instant date;

    @NotNull
    private Integer duration;


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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getDayOfYear() {
        return dayOfYear;
    }

    public void setDayOfYear(Integer dayOfYear) {
        this.dayOfYear = dayOfYear;
    }

    public Integer getWeekOfYear() {
        return weekOfYear;
    }

    public void setWeekOfYear(Integer weekOfYear) {
        this.weekOfYear = weekOfYear;
    }

    public Integer getDayOfWeek() {
        return dayOfWeek;
    }

    public void setDayOfWeek(Integer dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }


    public Boolean getPaid() {
        return paid;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }


    public String getFrenchFormattedDate() {
        DateTimeFormatter formatter =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(Locale.FRANCE)
                .withZone(ZoneId.systemDefault());

        String formatedDate = "le " + formatter.format(date);

        if(duration > 1){

            for (int i = 1; i< duration; i++) {

                Instant instant = date.plus(i , ChronoUnit.DAYS);
                formatedDate = formatedDate + " et le" + formatter.format(instant);
            }

        }


        return formatedDate;
    }


    public String getFrenchFormattedNextDate() {

        Instant instant = date.plus(this.duration, ChronoUnit.DAYS);

        ZoneId z = ZoneId.of("Africa/Tunis");
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant, z);

        if(zdt.getDayOfWeek().getValue() == 6){
            instant = date.plus(3, ChronoUnit.DAYS);
        }

        if(zdt.getDayOfWeek().getValue() == 7){
            instant = date.plus(2, ChronoUnit.DAYS);
        }

        DateTimeFormatter formatter =
            DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)
                .withLocale(Locale.FRANCE)
                .withZone(ZoneId.systemDefault());

        return formatter.format(instant);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        HolidayDTO holidayDTO = (HolidayDTO) o;
        if (holidayDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), holidayDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return
            ", name='" + getName() + "'" +
            ", description='" + getDescription() + "'" +
            ", year=" + getYear() +
            ", dayOfYear=" + getDayOfYear() +
            ", weekOfYear=" + getWeekOfYear() +
            ", dayOfWeek=" + getDayOfWeek() +
            ", paid='" + isPaid() + "'";
    }
}
