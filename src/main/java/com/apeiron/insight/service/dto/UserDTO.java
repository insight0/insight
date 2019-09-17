package com.apeiron.insight.service.dto;

import com.apeiron.insight.config.Constants;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.domain.enumeration.Seniority;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import javax.validation.constraints.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private String id;

    @NotBlank
    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 50)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 254)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 10)
    private String langKey;

    private String createdBy;

    private Instant createdDate;

    private String lastModifiedBy;

    private Instant lastModifiedDate;

    private Set<String> authorities;

    private Set<FunctionalRoleDTO> functionalRoles = new HashSet<>();

    private List<ContractDTO> contract = new ArrayList<>();

    private SalaryPackageDTO salaryPackage;

    private AddressDTO address;

    private String phoneNumber;

    private String parentPhoneNumber;

    private String parentPhoneOwner;

    private Seniority managementSeniority;

    private Seniority leadershipSeniority;

    private Seniority technicalSeniority;

    private String title;

    private List<DegreeDTO> degrees = new ArrayList<>();

    private Integer yearsOfExperience;

    private List<CertificationDTO> certifications = new ArrayList<>();

    private Set<SkillDTO> skills = new HashSet<>();

    private DocumentPlaceholderDTO idCard;

    private List<DocumentPlaceholderDTO> payslips = new ArrayList<>();

    public UserDTO() {
        // Empty constructor needed for Jackson.
        this.functionalRoles = new HashSet<>();
    }

    public UserDTO(User user) {

        this.id = user.getId();
        this.login = user.getLogin();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.email = user.getEmail();
        this.activated = user.getActivated();
        this.imageUrl = user.getImageUrl();
        this.langKey = user.getLangKey();
        this.createdBy = user.getCreatedBy();
        this.createdDate = user.getCreatedDate();
        this.lastModifiedBy = user.getLastModifiedBy();
        this.lastModifiedDate = user.getLastModifiedDate();
        this.yearsOfExperience = user.getYearsOfExperience();
        this.phoneNumber = user.getPhoneNumber();
        this.parentPhoneNumber = user.getParentPhoneNumber();
        this.parentPhoneOwner = user.getParentPhoneOwner();

        this.managementSeniority = user.getManagementSeniority();
        this.leadershipSeniority = user.getLeadershipSeniority();
        this.technicalSeniority = user.getTechnicalSeniority();
        this.title = user.getTitle();
        this.authorities = user.getAuthorities().stream()
            .map(Authority::getName)
            .collect(Collectors.toSet());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLogin() {
        return login;
    }


    public void setLogin(String login) {
        this.login = login;
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Instant getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Instant lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public Seniority getManagementSeniority() {
        return managementSeniority;
    }

    public void setManagementSeniority(Seniority managementSeniority) {
        this.managementSeniority = managementSeniority;
    }

    public Seniority getLeadershipSeniority() {
        return leadershipSeniority;
    }

    public void setLeadershipSeniority(Seniority leadershipSeniority) {
        this.leadershipSeniority = leadershipSeniority;
    }

    public Seniority getTechnicalSeniority() {
        return technicalSeniority;
    }

    public void setTechnicalSeniority(Seniority technicalSeniority) {
        this.technicalSeniority = technicalSeniority;
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

    public Set<SkillDTO> getSkills() {
        return skills;
    }

    public void setSkills(Set<SkillDTO> skills) {
        this.skills = skills;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public SalaryPackageDTO getSalaryPackage() {
        return salaryPackage;
    }

    public void setSalaryPackage(SalaryPackageDTO salaryPackage) {
        this.salaryPackage = salaryPackage;
    }

    public List<DegreeDTO> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<DegreeDTO> degrees) {
        this.degrees = degrees;
    }

    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public List<CertificationDTO> getCertifications() {
        return certifications;
    }

    public void setCertifications(List<CertificationDTO> certifications) {
        this.certifications = certifications;
    }


    public DocumentPlaceholderDTO getIdCard() {
        return idCard;
    }

    public void setIdCard(DocumentPlaceholderDTO idCard) {
        this.idCard = idCard;
    }

    public List<DocumentPlaceholderDTO> getPayslips() {
        return payslips;
    }

    public void setPayslips(List<DocumentPlaceholderDTO> payslips) {
        this.payslips = payslips;
    }

    public Set<FunctionalRoleDTO> getFunctionalRoles() {
        return functionalRoles;
    }

    public void setFunctionalRoles(Set<FunctionalRoleDTO> functionalRoles) {
        this.functionalRoles = functionalRoles;
    }

    public List<ContractDTO> getContract() {
        return contract;
    }

    public void setContract(List<ContractDTO> contract) {
        this.contract = contract;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy=" + createdBy +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            "}";
    }
}
