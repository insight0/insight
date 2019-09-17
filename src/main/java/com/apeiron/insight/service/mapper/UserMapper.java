package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.Authority;
import com.apeiron.insight.domain.DocumentPlaceholder;
import com.apeiron.insight.domain.User;
import com.apeiron.insight.service.FunctionalRoleService;
import com.apeiron.insight.service.SkillService;
import com.apeiron.insight.service.dto.FunctionalRoleDTO;
import com.apeiron.insight.service.dto.SkillDTO;
import com.apeiron.insight.service.dto.UserDTO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Mapper for the entity {@link User} and its DTO called {@link UserDTO}.
 * <p>
 * Normal mappers are generated using MapStruct, this one is hand-coded as MapStruct
 * support is still in beta, and requires a manual step with an IDE.
 */
@Service
public class UserMapper {

    @Autowired
    private FunctionalRoleService functionalRoleService;

    @Autowired
    private ContractMapper contractMapper;

    @Autowired
    private DegreeMapper degreeMapper;

    @Autowired
    private SkillMapper skillMapper;

    @Autowired
    private SkillService skillService;

    @Autowired
    private DocumentPlaceholderMapper documentPlaceholderMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private CertificationMapper certificationMapper;


    @Autowired
    private SalaryPackageMapper salaryPackageMapper;

    public List<UserDTO> usersToUserDTOs(List<User> users) {
        return users.stream()
            .filter(Objects::nonNull)
            .map(this::userToUserDTO)
            .collect(Collectors.toList());
    }

    public UserDTO userToUserDTO(User user) {

        UserDTO userDTO = new UserDTO(user);

        if (user.getFunctionalRoleIds() != null) {
            for (String id : user.getFunctionalRoleIds()
            ) {
                final Optional<FunctionalRoleDTO> one = functionalRoleService.findOne(id);
                if (one.isPresent()) {
                    userDTO.getFunctionalRoles().add(one.get());
                }
            }
        }

        if (user.getSkillIds() != null) {
            for (String id : user.getSkillIds()
            ) {
                final Optional<SkillDTO> one = skillService.findOne(id);
                if (one.isPresent()) {
                    userDTO.getSkills().add(one.get());
                }
            }
        }

        userDTO.setDegrees(degreeMapper.toDto(user.getDegrees()));
        userDTO.setContract(contractMapper.toDto(user.getContract()));
        userDTO.setSalaryPackage(salaryPackageMapper.toDto(user.getSalaryPackage()));
        userDTO.setAddress(addressMapper.toDto(user.getAddress()));
        userDTO.setIdCard(documentPlaceholderMapper.toDto(user.getIdCard()));
        userDTO.setPayslips(documentPlaceholderMapper.toDto(user.getPayslips()));
        userDTO.setCertifications(certificationMapper.toDto(user.getCertifications()));

        return userDTO;
    }

    public List<User> userDTOsToUsers(List<UserDTO> userDTOs) {
        return userDTOs.stream()
            .filter(Objects::nonNull)
            .map(this::userDTOToUser)
            .collect(Collectors.toList());
    }

    public User userDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        } else {
            User user = new User();
            user.setId(userDTO.getId());
            user.setLogin(userDTO.getLogin());
            user.setFirstName(userDTO.getFirstName());
            user.setLastName(userDTO.getLastName());
            user.setEmail(userDTO.getEmail());
            user.setImageUrl(userDTO.getImageUrl());
            user.setActivated(userDTO.isActivated());
            user.setLangKey(userDTO.getLangKey());
            user.setPhoneNumber(userDTO.getPhoneNumber());
            user.setParentPhoneNumber(userDTO.getParentPhoneNumber());
            user.setParentPhoneOwner(userDTO.getParentPhoneOwner());
            user.setYearsOfExperience(userDTO.getYearsOfExperience());
            user.setManagementSeniority(userDTO.getManagementSeniority());
            user.setLeadershipSeniority(userDTO.getLeadershipSeniority());
            user.setTechnicalSeniority(userDTO.getTechnicalSeniority());
            user.setTitle(userDTO.getTitle());
            user.setContract(contractMapper.toEntity(userDTO.getContract()));

            user.setSalaryPackage(salaryPackageMapper.toEntity(userDTO.getSalaryPackage()));

            if (userDTO.getFunctionalRoles() != null) {
                for (FunctionalRoleDTO functionalRoleDTO : userDTO.getFunctionalRoles()
                ) {
                    user.getFunctionalRoleIds().add(functionalRoleDTO.getId());
                }
            }

            if (userDTO.getSkills() != null) {
                for (SkillDTO skillDTO : userDTO.getSkills()
                ) {
                    user.getSkillIds().add(skillDTO.getId());
                }
            }

            user.setIdCard(documentPlaceholderMapper.toEntity(userDTO.getIdCard()));
            user.setAddress(addressMapper.toEntity(userDTO.getAddress()));
            user.setDegrees(degreeMapper.toEntity(userDTO.getDegrees()));
            user.setPayslips(documentPlaceholderMapper.toEntity(userDTO.getPayslips()));
            user.setCertifications(certificationMapper.toEntity(userDTO.getCertifications()));

            Set<Authority> authorities = this.authoritiesFromStrings(userDTO.getAuthorities());
            user.setAuthorities(authorities);
            return user;
        }
    }


    private Set<Authority> authoritiesFromStrings(Set<String> authoritiesAsString) {
        Set<Authority> authorities = new HashSet<>();

        if (authoritiesAsString != null) {
            authorities = authoritiesAsString.stream().map(string -> {
                Authority auth = new Authority();
                auth.setName(string);
                return auth;
            }).collect(Collectors.toSet());
        }

        return authorities;
    }

    public User userFromId(String id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }
}
