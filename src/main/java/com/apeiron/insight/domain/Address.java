package com.apeiron.insight.domain;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.Document;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A Address.
 */
@org.springframework.data.elasticsearch.annotations.Document(indexName = "address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    @Field("line_1")
    private String line1;

    @Field("line_2")
    private String line2;

    @NotNull
    @Field("city")
    private String city;

    @NotNull
    @Field("postal_code")
    private String postalCode;

    @NotNull
    @Field("country")
    private String country;

    @Field("ltd")
    private Integer ltd;

    @Field("lgt")
    private Integer lgt;

    public String getLine1() {
        return line1;
    }

    public Address line1(String line1) {
        this.line1 = line1;
        return this;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public Address line2(String line2) {
        this.line2 = line2;
        return this;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public Address city(String city) {
        this.city = city;
        return this;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public Address postalCode(String postalCode) {
        this.postalCode = postalCode;
        return this;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public Address country(String country) {
        this.country = country;
        return this;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Integer getLtd() {
        return ltd;
    }

    public Address ltd(Integer ltd) {
        this.ltd = ltd;
        return this;
    }

    public void setLtd(Integer ltd) {
        this.ltd = ltd;
    }

    public Integer getLgt() {
        return lgt;
    }

    public Address lgt(Integer lgt) {
        this.lgt = lgt;
        return this;
    }

    public void setLgt(Integer lgt) {
        this.lgt = lgt;
    }


    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Address{" +
            ", line1='" + getLine1() + "'" +
            ", line2='" + getLine2() + "'" +
            ", city='" + getCity() + "'" +
            ", postalCode='" + getPostalCode() + "'" +
            ", country='" + getCountry() + "'" +
            ", ltd=" + getLtd() +
            ", lgt=" + getLgt() +
            "}";
    }
}
