package com.apeiron.insight.service.mapper;

import com.apeiron.insight.domain.*;
import com.apeiron.insight.service.UserService;
import com.apeiron.insight.service.dto.DayOffDTO;

import com.apeiron.insight.service.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

/**
 * Mapper for the entity {@link DayOff} and its DTO {@link DayOffDTO}.
 */
@Service
public class DayOffMapper {

    @Autowired
    private UserService userService;

    public DayOff toEntity(DayOffDTO dto) {
        if (dto == null) {
            return null;
        }

        DayOff dayOff = new DayOff();

        dayOff.setId(dto.getId());
        dayOff.setStartDate(dto.getStartDate());
        dayOff.setEndDate(dto.getEndDate());
        dayOff.setDayOffObject(dto.getDayOffObject());
        dayOff.setStatus(dto.getStatus());
        dayOff.setForced(dto.isForced());
        dayOff.setEmployeId(dto.getEmployeId());
        dayOff.setValidatorId(dto.getValidatorId());
        dayOff.setDays(dto.getDays());
        dayOff.setType(dto.getType());
        dayOff.setNote(dto.getNote());
        dayOff.setApprovalFilePath(dto.getApprovalFilePath());
        dayOff.setGeneratedApprovalFilePath(dto.getGeneratedApprovalFilePath());
        dayOff.setMedicalCertificateFilePath(dto.getMedicalCertificateFilePath());

        return dayOff;
    }

    public Float dayOffDays(DayOff dayOff) {

        Long days = 0L;
        Long weekends = 0L;

        Calendar startCal = Calendar.getInstance();
        Calendar endCal = Calendar.getInstance();
        startCal.setTime(Date.from(dayOff.getStartDate()));
        endCal.setTime(Date.from(dayOff.getEndDate()));

        days = ChronoUnit.DAYS.between(dayOff.getStartDate(), dayOff.getEndDate())+1;

        if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            weekends++;
        }
        do {
            startCal.add(Calendar.DAY_OF_MONTH, 1);
            if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                weekends++;
            }
        } while (startCal.getTimeInMillis() < endCal.getTimeInMillis());
        return days.floatValue()-weekends;
    }

    public Float dayOffCount(DayOff dayOff) {
        return 1F;
    }


    public DayOffDTO toDto(DayOff entity) {
        if (entity == null) {
            return null;
        }

        DayOffDTO dayOffDTO = new DayOffDTO();

        dayOffDTO.setId(entity.getId());
        dayOffDTO.setStartDate(entity.getStartDate());
        dayOffDTO.setEndDate(entity.getEndDate());
        dayOffDTO.setDayOffObject(entity.getDayOffObject());
        dayOffDTO.setStatus(entity.getStatus());
        dayOffDTO.setForced(entity.isForced());
        dayOffDTO.setEmployeId(entity.getEmployeId());
        dayOffDTO.setValidatorId(entity.getValidatorId());
        // dayOffDays() : calculate total days excluding weekends
        dayOffDTO.setDays(dayOffDays(entity));
        dayOffDTO.setType(entity.getType());
        dayOffDTO.setNote(entity.getNote());
        dayOffDTO.setApprovalFilePath(entity.getApprovalFilePath());
        dayOffDTO.setGeneratedApprovalFilePath(entity.getGeneratedApprovalFilePath());
        dayOffDTO.setMedicalCertificateFilePath(entity.getMedicalCertificateFilePath());

        if (entity.getEmployeId() != null) {
            Optional<UserDTO> userWithAuthoritiesByLogin = userService.getUserWithAuthoritiesByLogin(entity.getEmployeId());
            if (userWithAuthoritiesByLogin.isPresent()) {
                dayOffDTO.setUser(userWithAuthoritiesByLogin.get());
            }
        }

        if (entity.getValidatorId() != null) {
            Optional<UserDTO> userWithAuthoritiesByLogin = userService.getUserWithAuthoritiesByLogin(entity.getValidatorId());
            if (userWithAuthoritiesByLogin.isPresent()) {
                dayOffDTO.setValidator(userWithAuthoritiesByLogin.get());
            }
        }
        return dayOffDTO;
    }

    public List<DayOff> toEntity(List<DayOffDTO> dtoList) {
        if (dtoList == null) {
            return null;
        }

        List<DayOff> list = new ArrayList<DayOff>(dtoList.size());
        for (DayOffDTO dayOffDTO : dtoList) {
            list.add(toEntity(dayOffDTO));
        }

        return list;
    }

    public List<DayOffDTO> toDto(List<DayOff> entityList) {
        if (entityList == null) {
            return null;
        }

        List<DayOffDTO> list = new ArrayList<DayOffDTO>(entityList.size());
        for (DayOff dayOff : entityList) {
            list.add(toDto(dayOff));
        }

        return list;
    }

    private DayOff fromId(String id) {
        if (id == null) {
            return null;
        }
        DayOff dayOff = new DayOff();
        dayOff.setId(id);
        return dayOff;
    }
}
