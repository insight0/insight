package com.apeiron.insight.domain;

import com.apeiron.insight.config.Constants;

import com.apeiron.insight.domain.enumeration.ContractType;
import com.apeiron.insight.domain.enumeration.Gender;
import com.apeiron.insight.domain.enumeration.Seniority;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

/**
 * A user.
 */
@org.springframework.data.mongodb.core.mapping.Document(collection = "jhi_user")
@org.springframework.data.elasticsearch.annotations.Document(indexName = "user")
public class User extends AbstractAuditingEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private String id;

    @NotNull
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    @Indexed
    private String login;

    @JsonIgnore
    @NotNull
    @Size(min = 60, max = 60)
    private String password;

    @Size(max = 50)
    @Field("first_name")
    private String firstName;

    @Size(max = 50)
    @Field("last_name")
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    @Indexed
    private String email;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    @Field("lang_key")
    private String langKey;

    @Size(max = 256)
    @Field("image_url")
    private String imageUrl;

    @Field("functional_role_ids")
    private Set<String> functionalRoleIds = new HashSet<>();

    @Field("contract")
    private List<Contract> contract = new ArrayList<>();

    @Field("address")
    private Address address;

    @Field("salary_package")
    private SalaryPackage salaryPackage;

    @Field("cnss_affiliate_number")
    private String cnssAffiliateNumber;

    @Field("gender")
    private Gender gender;

    @Field("birth_date")
    private Date birthDate;

    @Size(max = 20)
    @Field("activation_key")
    @JsonIgnore
    private String activationKey;

    @Size(max = 20)
    @Field("reset_key")
    @JsonIgnore
    private String resetKey;

    @Field("reset_date")
    private Instant resetDate = null;

    @Field("phone_number")
    private String phoneNumber;

    @Field("title")
    private String title;

    @Field("parent_phone_number")
    private String parentPhoneNumber;

    @Field("parent_phone_owner")
    private String parentPhoneOwner;

    @Field("id_card_number")
    private String idCardNumber;

    @Max(100)
    @Min(0)
    @Field("management_seniority")
    private Integer managementSeniority;

    @Max(100)
    @Min(0)
    @Field("leadership_seniority")
    private Integer leadershipSeniority;

    @Max(100)
    @Min(0)
    @Field("technical_seniority")
    private Integer technicalSeniority;

    @Field("years_of_experience")
    private Integer yearsOfExperience;

    @Field("degrees")
    private List<Degree> degrees = new ArrayList<>();

    @Field("id_card")
    private DocumentPlaceholder idCard;

    @Field("payslips")
    private List<DocumentPlaceholder> payslips = new ArrayList<>();

    @Field("certifications")
    private List<Certification> certifications = new ArrayList<>();

    @Field("skill_ids")
    private Set<String> skillIds = new HashSet<>();

    @JsonIgnore
    private Set<Authority> authorities = new HashSet<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    // Lowercase the login before saving it in database
    public void setLogin(String login) {
        this.login = StringUtils.lowerCase(login, Locale.ENGLISH);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getActivationKey() {
        return activationKey;
    }

    public void setActivationKey(String activationKey) {
        this.activationKey = activationKey;
    }

    public String getResetKey() {
        return resetKey;
    }

    public void setResetKey(String resetKey) {
        this.resetKey = resetKey;
    }

    public Instant getResetDate() {
        return resetDate;
    }

    public void setResetDate(Instant resetDate) {
        this.resetDate = resetDate;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<Authority> authorities) {
        this.authorities = authorities;
    }

    public boolean isActivated() {
        return activated;
    }

    public Set<String> getFunctionalRoleIds() {
        return functionalRoleIds;
    }

    public void setFunctionalRoleIds(Set<String> functionalRoleIds) {
        this.functionalRoleIds = functionalRoleIds;
    }

    public List<Contract> getContract() {
        return contract;
    }

    public void setContract(List<Contract> contract) {
        this.contract = contract;
    }

    public SalaryPackage getSalaryPackage() {
        return salaryPackage;
    }

    public void setSalaryPackage(SalaryPackage salaryPackage) {
        this.salaryPackage = salaryPackage;
    }

    public Address getAddress() {
        return address;
    }

    public Set<String> getSkillIds() {
        return skillIds;
    }

    public void setSkillIds(Set<String> skillIds) {
        this.skillIds = skillIds;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public List<Certification> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<Certification> certifications) {
        this.certifications = certifications;
    }

    public Integer getManagementSeniority() {
        return managementSeniority;
    }

    public void setManagementSeniority(Integer managementSeniority) {
        this.managementSeniority = managementSeniority;
    }

    public Integer getLeadershipSeniority() {
        return leadershipSeniority;
    }

    public void setLeadershipSeniority(Integer leadershipSeniority) {
        this.leadershipSeniority = leadershipSeniority;
    }

    public Integer getTechnicalSeniority() {
        return technicalSeniority;
    }

    public void setTechnicalSeniority(Integer technicalSeniority) {
        this.technicalSeniority = technicalSeniority;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public void setParentPhoneNumber(String parentPhoneNumber) {
        this.parentPhoneNumber = parentPhoneNumber;
    }

    public String getParentPhoneOwner() {
        return parentPhoneOwner;
    }

    public void setParentPhoneOwner(String parentPhoneOwner) {
        this.parentPhoneOwner = parentPhoneOwner;
    }


    public List<Degree> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<Degree> degrees) {
        this.degrees = degrees;
    }

    public DocumentPlaceholder getIdCard() {
        return idCard;
    }

    public void setIdCard(DocumentPlaceholder idCard) {
        this.idCard = idCard;
    }

    public List<DocumentPlaceholder> getPayslips() {
        return payslips;
    }

    public void setPayslips(List<DocumentPlaceholder> payslips) {
        this.payslips = payslips;
    }

    public String getCnssAffiliateNumber() {
        return cnssAffiliateNumber;
    }

    public void setCnssAffiliateNumber(String cnssAffiliateNumber) {
        this.cnssAffiliateNumber = cnssAffiliateNumber;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getIdCardNumber() {
        return idCardNumber;
    }

    public void setIdCardNumber(String idCardNumber) {
        this.idCardNumber = idCardNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        return id != null && id.equals(((User) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "User{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated='" + activated + '\'' +
            ", langKey='" + langKey + '\'' +
            ", activationKey='" + activationKey + '\'' +
            "}";
    }
}
